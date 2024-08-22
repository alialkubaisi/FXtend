package io.fxtend.chatview;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class SendLabel extends HBox implements MessageLabel
{
    private final Label sendLabel = new Label();

    public SendLabel(String sendText)
    {
        sendLabel.setText(sendText);
        sendLabel.setWrapText(true);
        sendLabel.getStyleClass().add("label-sender");

        setAlignment(Pos.CENTER_RIGHT);
        this.getChildren().add(sendLabel);
    }

    @Override
    public Label getMessageLabel()
    {
        return sendLabel;
    }
}
