package io.fxtend.demo;

import io.fxtend.kanban.KanbanBoard;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class KanbanDemo extends Application
{
    @Override
    public void start(Stage stage)
    {
        final VBox root = new VBox();
        KanbanBoard board = new KanbanBoard();
        VBox.setVgrow(board, Priority.ALWAYS);
        root.getChildren().add(board);
        final Scene scene = new Scene(root, 800,600);
        stage.setScene(scene);
        stage.setTitle("Kanban Demo");
        stage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
