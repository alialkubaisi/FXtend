package io.fxtend.chatview;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class ReceiveLabel extends HBox implements MessageLabel
{
    private final Label receiveLabel = new Label();

    public ReceiveLabel(String responseText)
    {
        receiveLabel.setText(responseText);
        receiveLabel.setWrapText(true);
        receiveLabel.getStyleClass().add("label-receiver");

        setAlignment(Pos.CENTER_LEFT);
        getChildren().add(receiveLabel);
    }

    @Override
    public Label getMessageLabel()
    {
        return receiveLabel;
    }
}
