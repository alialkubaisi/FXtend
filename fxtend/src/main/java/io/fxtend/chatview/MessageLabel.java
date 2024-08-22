package io.fxtend.chatview;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public interface MessageLabel
{
    double LABEL_WIDTH_RATIO = 0.8;

    default void resizeMessageContainer(Pane parentPane)
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

    Label getMessageLabel();
}
