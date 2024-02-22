package org.fxtend.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.fxtend.password.PasswordField;
import javafx.stage.Stage;

public class PasswordFieldDemo extends Application
{
    @Override
    public void start(Stage stage)
    {
        GridPane root = new GridPane();
        root.setHgap(15);

        final Label passwordLabel = new Label("Test Password Field");
        root.add(passwordLabel, 0, 0);

        final PasswordField passwordField = new PasswordField(true, 10);
        root.add(passwordField, 1, 0);

        stage.setScene(new Scene(root, 500, 500));
        stage.show();
    }
}
