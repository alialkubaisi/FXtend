package io.fxtend.chatview;

import io.fxtend.util.EPath;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Objects;

public class ChatView extends BorderPane
{
    private MessageView messageView;
    private TextField inputTextField;

    public ChatView()
    {
        this(null);
    }

    public ChatView(String title)
    {
        getStylesheets().add(Objects.requireNonNull(ChatView.class.getResource(EPath.CHAT_VIEW_STYLE.getPath())).toExternalForm());
        getStyleClass().add("chat-view");
        createTitleHeader(title);
        createMessageView();
        createInputArea();
    }

    private void createTitleHeader(String title)
    {
        if (title != null && !title.isEmpty())
        {
            HBox header = new HBox();
            header.setAlignment(Pos.CENTER);
            header.setMaxWidth(Double.MAX_VALUE);
            header.getStyleClass().add("header");

            Label labelTitle = new Label(title);
            labelTitle.getStyleClass().add("title");

            header.getChildren().add(labelTitle);
            this.setTop(header);
        }
    }

    private void createMessageView()
    {
        messageView = new MessageView();
        this.setCenter(messageView);
    }

    private void createInputArea()
    {
        HBox inputArea = new HBox();
        inputArea.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);
        inputArea.setPrefHeight(50);
        inputArea.setSpacing(15);

        inputTextField = new TextField();
        inputTextField.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        HBox.setHgrow(inputTextField, Priority.ALWAYS);

        Button buttonSend = new Button();
        buttonSend.getStyleClass().add("send-button");
        buttonSend.setGraphic(new FontIcon());
        buttonSend.setOnAction(actionEvent -> {
            sendMessage(inputTextField.getText());
            receiveMessage("Received");
        });

        inputArea.getChildren().addAll(inputTextField, buttonSend);
        this.setBottom(inputArea);
    }

    public void sendMessage(String message)
    {
        messageView.sendMessage(message);
        inputTextField.clear();
    }

    public void receiveMessage(String message)
    {
        messageView.receiveMessage(message);
        inputTextField.clear();
    }
}
