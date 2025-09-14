public class Queen extends Piece {
    Queen(NamedImage image, String name, int color) {
        super(image, name, color);
        character = 'Q';
        moveset = new int[4][2];
        //werde das bald eleganter Lösen
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
