package liam.connectfour.model;

import liam.connectfour.model.Board;
import liam.connectfour.model.Piece;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AITest {

    @Test
    void chooseColumnReturnsValidColumn() {
        Board board = new Board();
        MoveStrategy ai = new AI();

        int column = ai.chooseColumn(board);

        assertTrue(column >= 0 && column < board.getColumns());
    }

    @Test
    void chooseColumnNeverReturnsFullColumn() {
        Board board = new Board();
        MoveStrategy ai = new AI();

        // Fill column 0
        for (int i = 0; i < board.getRows(); i++) {
            board.dropPiece(0, Piece.RED);
        }

        int column = ai.chooseColumn(board);

        assertNotEquals(0, column);
    }

    @Test
    void chooseColumnDoesNotModifyBoard() {
        Board board = new Board();
        MoveStrategy ai = new AI();

        ai.chooseColumn(board);

        // Board should still be empty
        for (int r = 0; r < board.getRows(); r++) {
            for (int c = 0; c < board.getColumns(); c++) {
                assertEquals(Piece.EMPTY, board.getCell(r, c));
            }
        }
    }
}

