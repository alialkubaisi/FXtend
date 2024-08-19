package io.fxtend.chatview;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

public class SendLabel extends HBox
{
    public SendLabel(String sendText)
    {
        Text sendItem = new Text(sendText);
        TextFlow sendTextFlow = new TextFlow(sendItem);

        sendTextFlow.setMinWidth(50);

        sendTextFlow.widthProperty().addListener((observable, oldValue, newValue) -> {
            double width = newValue.doubleValue() + 5;
            setPrefWidth(width);
            setMaxWidth(width);
        });

        sendTextFlow.setTextAlignment(TextAlignment.RIGHT);

        getStyleClass().add("label-sender");
        setAlignment(Pos.CENTER_RIGHT);
        this.getChildren().add(sendTextFlow);
    }
}
