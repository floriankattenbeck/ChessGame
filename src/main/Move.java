package main;

import Pieces.Piece;

public class Move {
    public Piece firstPiece;
    int firstx;
    int firsty;
    public Piece secondPiece;
    int secondx;
    int secondy;

    public Move(Piece firstPiece, int firstx, int firsty, Piece secondPiece, int secondx, int secondy){
        this.firstPiece = firstPiece;
        this.firstx = firstx;
        this.firsty = firsty;
        this.secondPiece = secondPiece;
        this.secondx = secondx;
        this.secondy = secondy;
    }
}
