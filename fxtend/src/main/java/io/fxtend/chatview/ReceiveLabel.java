package io.fxtend.chatview;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class ReceiveLabel extends HBox
{
    public ReceiveLabel(String responseText)
    {
        Text responseItem = new Text(responseText);

        TextFlow resonseTextFlow = new TextFlow(responseItem);
        resonseTextFlow.setMinWidth(50);

        resonseTextFlow.widthProperty().addListener((observable, oldValue, newValue) -> {
            double width = newValue.doubleValue();
            setPrefWidth(width);
            setMaxWidth(width);
        });


        getStyleClass().add("label-receiver");
        setAlignment(Pos.CENTER_LEFT);
        getChildren().add(resonseTextFlow);
    }
}
