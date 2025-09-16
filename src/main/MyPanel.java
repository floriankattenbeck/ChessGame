package main;

import Pieces.*;

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
    int current_turn = gm.startColor;
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
    NamedImage light_rook = new NamedImage("pieces/light_rook");
    NamedImage dark_king = new NamedImage("pieces/dark_king");
    NamedImage light_king = new NamedImage("pieces/light_king");
    NamedImage dark_pawn = new NamedImage("pieces/dark_pawn");
    NamedImage light_pawn = new NamedImage("pieces/light_pawn");
    NamedImage dark_knight = new NamedImage("pieces/dark_knight");
    NamedImage light_knight = new NamedImage("pieces/light_knight");
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

        SetupBoard();
    }

    private void SetupBoard() {

        if (current_turn == DARK) {

            //CHATGPT TEST CODE
            // Weiße Grundreihe (unten, y = 0)
            board[0][0] = new Rook(light_rook, "light_rook", LIGHT);
            board[1][0] = new Knight(light_knight, "light_knight", LIGHT);
            board[2][0] = new Bishop(light_bishop, "light_bishop", LIGHT);
            board[3][0] = new Queen(light_queen, "light_queen", LIGHT);
            board[4][0] = new King(light_king, "light_king", LIGHT);
            board[5][0] = new Bishop(light_bishop, "light_bishop", LIGHT);
            board[6][0] = new Knight(light_knight, "light_knight", LIGHT);
            board[7][0] = new Rook(light_rook, "light_rook", LIGHT);

            // Weiße Bauernreihe (y = 1)
            for (int x = 0; x < 8; x++) {
                board[x][1] = new Pawn(light_pawn, "light_pawn", LIGHT);
            }

            // Mittelfeld leer (y = 2..5)
            for (int y = 2; y <= 5; y++) {
                for (int x = 0; x < 8; x++) {
                    board[x][y] = null;
                }
            }

            // Schwarze Bauernreihe (y = 6)
            for (int x = 0; x < 8; x++) {
                board[x][6] = new Pawn(dark_pawn, "dark_pawn", DARK);
            }

            // Schwarze Grundreihe (oben, y = 7)
            board[0][7] = new Rook(dark_rook, "dark_rook", DARK);
            board[1][7] = new Knight(dark_knight, "dark_knight", DARK);
            board[2][7] = new Bishop(dark_bishop, "dark_bishop", DARK);
            board[3][7] = new Queen(dark_queen, "dark_queen", DARK);
            board[4][7] = new King(dark_king, "dark_king", DARK);
            board[5][7] = new Bishop(dark_bishop, "dark_bishop", DARK);
            board[6][7] = new Knight(dark_knight, "dark_knight", DARK);
            board[7][7] = new Rook(dark_rook, "dark_rook", DARK);
        } else {
            // Schwarze Grundreihe (unten, y = 0)
            board[0][0] = new Rook(dark_rook, "dark_rook", DARK);
            board[1][0] = new Knight(dark_knight, "dark_knight", DARK);
            board[2][0] = new Bishop(dark_bishop, "dark_bishop", DARK);
            board[3][0] = new Queen(dark_queen, "dark_queen", DARK);
            board[4][0] = new King(dark_king, "dark_king", DARK);
            board[5][0] = new Bishop(dark_bishop, "dark_bishop", DARK);
            board[6][0] = new Knight(dark_knight, "dark_knight", DARK);
            board[7][0] = new Rook(dark_rook, "dark_rook", DARK);

            // Schwarze Bauernreihe (y = 1)
            for (int x = 0; x < 8; x++) {
                board[x][1] = new Pawn(dark_pawn, "dark_pawn", DARK);
            }

            // Mittelfeld leer (y = 2..5)
            for (int y = 2; y <= 5; y++) {
                for (int x = 0; x < 8; x++) {
                    board[x][y] = null;
                }
            }

            // Weiße Bauernreihe (y = 6)
            for (int x = 0; x < 8; x++) {
                board[x][6] = new Pawn(light_pawn, "light_pawn", LIGHT);
            }

            // Weiße Grundreihe (oben, y = 7)
            board[0][7] = new Rook(light_rook, "light_rook", LIGHT);
            board[1][7] = new Knight(light_knight, "light_knight", LIGHT);
            board[2][7] = new Bishop(light_bishop, "light_bishop", LIGHT);
            board[3][7] = new Queen(light_queen, "light_queen", LIGHT);
            board[4][7] = new King(light_king, "light_king", LIGHT);
            board[5][7] = new Bishop(light_bishop, "light_bishop", LIGHT);
            board[6][7] = new Knight(light_knight, "light_knight", LIGHT);
            board[7][7] = new Rook(light_rook, "light_rook", LIGHT);
        }
        board[3][3] = new Pawn(dark_pawn, "dark_pawn", DARK);
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
                if (gm.gamestate == 1 && possibleMoves != null) {
                    if (possibleMoves[i][ii] == 1) {
                        DrawOnBoard(dot.getImage(), i, ii);
                    }
                    if (possibleMoves[i][ii] == 2) {
                        DrawOnBoard(border_red.getImage(), i, ii);
                    }
                }
            }
        }

    }

    private void DrawOnBoard(BufferedImage bufferedImage, int x, int y) {
        g2D.drawImage(bufferedImage, x * SQUARE_SIZE, y * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE, this);
    }

    private void UndoMove() {
        if (!moveHistory.isEmpty()) {
            Move lastMove = moveHistory.pop();
            board[lastMove.firstx][lastMove.firsty] = lastMove.firstPiece;
            board[lastMove.secondx][lastMove.secondy] = lastMove.secondPiece;
            gm.gamestate = IDLE;
            lastMove.firstPiece.increaseMoveCountBy(-1);
            RemoveFromTakenPieces(lastMove.secondPiece);
            ResetSelect();
            repaint();
            SwitchTurn();
        }
    }

    private void AddToTakenPieces(Piece piece) {
        if (piece == null) {
            return;
        }
        takenPieces.add(piece);
        if (piece.color == LIGHT) {
            gm.score -= piece.getValue();
        } else {
            gm.score += piece.getValue();
        }

    }

    private void RemoveFromTakenPieces(Piece piece) {
        if (piece == null) {
            return;
        }
        for (int i = 0; i < takenPieces.size(); i++) {
            if (takenPieces.get(i).id == piece.id) {
                if (piece.color == LIGHT) {
                    gm.score += piece.getValue();
                } else {
                    gm.score -= piece.getValue();
                }
                takenPieces.remove(i);
            }
        }
    }

    private void SwitchTurn() {
        current_turn = (current_turn + 1) % 2;
    }

    private void MakeMove() {
        //aktuelle figur, position, destinationfigur und position davon auf den stack
        moveHistory.add(new Move(board[current_selected_x][current_selected_y], current_selected_x, current_selected_y, board[current_x][current_y], current_x, current_y));
        board[current_selected_x][current_selected_y].increaseMoveCountBy(1);
        AddToTakenPieces(board[current_x][current_y]);
        board[current_x][current_y] = board[current_selected_x][current_selected_y];
        board[current_selected_x][current_selected_y] = null;
        ResetSelect();
        SwitchTurn();
        //update global gamestate

        System.out.println("main.Move-Abfrage");
    }

    private void SelectPiece() {
        //select square, put it in gamestate = 1
        ui[current_x][current_y] = new UIElement(border_yellow, "border_yellow");
        gm.gamestate = SELECTED;
        possibleMoves = board[current_x][current_y].CalculatePossibleMoves(board, current_x, current_y);
        if (current_selected != null) {
            if (current_selected.id != board[current_x][current_y].id) {
                //deselect old square
                System.out.println("dazu noch deselect!");
                ui[current_selected_x][current_selected_y] = null;
            }
        }
        //update the current_selected values to new selected
        current_selected = board[current_x][current_y];
        current_selected_x = current_x;
        current_selected_y = current_y;
        System.out.println("selectsquare-Abfrage: moveCount: " + current_selected.moveCount);
    }

    private void ResetSelect() {
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
//                ui[current_x][current_y] = new main.UIElement(border_green, "border_green");
//            } else {
//                if (ui[current_x][current_y].name.equals("border_green")) {
//                    ui[current_x][current_y] = null;
//                }
//            }
            UndoMove();
        }


        if (e.getButton() == LEFTCLICK) {

            //prüfe, ob das ausgewählte Feld eine gegnerische Figur beinhaltet, oder frei ist
            if (gm.gamestate == SELECTED && (possibleMoves[current_x][current_y] == 2 || possibleMoves[current_x][current_y] == 1)) {
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
            else if (ui[current_x][current_y].name.equals("border_yellow")) {
                if (current_selected.id == board[current_x][current_y].id) {
                    ResetSelect();
                    System.out.println("deselect square selbes feld-Abfrage");
                }
            }


        }
        repaint();
        //DEBUG
        if (!takenPieces.isEmpty()) {
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