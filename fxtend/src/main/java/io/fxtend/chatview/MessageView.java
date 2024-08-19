package io.fxtend.chatview;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class MessageView extends ScrollPane
{
    private final VBox messageViewContent;

    public MessageView()
    {
        setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        setMaxHeight(Double.MAX_VALUE);

        messageViewContent = new VBox();
        messageViewContent.setSpacing(15);
        messageViewContent.prefWidthProperty().bind(widthProperty());

        setContent(messageViewContent);
        vvalueProperty().bind(messageViewContent.heightProperty());
    }

    public void sendMessage(String message)
    {
        SendLabel sendLabel = new SendLabel(message);
        messageViewContent.getChildren().add(sendLabel);
    }

    public void receiveMessage(String message)
    {
        ReceiveLabel receiveLabel = new ReceiveLabel(message);
        messageViewContent.getChildren().add(receiveLabel);
    }
}
