package io.fxtend.chatview;

import io.fxtend.util.EPath;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Objects;
import java.util.function.Consumer;

public class ChatView extends BorderPane
{
    private MessageView messageView;
    private TextField inputTextField;
    private Consumer<String> onSendMessage;

    public enum Style
    {
        DEFAULT,
        MODERN,
        DARK
    }

    public ChatView()
    {
        this(null, Style.DEFAULT);
    }

    public ChatView(String title)
    {
        this(title, Style.DEFAULT);
    }

    public ChatView(Style style)
    {
        this(null, style);
    }

    public ChatView(String title, Style style)
    {
        applyStyle(style);
        getStyleClass().add("chat-view");
        createTitleHeader(title);
        createMessageView();
        createInputArea();
    }

    public void createTitleHeader(String title)
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
        inputArea.getStyleClass().add("input-area");

        inputTextField = new TextField();
        inputTextField.setOnAction(actionEvent -> handleSendAction());
        HBox.setHgrow(inputTextField, Priority.ALWAYS);

        Button buttonSend = new Button();
        buttonSend.getStyleClass().add("send-button");
        buttonSend.setGraphic(new FontIcon());
        buttonSend.setOnAction(actionEvent -> handleSendAction());

        inputArea.getChildren().addAll(inputTextField, buttonSend);
        this.setBottom(inputArea);
    }

    private void handleSendAction()
    {
        String message = inputTextField.getText();
        if (!message.isEmpty())
        {
            sendMessage(message);
            inputTextField.clear();
            inputTextField.requestFocus();

            if (onSendMessage != null)
            {
                onSendMessage.accept(message);
            }
        }
    }

    public void sendMessage(String message)
    {
        messageView.sendMessage(message);
    }

    public void receiveMessage(String message)
    {
        receiveMessage(message, null);
    }

    public void receiveMessage(String message, Image logoImage)
    {
        messageView.receiveMessage(message, logoImage);
    }

    public void applyStyle(Style style)
    {
        String styleSheet = switch (style)
        {
            case MODERN -> EPath.CHAT_VIEW_MODERN_STYLE.getPath();
            case DARK -> EPath.CHAT_VIEW_DARK_STYLE.getPath();
            default -> EPath.CHAT_VIEW_DEFAULT_STYLE.getPath();
        };
        getStylesheets().clear();
        getStylesheets().add(Objects.requireNonNull(ChatView.class.getResource(styleSheet)).toExternalForm());
    }

    public void updateLastMessagesStatus(SendLabel.MessageStatus status)
    {
        messageView.updateLastMessagesStatus(status);
    }

    public void setOnSendMessage(Consumer<String> handler)
    {
        this.onSendMessage = handler;
    }

    public TextField getInputTextField()
    {
        return inputTextField;
    }
}
