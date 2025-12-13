package liam.connectfour.model;

public class Board {
    private final int rows = 6;
    private final int columns = 7;
    private final Piece[][] grid;
    //private final List<List<piece>> availableMoves = new ArrayList<>();

    public Board() {
        grid = new Piece[rows][columns];
        initialize();
    }
    public void initialize(){
        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < columns; j++)
            {
                grid[i][j] = Piece.EMPTY;
            }
        }
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

    private boolean checkHorizontal(Piece piece) {
        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < columns - 4; j++)
            {
                if(grid[i][j] == piece
                        && grid[i][j + 1] == piece
                        && grid[i][j + 2] == piece
                        && grid[i][j + 3] == piece)
                {
                    return true;
                }
            }
        }
        return false;
    }
    private boolean checkVertical(Piece piece)
    {
        for(int i = 0; i < columns; i++)
        {
            for(int j = 0; j < rows - 4; j++)
            {
                if(grid[j][i] == piece
                        && grid[j + 1][i] == piece
                        && grid[j + 2][i] == piece
                        && grid[j + 3][i] == piece)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean checkDiagonal(Piece piece)
    {
        for(int i = 0; i < rows - 4; i++)
        {
            for(int j = 0; j < columns - 4; j++)
            {
                if(grid[i][j] == piece && grid[i+1][j+1] == piece && grid[i+2][j+2] == piece && grid[i+3][j+3] == piece)
                {
                    return true;
                }
            }
        }
        for(int i = rows - 4; i >= 0; i--)
        {
            for(int j = columns - 4; j >= 0; j--)
            {
                if(grid[i][j] == piece && grid[i-1][j-1] == piece && grid[i-2][j-2] == piece && grid[i-3][j-3] == piece)
                {
                    return true;
                }
            }
        }
        return false;
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
        return (checkDiagonal(piece) && checkHorizontal(piece) && checkVertical(piece));
    }


    public int getRows() {
        return rows;
    }
    public int getColumns() {
        return columns;
    }
}
