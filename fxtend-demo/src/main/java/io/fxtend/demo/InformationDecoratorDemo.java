package io.fxtend.demo;

import io.fxtend.information.InformationDecorator;
import io.fxtend.information.InformationFactory;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class InformationDecoratorDemo extends Application
{
    @Override
    public void start(Stage primaryStage)
    {
        TextField emailField = new TextField();
        emailField.setPromptText("Enter your email address");
        String emailInfo = "Please enter a valid email address. We will use this for account recovery and notifications.";
        InformationDecorator emailDecorator = InformationFactory.addInfoTo(emailField, emailInfo, Pos.CENTER_RIGHT);

        TextField nameField = new TextField();
        nameField.setPromptText("Enter your full name");
        String nameInfo = "Enter your full name as shown on your official documents.";
        InformationDecorator nameDecorator = InformationFactory.addInfoTo(nameField, nameInfo, Pos.CENTER_LEFT);

        TextField phoneField = new TextField();
        phoneField.setPromptText("Enter your phone number");
        String phoneInfo = "Provide your contact number for further communication.";
        InformationDecorator phoneDecorator = InformationFactory.addInfoTo(phoneField, phoneInfo);

        VBox root = new VBox(15, emailDecorator, nameDecorator, phoneDecorator);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Information Decorator Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
