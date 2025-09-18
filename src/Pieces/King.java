package Pieces;

import main.Move;
import main.MyPanel;
import main.NamedImage;
import main.Vector2;

import java.util.LinkedList;
import java.util.Queue;

public class King extends Piece{
    public King(NamedImage image, String name, int color, MyPanel panel) {
        super(image, name, color, panel);
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
    public int[][] CalculatePossibleMoves(int current_x, int current_y) {
        int[][] possibleMoves = new int[gm.SQUARE_COUNT][gm.SQUARE_COUNT];
        for (int i = 0; i < gm.SQUARE_COUNT; i++) {
            for (int ii = 0; ii < gm.SQUARE_COUNT; ii++) {
                possibleMoves[i][ii] = 0;
            }
        }
        CheckForCastle(possibleMoves, current_x, current_y);
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
                //make it a 2 for takeable e piece
                if (board[current_x][current_y].color != board[startx][starty].color) {
                    possibleMoves[current_x][current_y] = 2;
                }
            }
        }
        return possibleMoves;
    }

    private void CheckForCastle(int[][] possibleMoves, int current_x, int current_y){
        if(board[current_x][current_y].moveCount != 0){return;}
        //King Side
        if (board[current_x+3][current_y] != null) {
            Piece kRook = board[current_x+3][current_y];
            if(kRook != null && kRook.character == 'R' && kRook.moveCount == 0){
                if(board[current_x+1][current_y] == null && board[current_x+2][current_y] == null){
                    possibleMoves[current_x+3][current_y] = 3;
                }
            }
        }

        //Queen Side
        if (board[current_x-4][current_y] != null) {
            Piece qRook = board[current_x-4][current_y];
            if(qRook != null && qRook.character == 'R' && qRook.moveCount == 0){
                if(board[current_x-1][current_y] == null && board[current_x-2][current_y] == null && board[current_x-3][current_y] == null){
                    possibleMoves[current_x-4][current_y] = 3;
                }
            }
        }
    }

    @Override
    public void SpecialMove(int rook_x, int rook_y){
        int king_x = panel.selected_x;
        int king_y = panel.selected_y;
        Piece rook = board[rook_x][rook_y];
        Piece king = board[king_x][king_y];

        panel.board[rook_x][rook_y].increaseMoveCountBy(1);
        panel.board[king_x][king_y].increaseMoveCountBy(1);


        Queue<Move> q = new LinkedList<>();

        if(rook_x == 0){
            q.add(new Move(board[king_x][king_y], king_x, king_y, board[king_x-2][king_y], king_x-2, king_y));
            q.add(new Move(board[rook_x][rook_y], rook_x, rook_y, board[rook_x+3][rook_y], rook_x+3, rook_y));
            board[rook_x+3][rook_y] = rook;
            board[rook_x][rook_y] = null;
            board[king_x-2][king_y] = king;
            board[king_x][king_y] = null;
        }
        else if(rook_x == 7){
            q.add(new Move(board[king_x][king_y], king_x, king_y, board[king_x+2][king_y], king_x+2, king_y));
            q.add(new Move(board[rook_x][rook_y], rook_x, rook_y, board[rook_x-2][rook_y], rook_x-2, rook_y));
            board[rook_x-2][rook_y] = rook;
            board[rook_x][rook_y] = null;
            board[king_x+2][king_y] = king;
            board[king_x][king_y] = null;
        }else{
            System.out.println("Error: Wrong Rook coordinates");
        }
        panel.moveHistory.add(q);


        panel.ResetSelect();
        panel.SwitchTurn();

    }
}
