package io.fxtend.chatview;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public abstract class MessageBubble extends HBox implements MessageLabel
{
    private final Label messageLabel = new Label();

    public MessageBubble(String messageText, String labelStyleClass, String timestampStyleClass, String bubbleStyleClass, Pos alignment)
    {
        messageLabel.setText(messageText);
        messageLabel.setWrapText(true);
        messageLabel.getStyleClass().add(labelStyleClass);

        Label timestampLabel = new Label(getCurrentTime());
        timestampLabel.getStyleClass().add(timestampStyleClass);

        VBox bubble = new VBox(messageLabel, timestampLabel);
        bubble.setAlignment(Pos.BOTTOM_RIGHT);
        bubble.getStyleClass().add(bubbleStyleClass);

        setAlignment(alignment);
        getChildren().add(bubble);
    }

    @Override
    public Label getMessageLabel()
    {
        return messageLabel;
    }

    protected String getCurrentTime()
    {
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return currentTime.format(formatter);
    }
}
