package liam.connectfour.model;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    @Test
    void newBoard_isEmpty() {
        Board board = new Board();
        for(int i = 0; i < board.getRows(); i++)
        {
            for(int j = 0; j < board.getColumns(); j++)
            {
                assertEquals(Piece.EMPTY, board.getCell(i,j));
            }
        }
    }
    @Test
    void dropPiece_stacksCorrectlyInMultipleColumns() {
        Board board = new Board();

        board.dropPiece(0, Piece.RED);
        board.dropPiece(1, Piece.YELLOW);
        board.dropPiece(1, Piece.YELLOW);
        board.dropPiece(2, Piece.RED);

        assertEquals(Piece.RED, board.getCell(5, 0));
        assertEquals(Piece.YELLOW, board.getCell(5, 1));
        assertEquals(Piece.YELLOW, board.getCell(4, 1));
        assertEquals(Piece.RED, board.getCell(5, 2));
    }

    @Test
    void checkDraw_returnsTrueWhenBoardIsFull() {
        Board board = new Board();

        for (int col = 0; col < board.getColumns(); col++) {
            for (int i = 0; i < board.getRows(); i++) {
                board.dropPiece(col, (i % 2 == 0) ? Piece.RED : Piece.YELLOW);
            }
        }

        assertTrue(board.checkDraw());
    }

    @Test
    void checkDraw_returnsFalseWhenAnyCellIsEmpty() {
        Board board = new Board();

        for (int col = 0; col < board.getColumns(); col++) {
            for (int i = 0; i < board.getRows() - 1; i++) {
                board.dropPiece(col, Piece.RED);
            }
        }

        assertFalse(board.checkDraw());
    }


    @Test
    void one_missing_checkDraw() {
        Board board = new Board();
        for(int i = 0; i < board.getColumns(); i++)
        {
            for(int j = board.getRows() - 1; j >= 0; j--)
            {
                if(i == 0 && j == 0)
                {
                    continue;
                }
                if(j % 2 == 0)
                {
                    board.dropPiece(i,Piece.RED);
                }
                else board.dropPiece(i, Piece.YELLOW);
            }
        }
        assertFalse(board.checkDraw());
        board = new Board();
        for(int i = 0; i < board.getColumns(); i++)
        {
            for(int j = board.getRows() - 1; j >= 0; j--)
            {
                if(i == 3 && j == 5)
                {
                    continue;
                }
                if(j % 2 == 0)
                {
                    board.dropPiece(i,Piece.RED);
                }
                else board.dropPiece(i, Piece.YELLOW);
            }
        }
        assertFalse(board.checkDraw());
        board = new Board();
        for(int i = 0; i < board.getColumns(); i++)
        {
            for(int j = board.getRows() - 1; j >= 0; j--)
            {
                if(i == 6 && j == 5)
                {
                    continue;
                }
                if(j % 2 == 0)
                {
                    board.dropPiece(i,Piece.RED);
                }
                else board.dropPiece(i, Piece.YELLOW);
            }
        }
        assertFalse(board.checkDraw());
    }
    @Test
    void hasWinner_detectsVerticalWin() {
        Board board = new Board();

        board.dropPiece(1, Piece.RED);
        board.dropPiece(1, Piece.RED);
        board.dropPiece(1, Piece.RED);
        board.dropPiece(1, Piece.RED);

        assertTrue(board.hasWinner(Piece.RED));
    }

    @Test
    void hasWinner_detectsHorizontalWin() {
        Board board = new Board();

        board.dropPiece(0, Piece.RED);
        board.dropPiece(1, Piece.RED);
        board.dropPiece(2, Piece.RED);
        board.dropPiece(3, Piece.RED);

        assertTrue(board.hasWinner(Piece.RED));
    }

    @Test
    void hasWinner_detectsDiagonalUpRight() {
        Board board = new Board();

        board.dropPiece(0, Piece.YELLOW);
        board.dropPiece(0, Piece.YELLOW);
        board.dropPiece(0, Piece.YELLOW);
        board.dropPiece(0, Piece.RED);

        board.dropPiece(1, Piece.YELLOW);
        board.dropPiece(1, Piece.YELLOW);
        board.dropPiece(1, Piece.RED);

        board.dropPiece(2, Piece.YELLOW);
        board.dropPiece(2, Piece.RED);

        board.dropPiece(3, Piece.RED);

        assertTrue(board.hasWinner(Piece.RED));
    }

    @Test
    void hasWinner_detectsDiagonalDownRight() {
        Board board = new Board();

        board.dropPiece(0, Piece.RED);

        board.dropPiece(1, Piece.YELLOW);
        board.dropPiece(1, Piece.RED);

        board.dropPiece(2, Piece.YELLOW);
        board.dropPiece(2, Piece.YELLOW);
        board.dropPiece(2, Piece.RED);

        board.dropPiece(3, Piece.YELLOW);
        board.dropPiece(3, Piece.YELLOW);
        board.dropPiece(3, Piece.YELLOW);
        board.dropPiece(3, Piece.RED);

        assertTrue(board.hasWinner(Piece.RED));
    }

}
