package Pieces;

import main.NamedImage;

public class Bishop extends Piece{
    public Bishop(NamedImage image, String name, int color) {
        super(image, name, color);
        character = 'B';
        moveset = new int[2][2];
        value = 3;
        //werde das bald eleganter Lösen
        //das moveset beschreibt, in welche Richtugen sich die Figur bewegen darf, dabei ist moveset[i][0] -> x-Komponente
        //                                                                                   moveset[i][1] -> y-Komponente
        moveset[0][0] = 1;
        moveset[0][1] = 1;
        moveset[1][0] = 1;
        moveset[1][1] = -1;
    }
}
