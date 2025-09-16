package Pieces;

import main.NamedImage;
import main.Vector2;

public class King extends Piece{
    public King(NamedImage image, String name, int color) {
        super(image, name, color);
        character = 'K';
        value = 999;
        this.moveCount = 0;

        moveDirections = new Vector2[8];
        moveDirections[0] = new Vector2(1, 1);
        moveDirections[1] = new Vector2(1, -1);
        moveDirections[2] = new Vector2(1, 0);
        moveDirections[3] = new Vector2(0, 1);
        moveDirections[4] = new Vector2(0, -1);
        moveDirections[5] = new Vector2(-1, -1);
        moveDirections[6] = new Vector2(-1, 0);
        moveDirections[7] = new Vector2(-1, 1);
    }
    @Override
    public int[][] CalculatePossibleMoves(Piece[][] board, int current_x, int current_y) {
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

            if (current_x >= possibleMoves.length || current_x < 0 ||
                    current_y >= possibleMoves.length || current_y < 0) {
                continue;
            }

            if (board[current_x][current_y] == null) {
                //mark it a 1 for free square
                possibleMoves[current_x][current_y] = 1;

            } else {
                //make it a 2 for takeab e piece
                if (board[current_x][current_y].color != board[startx][starty].color) {
                    possibleMoves[current_x][current_y] = 2;
                }
            }
        }
        return possibleMoves;
    }
}
