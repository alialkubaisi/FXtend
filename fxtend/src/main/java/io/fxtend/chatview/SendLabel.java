package io.fxtend.chatview;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import org.kordamp.ikonli.javafx.FontIcon;

public class SendLabel extends MessageBubble
{
    private final static String STATUS_DEFAULT_STYLE = "status-icon-default";
    private final FontIcon statusIcon = new FontIcon();

    public enum MessageStatus
    {
        SENT("status-sent-icon"),
        RECEIVED("status-received-icon"),
        READ("status-read-icon");

        private final String cssClass;

        MessageStatus(String cssClass)
        {
            this.cssClass = cssClass;
        }

        public String getCssClass()
        {
            return cssClass;
        }
    }

    public SendLabel(String sendText)
    {
        super(sendText, "label-sender", "timestamp-sender", "bubble-sender", Pos.CENTER_RIGHT);
        statusIcon.getStyleClass().add(STATUS_DEFAULT_STYLE);
    }

    public void updateMessageStatus(MessageStatus status)
    {
        statusContainer.getChildren().clear();
        statusContainer.getChildren().add(timestampLabel);
        statusContainer.getChildren().add(statusIcon);
        HBox.setMargin(statusIcon, new Insets(5, 0, 0, 2));
        statusIcon.getStyleClass().removeIf(sc -> !STATUS_DEFAULT_STYLE.equals(sc));
        statusIcon.getStyleClass().add(status.getCssClass());
    }
}
