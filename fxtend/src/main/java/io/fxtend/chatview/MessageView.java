package io.fxtend.chatview;

import javafx.application.Platform;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

import java.util.HashSet;
import java.util.Set;

public class MessageView extends ScrollPane
{
    private final VBox messageViewContent;
    private final Set<SendLabel> lastMessages = new HashSet<>();

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
        lastMessages.add(sendLabel);
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

    public void updateLastMessagesStatus(SendLabel.MessageStatus messageStatus)
    {
        if (SendLabel.MessageStatus.SENT.equals(messageStatus))
        {
            lastMessages.forEach(sendLabel -> sendLabel.updateMessageStatus(messageStatus));
        }
        else if (SendLabel.MessageStatus.RECEIVED.equals(messageStatus))
        {
            lastMessages.stream()
                    .filter(sendLabel -> sendLabel.getMessageStatus() == null || SendLabel.MessageStatus.SENT.equals(sendLabel.getMessageStatus()))
                    .forEach(sendLabel -> sendLabel.updateMessageStatus(messageStatus));
        }
        else if (SendLabel.MessageStatus.READ.equals(messageStatus))
        {
            lastMessages.stream()
                    .filter(sendLabel -> sendLabel.getMessageStatus() == null || SendLabel.MessageStatus.RECEIVED.equals(
                            sendLabel.getMessageStatus()))
                    .forEach(sendLabel -> sendLabel.updateMessageStatus(messageStatus));
        }
    }
}
