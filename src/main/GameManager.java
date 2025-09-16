package main;

import Pieces.Piece;

import java.util.ArrayList;

public class GameManager {
    public final int SQUARE_COUNT = 8;
    final int SQUARE_SIZE = 100;
    final int DARK = 0;
    final int LIGHT = 1;
    final int IDLE = 0;
    final int SELECTED = 1;
    public final int startColor = LIGHT;
    int gamestate = IDLE;
    int score = 0;
    ArrayList<Piece> takenPieces = new ArrayList<>();
}
