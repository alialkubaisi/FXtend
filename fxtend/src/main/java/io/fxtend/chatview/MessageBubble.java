package io.fxtend.chatview;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public abstract class MessageBubble extends HBox
{
    private final static double LABEL_WIDTH_RATIO = 0.8;
    private final Label messageLabel = new Label();
    protected final HBox statusContainer = new HBox();
    private final VBox bubble = new VBox(messageLabel, statusContainer);
    protected Label timestampLabel;

    public MessageBubble(String messageText, String labelStyleClass, String timestampStyleClass, String bubbleStyleClass, Pos alignment)
    {
        messageLabel.setText(messageText);
        messageLabel.setWrapText(true);
        messageLabel.getStyleClass().add(labelStyleClass);

        timestampLabel = new Label(getCurrentTime());
        timestampLabel.getStyleClass().add(timestampStyleClass);

        statusContainer.setAlignment(Pos.CENTER_RIGHT);
        statusContainer.getChildren().addAll(timestampLabel);

        bubble.setAlignment(Pos.BOTTOM_RIGHT);
        bubble.getStyleClass().add(bubbleStyleClass);

        setAlignment(alignment);
        getChildren().add(bubble);
    }

    protected void resizeMessageContainer(Pane parentPane)
    {
        Label messageLabel = getMessageLabel();
        messageLabel.setMaxWidth(parentPane.getWidth() * LABEL_WIDTH_RATIO);
        parentPane.widthProperty().addListener((observableValue, oldWidth, newWidth) -> {
            double newWidthValue = newWidth.doubleValue();
            if (newWidthValue > 0)
            {
                messageLabel.setMaxWidth(newWidth.doubleValue() * LABEL_WIDTH_RATIO);
            }
        });
    }

    private String getCurrentTime()
    {
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return currentTime.format(formatter);
    }

    public Label getMessageLabel()
    {
        return messageLabel;
    }

    public VBox getBubble()
    {
        return bubble;
    }
}
