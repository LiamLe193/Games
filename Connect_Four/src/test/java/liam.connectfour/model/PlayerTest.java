package liam.connectfour.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;


class PlayerTest {
    @Test
    void playerStoresAssignedPiece() {
        MoveStrategy dummyStrategy = board -> 0;
        Player player = new Player(Piece.RED, dummyStrategy);
        assertEquals(Piece.RED, player.getPiece());
    }

    @Test
    void playerStoresAssignedMoveStrategy() {
        MoveStrategy dummyStrategy = board -> 3;
        Player player = new Player(Piece.YELLOW, dummyStrategy);
        assertSame(dummyStrategy, player.getMovesStrategy());
    }
}
