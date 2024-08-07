package io.fxtend.demo;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import io.fxtend.password.SecurePasswordField;
import javafx.stage.Stage;

import java.util.Objects;

public class PasswordFieldDemo extends Application
{
    @Override
    public void start(Stage stage)
    {
        GridPane root = new GridPane();
        root.setHgap(15);
        root.setVgap(15);
        root.setAlignment(Pos.CENTER);

        final Label validationPasswordLabel = new Label("PasswordField with popup");
        root.add(validationPasswordLabel, 0, 0);

        final SecurePasswordField validationPasswordField = new SecurePasswordField(true, 10);
        root.add(validationPasswordField, 1, 0);

        final Label passwordLabel = new Label("PasswordField without popup");
        root.add(passwordLabel, 0, 1);

        final SecurePasswordField passwordField = new SecurePasswordField();
        root.add(passwordField, 1, 1);

        root.getStylesheets().add(Objects.requireNonNull(getClass().getResource(DemoPath.DEMO_PATH_STYLE.getPath())).toExternalForm());
        stage.setScene(new Scene(root, 500, 500));
        stage.show();
    }
}
