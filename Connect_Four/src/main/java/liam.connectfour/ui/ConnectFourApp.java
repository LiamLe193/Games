package liam.connectfour.ui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import liam.connectfour.model.Board;

public class ConnectFourApp extends Application{
    @Override
    public void start(Stage stage) {

        Board board = new Board();
        BoardView boardView = new BoardView(board);

        Button resetButton = new Button("New Game");
        resetButton.setOnAction(e -> {
            board.reset();
            boardView.reset();
        });

        javafx.scene.layout.VBox root = new javafx.scene.layout.VBox(20);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(boardView, resetButton);

        Scene scene = new Scene(root, 800, 650);
        stage.setTitle("Connect Four");
        stage.setScene(scene);
        stage.show();
    }

}
