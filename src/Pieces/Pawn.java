package Pieces;

import main.MyPanel;
import main.NamedImage;
import main.Vector2;

public class Pawn extends Piece {
    public Pawn(NamedImage image, String name, int color, MyPanel panel) {
        super(image, name, color, panel);
        character = 'P';
        value = 1;
        moveDirections = new Vector2[3];
        moveDirections[0] = new Vector2(0, -1);
        moveDirections[1] = new Vector2(1, -1);
        moveDirections[2] = new Vector2(-1, -1);
    }

    @Override
    public int[][] CalculatePossibleMoves(int current_x, int current_y) {
        int[][] possibleMoves = new int[gm.SQUARE_COUNT][gm.SQUARE_COUNT];
        for (int i = 0; i < gm.SQUARE_COUNT; i++) {
            for (int ii = 0; ii < gm.SQUARE_COUNT; ii++) {
                possibleMoves[i][ii] = 0;
            }
        }
        int startx = current_x;
        int starty = current_y;
        for (Vector2 moveDirection : moveDirections) {

            current_x = startx + moveDirection.x;
            current_y = starty + moveDirection.y;

            if (current_x >= possibleMoves.length || current_x < 0 || current_y >= possibleMoves.length || current_y < 0) {
                continue;
            }
            if (board[startx][starty].color != gm.startColor) {
                moveDirection.y = 1;
                current_y = starty + moveDirection.y;
            }
            if (board[current_x][current_y] == null && moveDirection.x == 0) {
                //mark it a 1 for free square
                possibleMoves[current_x][current_y] = 1;

                if (moveCount == 0 && current_y + moveDirection.y > 0) {
                    possibleMoves[current_x][current_y + moveDirection.y] = 1;
                }

            } else if (board[current_x][current_y] != null) {
                //make it a 2 for takeable piece
                if (board[current_x][current_y].color != board[startx][starty].color && moveDirection.x != 0) {
                    possibleMoves[current_x][current_y] = 2;
                }
            }
        }
        return possibleMoves;
    }
}
