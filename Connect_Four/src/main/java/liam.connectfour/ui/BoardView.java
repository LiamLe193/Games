package liam.connectfour.ui;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import liam.connectfour.model.Board;
import liam.connectfour.model.Piece;

public class BoardView extends StackPane {
    private static final int CELL_RADIUS = 30;
    private static final int GAP = 10;
    private static final int PADDING = 20;
    private static final int CELL_SIZE = 50;
    private final StackPane animationLayer = new StackPane();
    private final GridPane grid = new GridPane();

    public BoardView(Board board) {
        int boardWidth = board.getColumns() * (CELL_RADIUS * 2) + (board.getColumns() - 1) * GAP + PADDING * 2;
        int boardHeight = board.getRows() * (CELL_RADIUS * 2)+ (board.getRows() - 1) * GAP + PADDING * 2;
        // 1. Create the grid (holes)
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        render(board, grid); // adjust render signature if needed

        // 2. Create blue board background
        StackPane boardBackground = new StackPane(grid);
        boardBackground.setPrefSize(boardWidth, boardHeight);
        boardBackground.setMaxSize(boardWidth, boardHeight);
        boardBackground.setStyle("""
        -fx-background-color: #1E5BFF;
        -fx-padding: 20;
        -fx-background-radius: 15;
        """);


        // 4. Put board in this BoardView
        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(boardBackground, animationLayer);

        animationLayer.setPickOnBounds(false);
    }

    private double boardWidth() {
        return getWidth() > 0 ? getWidth() : getPrefWidth();
    }

    private double boardHeight() {
        return getHeight() > 0 ? getHeight() : getPrefHeight();
    }

    private void render(Board board, GridPane grid)
    {
        for (int row = 0; row < board.getRows(); row++)
        {
            for (int col = 0; col < board.getColumns(); col++)
            {
                Circle c = new Circle(30);
                c.setFill(Color.LIGHTGRAY);
                grid.add(c, col, row);
            }
        }
    }


    private Color colorFor(Piece piece) {
        return switch (piece) {
            case RED -> Color.RED;
            case YELLOW -> Color.GOLD;
            case EMPTY -> Color.LIGHTGRAY;
        };
    }
    private void placeDiscInGrid(int col, int row, Piece piece) {
        Circle disc = new Circle(30);
        disc.setFill(piece == Piece.RED ? Color.RED : Color.YELLOW);
        grid.add(disc, col, row);
    }


    public void animateDrop(int column, int targetRow, Piece piece) {

        Circle disc = new Circle(CELL_RADIUS);
        disc.setFill(colorFor(piece));

        double startY = -boardHeight() / 2.0;
        double endY = targetRow * (CELL_RADIUS * 2 + GAP)
                - boardHeight() / 2.0
                + PADDING
                + CELL_RADIUS;

        double startX = column * (CELL_RADIUS * 2 + GAP)
                - boardWidth() / 2.0
                + PADDING
                + CELL_RADIUS;

        disc.setTranslateX(startX);
        disc.setTranslateY(startY);

        animationLayer.getChildren().add(disc);

        TranslateTransition drop = new TranslateTransition(Duration.millis(350), disc);
        drop.setToY(endY);
        drop.setInterpolator(Interpolator.EASE_IN);

        drop.setOnFinished(e -> {
            animationLayer.getChildren().remove(disc);
            placeDiscInGrid(column, targetRow, piece);
        });

        drop.play();
    }

}

