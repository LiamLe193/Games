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
}
