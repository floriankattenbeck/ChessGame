public class Move {
    Piece firstPiece;
    int firstx;
    int firsty;
    Piece secondPiece;
    int secondx;
    int secondy;

    Move(Piece firstPiece, int firstx, int firsty, Piece secondPiece, int secondx, int secondy){
        this.firstPiece = firstPiece;
        this.firstx = firstx;
        this.firsty = firsty;
        this.secondPiece = secondPiece;
        this.secondx = secondx;
        this.secondy = secondy;
    }
}
