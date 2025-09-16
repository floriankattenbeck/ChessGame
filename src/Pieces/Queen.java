package Pieces;

import main.NamedImage;
import main.Vector2;

public class Queen extends Piece {
    public Queen(NamedImage image, String name, int color) {
        super(image, name, color);
        character = 'Q';
        value = 9;
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

}
