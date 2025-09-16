package Pieces;

import main.NamedImage;

public class Queen extends Piece {
    public Queen(NamedImage image, String name, int color) {
        super(image, name, color);
        character = 'Q';
        moveset = new int[4][2];
        value = 9;
        //werde das bald eleganter Lösen
        //das moveset beschreibt, in welche Richtugen sich die Figur bewegen darf, dabei ist moveset[i][0] -> x-Komponente
        //                                                                                   moveset[i][1] -> y-Komponente
        moveset[0][0] = 1;
        moveset[0][1] = 1;
        moveset[1][0] = 1;
        moveset[1][1] = -1;
        moveset[2][0] = 1;
        moveset[2][1] = 0;
        moveset[3][0] = 0;
        moveset[3][1] = 1;

    }

}
