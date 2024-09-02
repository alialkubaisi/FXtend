package io.fxtend.demo;

import io.fxtend.chatview.ChatView;
import io.fxtend.chatview.SendLabel;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Objects;

public class ChatViewDemo extends Application
{
    private final Image logoImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("profile.jpg")));
    private ChatView chatView1, chatView2;
    private CheckBox imageCheckBox;

    @Override
    public void start(Stage primaryStage)
    {
        final BorderPane root = new BorderPane();

        chatView1 = new ChatView();
        chatView2 = new ChatView();

        addDemoMessages();

        String borderStyle = "-fx-border-color: black;";
        chatView1.setStyle(borderStyle);
        chatView2.setStyle(borderStyle);

        chatView1.setOnSendMessage(message -> sendMessage(chatView2, message, imageCheckBox.isSelected()));
        chatView2.setOnSendMessage(message -> sendMessage(chatView1, message, imageCheckBox.isSelected()));

        final HBox chatViewContainer = new HBox(chatView1, chatView2);
        chatViewContainer.setSpacing(100);
        chatViewContainer.setPadding(new Insets(20));
        chatViewContainer.setAlignment(Pos.CENTER);
        HBox.setHgrow(chatView1, Priority.ALWAYS);
        HBox.setHgrow(chatView2, Priority.ALWAYS);
        root.setCenter(chatViewContainer);
        root.setBottom(getOptionPane());

        primaryStage.setTitle("ChatView Demo");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    private void addDemoMessages()
    {
        chatView1.sendMessage("Hello! How are you doing today?");

        chatView2.receiveMessage("Hello! How are you doing today?");
        chatView2.sendMessage("I'm doing well, thank you! How about you?");

        chatView1.receiveMessage("I'm doing well, thank you! How about you?");

        chatView1.sendMessage("I'm great, just working on a new project.");
        chatView2.receiveMessage("I'm great, just working on a new project.");

        chatView2.sendMessage("That sounds interesting! What are you working on?");
        chatView1.receiveMessage("That sounds interesting! What are you working on?", logoImage);

        chatView1.sendMessage("I'm creating a new chat interface for my app using JavaFX!");
        chatView2.receiveMessage("I'm creating a new chat interface for my app using JavaFX!");

        chatView2.sendMessage("That's awesome! 😃 I can't wait to see it in action.");
        chatView1.receiveMessage("That's awesome! 😃 I can't wait to see it in action.");

        chatView1.sendMessage("Thanks! I'll share it with you once it's done.");
        chatView2.receiveMessage("Thanks! I'll share it with you once it's done.");

        chatView1.updateLastMessagesStatus(SendLabel.MessageStatus.READ);
    }

    private GridPane getOptionPane()
    {
        ComboBox<ChatView.Style> styleComboBox = new ComboBox<>();
        styleComboBox.getItems().addAll(ChatView.Style.values());
        styleComboBox.setValue(ChatView.Style.DEFAULT);
        styleComboBox.setOnAction(e -> applyStyle(styleComboBox.getValue()));

        final Label styleLabel = new Label("ChatView style: ");

        imageCheckBox = new CheckBox("Add Image to Receive Bubble");

        CheckBox headerCheckBox = new CheckBox("Add Header");
        headerCheckBox.selectedProperty().addListener((observableValue, wasSelected, isSelected) -> {
            chatView1.createTitleHeader("Chat view 1");
            chatView2.createTitleHeader("Chat view 2");
        });

        GridPane optionsPane = new GridPane();
        optionsPane.setHgap(10);
        optionsPane.setVgap(20);

        optionsPane.setAlignment(Pos.CENTER);
        optionsPane.setPadding(new Insets(20));
        optionsPane.add(styleLabel, 0, 0);
        optionsPane.add(styleComboBox, 1, 0);
        optionsPane.add(imageCheckBox, 1, 1);
        optionsPane.add(headerCheckBox, 1, 2);

        return optionsPane;
    }

    private void sendMessage(ChatView receiver, String message, boolean addImage)
    {
        if (!message.isEmpty())
        {
            if (addImage)
            {
                receiver.receiveMessage(message, logoImage);
            }
            else
            {
                receiver.receiveMessage(message);
            }
        }
    }

    private void applyStyle(ChatView.Style style)
    {
        chatView1.applyStyle(style);
        chatView2.applyStyle(style);
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
