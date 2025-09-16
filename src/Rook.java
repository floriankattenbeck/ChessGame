public class Rook extends Piece{
    Rook(NamedImage image, String name, int color) {
        super(image, name, color);
        character = 'K';
        moveset = new int[2][2];
        value = 6;
        //werde das bald eleganter Lösen
        //das moveset beschreibt, in welche Richtugen sich die Figur bewegen darf, dabei ist moveset[i][0] -> x-Komponente
        //                                                                                   moveset[i][1] -> y-Komponente
        moveset[0][0] = 1;
        moveset[0][1] = 0;
        moveset[1][0] = 0;
        moveset[1][1] = 1;
    }
}
