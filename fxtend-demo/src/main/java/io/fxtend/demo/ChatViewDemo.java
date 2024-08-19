package io.fxtend.demo;

import io.fxtend.chatview.ChatView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChatViewDemo extends Application
{
    @Override
    public void start(Stage primaryStage)
    {
        ChatView chatView = new ChatView();

        Scene scene = new Scene(chatView, 400, 600);

        primaryStage.setTitle("ChatView Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
