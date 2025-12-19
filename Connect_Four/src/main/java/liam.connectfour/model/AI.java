package liam.connectfour.model;

import java.util.Random;

public class AI implements MoveStrategy{

    @Override
    public int chooseColumn(Board board) {
        Random random = new Random();
        int column;
        //TO DO

        do {
            column = random.nextInt(board.getColumns());
        } while (board.isColumnFull(column));
        return column;
    }
}
