package main;

import Pieces.Piece;

import java.util.ArrayList;

public class GameManager {
    public final int SQUARE_COUNT = 8;
    final int SQUARE_SIZE = 80;
    final int WIDTH = SQUARE_SIZE * SQUARE_COUNT;
    final int HEIGTH = SQUARE_SIZE * SQUARE_COUNT;
    final int DARK = 0;
    final int LIGHT = 1;
    final int IDLE = 0;
    final int SELECTED = 1;
    int gamestate = IDLE;
    int score = 0;
    ArrayList <Piece> takenPieces = new ArrayList<>();
}
