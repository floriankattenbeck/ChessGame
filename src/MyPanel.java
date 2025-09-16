import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Stack;

public class MyPanel extends JPanel implements MouseListener {

    Graphics2D g2D;
    GameManager gm = new GameManager();
    final int SQUARE_COUNT = gm.SQUARE_COUNT;
    final int SQUARE_SIZE = gm.SQUARE_SIZE;
    final int WIDTH = SQUARE_SIZE * SQUARE_COUNT;
    final int HEIGTH = SQUARE_SIZE * SQUARE_COUNT;
    final int DARK = gm.DARK;
    final int LIGHT = gm.LIGHT;
    final int IDLE = gm.IDLE;
    final int SELECTED = gm.SELECTED;
    int current_turn = DARK;
    int current_x = 0;
    int current_y = 0;
    Piece current_selected = null;
    int current_selected_x = 0;
    int current_selected_y = 0;
    int score = gm.score;
    NamedImage light_queen = new NamedImage("pieces/light_queen");
    NamedImage dark_queen = new NamedImage("pieces/dark_queen");
    NamedImage light_bishop = new NamedImage("pieces/light_bishop");
    NamedImage dark_bishop = new NamedImage("pieces/dark_bishop");
    NamedImage dark_rook = new NamedImage("pieces/dark_rook");
    NamedImage border_green = new NamedImage("ui/border_green");
    NamedImage border_yellow = new NamedImage("ui/border_yellow");
    NamedImage light_square = new NamedImage("ui/light_square");
    NamedImage dark_square = new NamedImage("ui/dark_square");
    NamedImage dot = new NamedImage("ui/dot");
    NamedImage border_red = new NamedImage("ui/border_red");
    Piece[][] board = new Piece[SQUARE_COUNT][SQUARE_COUNT];
    UIElement[][] ui = new UIElement[SQUARE_COUNT][SQUARE_COUNT];
    int[][] possibleMoves = new int[SQUARE_COUNT][SQUARE_COUNT];
    Stack<Move> moveHistory = new Stack<>();
    ArrayList<Piece> takenPieces = gm.takenPieces;

    MyPanel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGTH));
        addMouseListener(this);

        run();
    }

    private void run() {

        board[3][3] = new Queen(dark_queen, "dark_queen", DARK);
        board[4][1] = new Queen(dark_queen, "dark_queen", DARK);
        board[7][7] = new Queen(light_queen, "light_queen", LIGHT);
        board[7][4] = new Queen(light_queen, "light_queen", LIGHT);
        board[6][3] = new Bishop(light_bishop, "light_bishop", LIGHT);
        board[0][0] = new Bishop(dark_bishop, "dark_bishop", DARK);
        board[1][1] = new Rook(dark_rook, "dark_rook", DARK);
    }

    public void paint(Graphics g) {

        g2D = (Graphics2D) g;

        UIElement dark_square = new UIElement(this.dark_square, "dark_square");
        UIElement light_square = new UIElement(this.light_square, "light_square");
        UIElement[] img_square_arr = {dark_square, light_square};

        for (int i = 0; i < SQUARE_COUNT; i++) {
            for (int ii = 0; ii < SQUARE_COUNT; ii++) {

                //Draw background squares
                g2D.drawImage(img_square_arr[((i % 2) + (ii + 1)) % 2].namedImage.getImage(), i * SQUARE_SIZE, ii * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE, this);

                //Draw Pieces
                if (board[i][ii] != null) {
                    DrawOnBoard(board[i][ii].namedImage.getImage(), i, ii);
                }

                //Draw UI Elements
                if (ui[i][ii] != null) {
                    DrawOnBoard(ui[i][ii].namedImage.getImage(), i, ii);
                }

                //gamestate specific
                if(gm.gamestate == 1 && possibleMoves != null){
                    if(possibleMoves[i][ii] == 1){
                        DrawOnBoard(dot.getImage(), i, ii);
                    }
                    if(possibleMoves[i][ii] == 2){
                        DrawOnBoard(border_red.getImage(), i, ii);
                    }
                }
            }
        }

    }

    private void DrawOnBoard(BufferedImage bufferedImage, int x, int y) {
        g2D.drawImage(bufferedImage, x * SQUARE_SIZE, y * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE, this);
    }

    private void UndoMove(){
        if (!moveHistory.isEmpty()) {
            Move lastMove = moveHistory.pop();
            board[lastMove.firstx][lastMove.firsty] = lastMove.firstPiece;
            board[lastMove.secondx][lastMove.secondy] = lastMove.secondPiece;
            gm.gamestate = IDLE;
            RemoveFromTakenPieces(lastMove.secondPiece);
            ResetSelect();
            repaint();
        }
    }

    private void AddToTakenPieces(Piece piece){
        if(piece == null){return;}
        takenPieces.add(piece);
        if(piece.color == LIGHT){
            gm.score -= piece.getValue();
        } else {
            gm.score += piece.getValue();
        }

    }

    private void RemoveFromTakenPieces(Piece piece){
        if(piece == null){return;}
        for(int i = 0; i < takenPieces.size(); i++){
            if(takenPieces.get(i).id == piece.id){
                if(piece.color == LIGHT){
                    gm.score += piece.getValue();
                } else {
                    gm.score -= piece.getValue();
                }
                takenPieces.remove(i);
            }
        }
    }

    private void SwitchTurn(){
        current_turn = (current_turn+1)%2;
    }

    private void MakeMove(){
        //aktuelle figur, position, destinationfigur und position davon auf den stack
        moveHistory.add(new Move(board[current_selected_x][current_selected_y], current_selected_x, current_selected_y, board[current_x][current_y], current_x, current_y));
        AddToTakenPieces(board[current_x][current_y]);
        board[current_x][current_y] = board[current_selected_x][current_selected_y];
        board[current_selected_x][current_selected_y] = null;
        ResetSelect();
        SwitchTurn();
        //update global gamestate

        System.out.println("Move-Abfrage");
    }

    private void SelectPiece(){
        System.out.println("selectsquare-Abfrage");
        //select square, put it in gamestate = 1
        ui[current_x][current_y] = new UIElement(border_yellow, "border_yellow");
        gm.gamestate = SELECTED;
        possibleMoves = board[current_x][current_y].CalculatePossibleMoves(board, current_x, current_y);
        if(current_selected!=null){
            if(current_selected.id != board[current_x][current_y].id){
                //deselect old square
                System.out.println("dazu noch deselect!");
                ui[current_selected_x][current_selected_y] = null;
            }
        }
        //update the current_selected values to new selected
        current_selected = board[current_x][current_y];
        current_selected_x = current_x;
        current_selected_y = current_y;
    }

    private void ResetSelect(){
        ui[current_selected_x][current_selected_y] = null;
        gm.gamestate = IDLE;
        current_selected = null;
        possibleMoves = null;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        final int RIGHTCLICK = 3;
        final int LEFTCLICK = 1;
        current_x = (e.getX() - this.getX()) / SQUARE_SIZE;
        current_y = (e.getY() - this.getY()) / SQUARE_SIZE;

        if (e.getButton() == RIGHTCLICK) {
//            if (ui[current_x][current_y] == null) {
//                ui[current_x][current_y] = new UIElement(border_green, "border_green");
//            } else {
//                if (ui[current_x][current_y].name.equals("border_green")) {
//                    ui[current_x][current_y] = null;
//                }
//            }
            UndoMove();
        }


        if (e.getButton() == LEFTCLICK) {

            //prüfe, ob das ausgewählte Feld eine gegnerische Figur beinhaltet, oder frei ist
            if(gm.gamestate == SELECTED && (possibleMoves[current_x][current_y] == 2 || possibleMoves[current_x][current_y] == 1)){
                MakeMove();
            }

            //befindet sich dort eine Figur in deiner Farbe?
            else if (board[current_x][current_y] == null || board[current_x][current_y].color != current_turn) {
                System.out.println("return-Abfrage");
                return;
            }
            //prüft ob das Feld markiert ist und setzt den selected gamestate ein (=1)
            else if (ui[current_x][current_y] == null) {
                SelectPiece();
            }

            //Falls es schon selected ist (=1) setze den select bei weiterm klick zurück
            else if(ui[current_x][current_y].name.equals("border_yellow")) {
                if(current_selected.id == board[current_x][current_y].id){
                    ResetSelect();
                    System.out.println("deselect square selbes feld-Abfrage");
                }
            }


        }
        repaint();
        //DEBUG
        if(!takenPieces.isEmpty()){
            System.out.println(takenPieces);
        } else {
            System.out.println("No taken Pieces");
        }
        System.out.println(gm.score);
    }


    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}