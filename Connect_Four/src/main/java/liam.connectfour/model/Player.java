package liam.connectfour.model;

public class Player implements MoveStrategy {

    private final Piece piece;
    private final MoveStrategy moveStrategy;
    public Player(Piece piece, MoveStrategy moves)
    {
        this.piece = piece;
        this.moveStrategy = moves;
    }
    public MoveStrategy getMovesStrategy() {
        return moveStrategy;
    }
    public Piece getPiece() {
        return piece;
    }
    @Override
    public int chooseColumn(Board board) {
        //TO DO
        return 0;
    }

}
