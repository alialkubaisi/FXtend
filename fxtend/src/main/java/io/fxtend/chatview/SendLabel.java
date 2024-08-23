package io.fxtend.chatview;

import javafx.geometry.Pos;

public class SendLabel extends MessageBubble
{
    public SendLabel(String sendText)
    {
        super(sendText, "label-sender", "timestamp-sender", "bubble-sender", Pos.CENTER_RIGHT);
    }
}
