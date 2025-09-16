package Pieces;

import java.util.UUID;
import main.NamedImage;
import main.GameManager;


public class Piece {

    public NamedImage namedImage;
    public String name;
    public char character;
    public int color;
    GameManager gm;
    public int[][] moveset;
    public UUID id;
    public int value;

    Piece(NamedImage namedImage, String name, int color) {
        this.namedImage = namedImage;
        this.name = name;
        this.color = color;
        gm = new GameManager();
        moveset = null;
        id = UUID.randomUUID();
    }

    public int[][] CalculatePossibleMoves(Piece[][] board, int current_x, int current_y) {
        int[][] possibleMoves = new int[gm.SQUARE_COUNT][gm.SQUARE_COUNT];
        for (int i = 0; i < gm.SQUARE_COUNT; i++) {
            for (int ii = 0; ii < gm.SQUARE_COUNT; ii++) {
                possibleMoves[i][ii] = 0;
            }
        }
        int startx = current_x;
        int starty = current_y;
        for (int[] ints : moveset) {
            int xdir = ints[0];
            int ydir = ints[1];
            current_x = startx;
            current_y = starty;

            int dirChanged = 0;

            while (dirChanged < 2) {
                current_x += xdir;
                current_y += ydir;

                if (current_x == startx && current_y == starty) {
                    continue;
                }

                if (current_x >= possibleMoves.length || current_x < 0 ||
                        current_y >= possibleMoves.length || current_y < 0) {
                    dirChanged++;
                    if (dirChanged == 2) {
                        break;
                    }
                    xdir = xdir * -1;
                    ydir = ydir * -1;
                    continue;
                }

                if (board[current_x][current_y] == null) {
                    //mark it a 1 for free square
                    possibleMoves[current_x][current_y] = 1;

                } else {
                    //make it a 2 for takeable piece
                    if (board[current_x][current_y].color != board[startx][starty].color) {
                        possibleMoves[current_x][current_y] = 2;
                    }
                    dirChanged++;
                    if (dirChanged == 2) {
                        break;
                    }
                    xdir = xdir * -1;
                    ydir = ydir * -1;
                }
            }
        }
        return possibleMoves;
    }

    public int getValue(){
        return value;
    }
}