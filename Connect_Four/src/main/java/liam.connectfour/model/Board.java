package liam.connectfour.model;

import java.util.Collections;
import java.util.List;
import liam.connectfour.model.Position;

public class Board {
    private static final int rows = 6;
    private static final int columns = 7;
    private final Piece[][] grid;

    public Board() {
        grid = new Piece[rows][columns];
        initialize();
    }

    private void initialize() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                grid[i][j] = Piece.EMPTY;
            }
        }
    }
    public Piece getCell(int row, int col)
    {
        return grid[row][col];
    }
    public void reset() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                grid[r][c] = Piece.EMPTY;
            }
        }
    }

    public List<Position> getWinningPositions(Piece piece) {

        // 1. Horizontal
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col <= columns - 4; col++) {
                if (grid[row][col] == piece &&
                        grid[row][col + 1] == piece &&
                        grid[row][col + 2] == piece &&
                        grid[row][col + 3] == piece) {

                    return List.of(
                            new Position(row, col),
                            new Position(row, col + 1),
                            new Position(row, col + 2),
                            new Position(row, col + 3)
                    );
                }
            }
        }

        // 2. Vertical
        for (int col = 0; col < columns; col++) {
            for (int row = 0; row <= rows - 4; row++) {
                if (grid[row][col] == piece &&
                        grid[row + 1][col] == piece &&
                        grid[row + 2][col] == piece &&
                        grid[row + 3][col] == piece) {

                    return List.of(
                            new Position(row, col),
                            new Position(row + 1, col),
                            new Position(row + 2, col),
                            new Position(row + 3, col)
                    );
                }
            }
        }

        // 3. Diagonal (down-right)
        for (int row = 0; row <= rows - 4; row++) {
            for (int col = 0; col <= columns - 4; col++) {
                if (grid[row][col] == piece &&
                        grid[row + 1][col + 1] == piece &&
                        grid[row + 2][col + 2] == piece &&
                        grid[row + 3][col + 3] == piece) {

                    return List.of(
                            new Position(row, col),
                            new Position(row + 1, col + 1),
                            new Position(row + 2, col + 2),
                            new Position(row + 3, col + 3)
                    );
                }
            }
        }

        // 4. Diagonal (up-right)
        for (int row = 3; row < rows; row++) {
            for (int col = 0; col <= columns - 4; col++) {
                if (grid[row][col] == piece &&
                        grid[row - 1][col + 1] == piece &&
                        grid[row - 2][col + 2] == piece &&
                        grid[row - 3][col + 3] == piece) {

                    return List.of(
                            new Position(row, col),
                            new Position(row - 1, col + 1),
                            new Position(row - 2, col + 2),
                            new Position(row - 3, col + 3)
                    );
                }
            }
        }

        return Collections.emptyList();
    }
    public boolean dropPiece(int column, Piece piece)
    {
        if(column < 0 || column >= columns)
        {
            return false;
        }
        for(int i = rows - 1; i >= 0; i--)
        {
            if(grid[i][column] == Piece.EMPTY)
            {
                grid[i][column] = piece;
                return true;
            }
        }
        return false;
    }
    public int getNextAvailableRow(int column) {
        for (int row = rows - 1; row >= 0; row--) {
            if (grid[row][column] == Piece.EMPTY) {
                return row;
            }
        }
        return -1;
    }
    public boolean checkDraw() {
        for(int i = 0; i < columns; i++)
        {
            if(grid[0][i] == Piece.EMPTY)
            {
                return false;
            }
        }
        return true;
    }
    public boolean hasWinner(Piece piece)
    {
        return !getWinningPositions(piece).isEmpty();
    }
    public boolean isColumnFull(int col)
    {
        return grid[0][col] != Piece.EMPTY;
    }
    public int getRows() {
        return rows;
    }
    public int getColumns() {
        return columns;
    }
}
