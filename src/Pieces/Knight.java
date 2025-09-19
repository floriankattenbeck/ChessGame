package Pieces;

import main.MyPanel;
import main.NamedImage;
import main.Vector2;

public class Knight extends Piece{

    public Knight(NamedImage image, String name, int color, MyPanel panel) {
        super(image, name, color, panel);
        character = 'N';
        value = 3;
        this.moveCount = 0;
        moveDirections = new Vector2[2];
        moveDirections[0] = new Vector2(2, 1);
        moveDirections[1] = new Vector2(1, 2);
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
            for(int i = 0; i < 4 ; i++){
                current_x = startx + moveDirection.x;
                current_y = starty + moveDirection.y;

                if (current_x >= possibleMoves.length || current_x < 0 ||
                        current_y >= possibleMoves.length || current_y < 0) {
                    System.out.println("continue");
                    moveDirection = moveDirection.rotate90(true);
                    continue;
                }
                if (possibleMoves[current_x][current_y] != 0) {
                    System.out.println("break");
                    break;

                }
                else if (board[current_x][current_y] == null) {
                    //mark it a 1 for free square
                    possibleMoves[current_x][current_y] = 1;
                } else {
                    //make it a 2 for takeable piece
                    if (board[current_x][current_y].color != board[startx][starty].color) {
                        possibleMoves[current_x][current_y] = 2;
                    }
                }
                moveDirection = moveDirection.rotate90(true);
            }
        }
        return possibleMoves;
    }
}
