package io.fxtend.chatview;

import javafx.geometry.Pos;

public class ReceiveLabel extends MessageBubble
{
    public ReceiveLabel(String responseText)
    {
        super(responseText, "label-receiver", "timestamp-receiver", "bubble-receiver", Pos.CENTER_LEFT);
    }
}
