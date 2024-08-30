package io.fxtend.chatview;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

public class ReceiveLabel extends MessageBubble
{
    public ReceiveLabel(String responseText)
    {
        super(responseText, "label-receiver", "timestamp-receiver", "bubble-receiver", Pos.CENTER_LEFT);
    }

    public void initLogoImage(Image logo)
    {
        ImageView logoImageView = new ImageView();

        logoImageView.setImage(logo);
        logoImageView.setFitWidth(30);
        logoImageView.setFitHeight(30);

        Circle clip = new Circle(15, 15, 15);
        logoImageView.setClip(clip);

        logoImageView.setPreserveRatio(true);
        getBubble().getStyleClass().add("bubble-receiver-with-logo");

        HBox.setMargin(logoImageView, new Insets(0, 3, 20, 0));
        getChildren().add(0, logoImageView);
    }
}
