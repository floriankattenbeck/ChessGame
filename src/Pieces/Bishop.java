package Pieces;

import main.MyPanel;
import main.NamedImage;
import main.Vector2;

public class Bishop extends Piece {
    public Bishop(NamedImage image, String name, int color, MyPanel panel) {
        super(image, name, color, panel);
        character = 'B';
        value = 3;
        moveDirections = new Vector2[2];
        moveDirections[0] = new Vector2(1, 1);
        moveDirections[1] = new Vector2(1, -1);
    }
}
