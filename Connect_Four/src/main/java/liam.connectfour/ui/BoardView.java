package liam.connectfour.ui;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import liam.connectfour.model.Board;
import liam.connectfour.model.Piece;
import liam.connectfour.model.Position;

import java.util.List;

public class BoardView extends StackPane {

    private static final int CELL_RADIUS = 30;
    private static final int GAP = 10;
    private static final int PADDING = 20;
    private static final int CELL_SIZE = 50;
    private final StackPane animationLayer = new StackPane();
    private final GridPane grid = new GridPane();
    private boolean animationInProgress = false;
    private final Board board;
    private boolean gameOver = false;
    private Piece currentPiece = Piece.RED;
    private final Circle previewDisc = new Circle(CELL_RADIUS);
    private final Circle[][] cells;
    private final Circle[][] discNodes;
    private final StackPane overlay = new StackPane();
    private final javafx.scene.control.Label resultLabel = new javafx.scene.control.Label();
    private Label announcementLabel;



    public BoardView(Board board) {
        this.board = board;
        discNodes = new Circle[board.getRows()][board.getColumns()];
        cells = new Circle[board.getRows()][board.getColumns()];
        int boardWidth = board.getColumns() * (CELL_RADIUS * 2) + (board.getColumns() - 1) * GAP + PADDING * 2;
        int boardHeight = board.getRows() * (CELL_RADIUS * 2)+ (board.getRows() - 1) * GAP + PADDING * 2;

        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        render(board, grid);
        previewDisc.setOpacity(0.4);
        previewDisc.setVisible(false);
        grid.getChildren().add(previewDisc);

        StackPane boardBackground = new StackPane(grid);
        boardBackground.setPrefSize(boardWidth, boardHeight);
        boardBackground.setMaxSize(boardWidth, boardHeight);
        boardBackground.setStyle("""
        -fx-background-color: #1E5BFF;
        -fx-padding: 20;
        -fx-background-radius: 15;
        """);
        overlay.setStyle("""
        -fx-background-color: rgba(0, 0, 0, 0.6);
        """);
        overlay.setVisible(false);
        resultLabel.setTextFill(Color.WHITE);
        resultLabel.setStyle("""
        -fx-font-size: 36px;
        -fx-font-weight: bold;
        """);
        overlay.getChildren().add(resultLabel);

        this.getChildren().add(overlay);

        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(boardBackground, animationLayer);

        animationLayer.setPickOnBounds(false);
        enableColumnClicks(board);


    }
    public void reset() {
        gameOver = false;
        animationInProgress = false;
        currentPiece = Piece.RED;

        animationLayer.getChildren().clear();
        grid.getChildren().clear();

        if (announcementLabel != null) {
            this.getChildren().remove(announcementLabel);
            announcementLabel = null;
        }

        render(board, grid);
        enableColumnClicks(board);
    }




    private void showWinner(Piece piece) {
        announcementLabel = new Label(
                (piece == Piece.RED ? "Red" : "Yellow") + " Wins!"
        );

        announcementLabel.setStyle("""
        -fx-font-size: 36px;
        -fx-font-weight: bold;
        -fx-text-fill: white;
        -fx-background-color: rgba(0,0,0,0.6);
        -fx-padding: 20;
        -fx-background-radius: 10;
    """);

        StackPane.setAlignment(announcementLabel, Pos.TOP_CENTER);
        this.getChildren().add(announcementLabel);
    }



    public void showDraw() {
        resultLabel.setText("DRAW");
        overlay.setVisible(true);
    }


    private void enableColumnClicks(Board board) {
        for (int col = 0; col < board.getColumns(); col++) {
            final int column = col;

            // Transparent click area covering a whole column
            Rectangle clickZone = new Rectangle(
                    CELL_RADIUS * 2 + GAP,
                    board.getRows() * (CELL_RADIUS * 2 + GAP)
            );

            clickZone.setFill(Color.TRANSPARENT);

            clickZone.setOnMouseClicked(e -> handleColumnClick(column));
            clickZone.setOnMouseEntered(e -> showPreview(column));
            clickZone.setOnMouseExited(e -> hidePreview());

            GridPane.setColumnIndex(clickZone, col);
            GridPane.setRowIndex(clickZone, 0);
            GridPane.setRowSpan(clickZone, board.getRows());

            grid.getChildren().add(clickZone);
        }
    }
    private void handleColumnClick(int column) {
        if (animationInProgress || gameOver) return;

        int row = board.getNextAvailableRow(column);
        if (row == -1) return;

        Piece piece = currentPiece;
        board.dropPiece(column, piece);
        animateDrop(column, row, piece);
    }

    private void showPreview(int column) {
        if (animationInProgress) return;

        previewDisc.setFill(colorFor(currentPiece));
        GridPane.setColumnIndex(previewDisc, column);
        GridPane.setRowIndex(previewDisc, 0);
        previewDisc.setVisible(true);
    }

    private void hidePreview() {
        previewDisc.setVisible(false);
    }

    private void render(Board board, GridPane grid) {
        grid.getChildren().clear();

        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getColumns(); col++) {

                Circle hole = new Circle(CELL_RADIUS);
                hole.setFill(Color.LIGHTGRAY);

                cells[row][col] = hole;
                discNodes[row][col] = null; // ⬅️ CRITICAL

                grid.add(hole, col, row);
            }
        }
    }

    public void highlightWin(List<Position> winningPositions) {
        for (Position pos : winningPositions) {
            Circle c = cells[pos.row()][pos.col()];
            c.setStroke(Color.LIGHTGREEN);
            c.setStrokeWidth(0.5);
        }
    }

    private Color winningColor(Piece piece) {
        return piece == Piece.RED
                ? Color.DARKRED
                : Color.GOLDENROD;
    }


    private Color colorFor(Piece piece) {
        return switch (piece) {
            case RED -> Color.RED;
            case YELLOW -> Color.GOLD;
            case EMPTY -> Color.LIGHTGRAY;
        };
    }
    private void placeDiscInGrid(int col, int row, Piece piece) {
        Circle disc = new Circle(CELL_RADIUS);
        disc.setFill(colorFor(piece));
        grid.add(disc, col, row);
        discNodes[row][col] = disc;
    }

    public void markWinningDiscs(List<Position> positions, Piece piece) {
        Color winColor = winningColor(piece);

        for (Position p : positions) {
            Circle disc = discNodes[p.row()][p.col()];
            if (disc != null) {
                disc.setFill(winColor);
            }
        }
    }



    public void animateDrop(int column, int targetRow, Piece piece) {

        animationInProgress = true;

        Circle disc = new Circle(CELL_RADIUS);
        disc.setFill(colorFor(piece));
        disc.setTranslateY(- (targetRow + 1) * (CELL_RADIUS * 2 + GAP));

        grid.add(disc, column, targetRow);
        discNodes[targetRow][column] = disc;


        TranslateTransition drop = new TranslateTransition(Duration.millis(350), disc);

        drop.setToY(0);
        drop.setInterpolator(Interpolator.EASE_IN);

        drop.setOnFinished(e -> {
            animationInProgress = false;

            // ✅ NOW check for win — AFTER animation finishes
            var win = board.getWinningPositions(piece);
            if (!win.isEmpty()) {
                markWinningDiscs(win, piece);
                showWinner(piece);   // ✅ announce winner
                gameOver = true;
                return;
            }

            if (board.checkDraw()) {
                showDraw();
                gameOver = true;
                return;
            }

            // Only switch turn if game continues
            currentPiece = (currentPiece == Piece.RED)
                    ? Piece.YELLOW
                    : Piece.RED;
        });

        drop.play();
    }


}

