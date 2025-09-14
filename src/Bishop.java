public class Bishop extends Piece{
    Bishop(NamedImage image, String name, int color) {
        super(image, name, color);
        character = 'B';
        moveset = new int[2][2];
        //werde das bald eleganter Lösen
        moveset[0][0] = 1;
        moveset[0][1] = 1;
        moveset[1][0] = 1;
        moveset[1][1] = -1;
    }
}
