package io.fxtend.chatview;

import javafx.application.Platform;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

public class MessageView extends ScrollPane
{
    private final VBox messageViewContent;

    public MessageView()
    {
        setHbarPolicy(ScrollBarPolicy.NEVER);
        setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        setFitToWidth(true);
        setMaxHeight(Double.MAX_VALUE);

        messageViewContent = new VBox();
        messageViewContent.setSpacing(15);
        messageViewContent.prefWidthProperty().bind(widthProperty());
        messageViewContent.getStyleClass().add("message-view-content");

        setContent(messageViewContent);
    }

    public void sendMessage(String message)
    {
        SendLabel sendLabel = new SendLabel(message);
        messageViewContent.getChildren().add(sendLabel);
        sendLabel.resizeMessageContainer(messageViewContent);
        scrollToBottom();
    }

    public void receiveMessage(String message)
    {
        receiveMessage(message, null);
    }

    public void receiveMessage(String message, Image logo)
    {
        ReceiveLabel receiveLabel = new ReceiveLabel(message);
        if (logo != null)
        {
            receiveLabel.initLogoImage(logo);
        }
        messageViewContent.getChildren().add(receiveLabel);
        receiveLabel.resizeMessageContainer(messageViewContent);
        scrollToBottom();
    }

    private void scrollToBottom()
    {
        Platform.runLater(() -> setVvalue(1.0));
    }
}
