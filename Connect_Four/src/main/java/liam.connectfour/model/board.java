package liam.connectfour.model;

public class board {
    private int row = 6;
    private int column = 7;
    int[][] board = new int[row][column];

    public int[][] getBoard() {
        return board;
    }
    public void checkWin(int[][] board) {

    }
    public void checkDraw(int[][] board) {

    }
}
