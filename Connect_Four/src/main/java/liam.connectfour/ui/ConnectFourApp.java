package liam.connectfour.ui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import liam.connectfour.model.Board;

public class ConnectFourApp extends Application{
    @Override
    public void start(Stage stage) {

        Board board = new Board();
        BoardView boardView = new BoardView(board);

        StackPane root = new StackPane();
        root.setAlignment(Pos.CENTER);
        root.getChildren().add(boardView);

        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Connect Four");
        stage.setScene(scene);
        stage.show();
    }
}
