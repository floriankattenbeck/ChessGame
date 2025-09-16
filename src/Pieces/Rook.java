package Pieces;

import main.NamedImage;
import main.Vector2;

public class Rook extends Piece {
    public Rook(NamedImage image, String name, int color) {
        super(image, name, color);
        character = 'K';
        value = 6;
        moveDirections = new Vector2[2];
        moveDirections[0] = new Vector2(1, 0);
        moveDirections[1] = new Vector2(0, 1);
    }
}
