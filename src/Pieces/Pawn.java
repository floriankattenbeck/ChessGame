package Pieces;

import main.Move;
import main.MyPanel;
import main.NamedImage;
import main.Vector2;

import java.util.LinkedList;
import java.util.Queue;

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
    public int[][] CalculatePossibleMoves(int clicked_x, int clicked_y) {
        int[][] possibleMoves = new int[gm.SQUARE_COUNT][gm.SQUARE_COUNT];
        for (int i = 0; i < gm.SQUARE_COUNT; i++) {
            for (int ii = 0; ii < gm.SQUARE_COUNT; ii++) {
                possibleMoves[i][ii] = 0;
            }
        }
        int startx = clicked_x;
        int starty = clicked_y;
        for (Vector2 moveDirection : moveDirections) {
            moveDirection.y = board[startx][starty].color != gm.boardOrientation ? 1 : moveDirection.y;
            clicked_x = startx + moveDirection.x;
            clicked_y = starty + moveDirection.y;

            if (clicked_x >= possibleMoves.length || clicked_x < 0 || clicked_y >= possibleMoves.length || clicked_y < 0) {
                continue;
            }

            if (board[clicked_x][clicked_y] == null && moveDirection.x == 0) {
                //mark it a 1 for free square
                possibleMoves[clicked_x][clicked_y] = 1;

                if (moveCount == 0 && clicked_y + moveDirection.y > 0) {
                    possibleMoves[clicked_x][clicked_y + moveDirection.y] = 1;
                }

            } else if (board[clicked_x][clicked_y] != null) {
                //make it a 2 for takeable piece
                if (board[clicked_x][clicked_y].color != board[startx][starty].color && moveDirection.x != 0) {
                    possibleMoves[clicked_x][clicked_y] = 2;
                }
            }
            CheckForUpgrade(possibleMoves, startx, starty, moveDirection);
            CheckForEnPassant(possibleMoves, startx, starty, moveDirection);
        }
        return possibleMoves;
    }

    private void CheckForEnPassant(int[][] possibleMoves, int startx, int starty, Vector2 moveDirection) {
        try {
            Move lastMove = panel.moveHistory.peek().peek();
            if(lastMove.firstPiece.character != 'P'){
                System.out.println("last move wasn't made my a pawn.");
                return;
            }
            if(Math.abs(lastMove.secondy - lastMove.firsty) < 2){
                System.out.println("the last move was not a 2 step");
                return;
            }
            if(lastMove.secondy == starty && Math.abs(lastMove.secondx - startx) == 1){
                System.out.println("En Passant!");
                possibleMoves[lastMove.secondx][lastMove.secondy+moveDirection.y] = 4;
            }
        } catch (Exception e){
            System.out.println("there is no last move");
        }

    }

    public void CheckForUpgrade(int[][] possibleMoves, int startx, int starty, Vector2 moveDirection) {
        int ygoal;
        if (board[startx][starty].color == gm.boardOrientation) {
            ygoal = 0;
        } else {
            ygoal = 7;
        }
        if(starty + moveDirection.y == ygoal){
            possibleMoves[startx + moveDirection.x][starty + moveDirection.y] = 3;
        }
    }

    @Override
    public void SpecialMove(int clicked_x, int clicked_y) {
        //Change pawn to whatever piece you want
        Queue<Move> q = new LinkedList<>();
        q.add(new Move(panel.board[panel.selected_x][panel.selected_y], panel.selected_x, panel.selected_y, panel.board[clicked_x][clicked_y], clicked_x, clicked_y));

        if(panel.board[clicked_x][clicked_y] != null){
            q.add(new Move(board[panel.selected_x][panel.selected_y], panel.selected_x, panel.selected_y, board[clicked_x][clicked_y], clicked_x, clicked_y));
            panel.AddToTakenPieces(board[clicked_x][clicked_y]);
        }
        if(panel.board[panel.selected_x][panel.selected_y].color == gm.LIGHT){
            panel.board[clicked_x][clicked_y] = new Queen(panel.light_queen, "light_queen", gm.LIGHT, panel);
        } else {
            panel.board[clicked_x][clicked_y] = new Queen(panel.dark_queen, "dark_queen", gm.DARK, panel);
        }
        panel.board[panel.selected_x][panel.selected_y] = null;
        panel.moveHistory.add(q);
        panel.SwitchTurn();
        panel.ResetSelect();
    }
    @Override
    public void SecondSpecialMove(int clicked_x, int clicked_y){
        moveDirections[0].y = board[panel.selected_x][panel.selected_y].color != gm.boardOrientation ? 1 : moveDirections[0].y;
        System.out.println("en passant move method called");
        //Change pawn to whatever piece you want
        Queue<Move> q = new LinkedList<>();
        q.add(new Move(board[panel.selected_x][panel.selected_y], panel.selected_x, panel.selected_y, board[clicked_x][clicked_y], clicked_x, clicked_y));

        board[panel.selected_x][panel.selected_y].increaseMoveCountBy(1);
        panel.AddToTakenPieces(board[clicked_x][clicked_y]);
        board[clicked_x][clicked_y] = board[panel.selected_x][panel.selected_y];
        board[panel.selected_x][panel.selected_y] = null;
        board[clicked_x][clicked_y-moveDirections[0].y] = null;

        panel.moveHistory.add(q);
        panel.SwitchTurn();
        panel.ResetSelect();
    }

}
