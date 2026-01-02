package liam.connectfour.controller;

import liam.connectfour.model.Board;
import liam.connectfour.model.MoveStrategy;
import liam.connectfour.model.Piece;
import liam.connectfour.model.Player;

import java.util.Random;

public class GameController {

    private final Board board;
    private final Player redPlayer;
    private final Player yellowPlayer;

    private Player currentPlayer;
    private Player otherPlayer;
    public GameController(MoveStrategy redStrategy, MoveStrategy yellowStrategy)
    {
        this.board = new Board();
        this.redPlayer = new Player(Piece.RED, redStrategy);
        this.yellowPlayer = new Player(Piece.YELLOW, yellowStrategy);
        chooseFirstPlayer();
    }
    private void chooseFirstPlayer()
    {
        if (new Random().nextBoolean())
        {
            currentPlayer = redPlayer;
            otherPlayer = yellowPlayer;
        }
        else
        {
            currentPlayer = yellowPlayer;
            otherPlayer = redPlayer;
        }
    }

    private boolean executeTurn()
    {
        int column = currentPlayer.getMovesStrategy().chooseColumn(board);
        return board.dropPiece(column, currentPlayer.getPiece());
    }
    private void switchPlayer()
    {
        Player temp = currentPlayer;
        currentPlayer = otherPlayer;
        otherPlayer = temp;
    }
    private boolean gameEnd()
    {
        return board.hasWinner(currentPlayer.getPiece()) || board.checkDraw();
    }
    public void startGame() {
        while (!gameEnd()) {
            if (executeTurn()) {
                if (!gameEnd()) {
                    switchPlayer();
                }
            }
        }
    }
}
