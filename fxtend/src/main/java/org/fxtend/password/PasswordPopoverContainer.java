package org.fxtend.password;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.net.URL;
import java.util.ResourceBundle;

public class PasswordPopoverContainer implements Initializable
{
    @FXML
    private Label labelPasswordStatus;

    @FXML
    private CheckBox checkBoxLetter;
    @FXML
    private CheckBox checkBoxSymbol;
    @FXML
    private CheckBox checkBoxNumber;
    @FXML
    private CheckBox checkBoxLonger;

    @FXML
    private ProgressBar passwordStrengthBar;

    private static final String WEAK_COLOR = "#F9671C";
    private static final String AVERAGE_COLOR = "#FBAD4E";
    private static final String GOOD_COLOR = "#31B98B";
    private static final String STRONG_COLOR = "#135A46";
    private final StringProperty passwordProperty = new SimpleStringProperty();

    private int minLength = 12; // recommended now

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        // set default min length value in label text
        labelPasswordStatus.setText(PasswordFieldConstants.PASSWORD_TITLE.getTranslatedMessage("{{length}}", String.valueOf(minLength)));

        passwordProperty.addListener((observable, oldValue, newValue) -> {
            int score = calculatePasswordStrength(newValue);
            // the progress musst be the score divided by the number of all factories
            passwordStrengthBar.setProgress(score / PasswordField.FACTORS_NUMBER);
        });

        passwordStrengthBar.progressProperty().addListener((o, oldValue, newValue) -> {
            final double progress = newValue.doubleValue();
            if (progress == 0)
            {
                labelPasswordStatus.setText(PasswordFieldConstants.PASSWORD_TITLE.getTranslatedMessage("{{length}}", String.valueOf(minLength)));
            }
            if (progress > 0 && progress < 0.4)
            {
                labelPasswordStatus.setText(PasswordFieldConstants.PASSWORD_WEAK.getTranslatedMessage());
                passwordStrengthBar.setStyle("-fx-accent:" + WEAK_COLOR);
            }
            if (progress >= 0.6)
            {
                labelPasswordStatus.setText(PasswordFieldConstants.PASSWORD_AVERAGE.getTranslatedMessage());
                passwordStrengthBar.setStyle("-fx-accent:" + AVERAGE_COLOR);
            }
            if (progress >= 0.8)
            {
                labelPasswordStatus.setText(PasswordFieldConstants.PASSWORD_GOOD.getTranslatedMessage());
                passwordStrengthBar.setStyle("-fx-accent:" + GOOD_COLOR);
            }
            if (progress == 1)
            {
                labelPasswordStatus.setText(PasswordFieldConstants.PASSWORD_STRONG.getTranslatedMessage());
                passwordStrengthBar.setStyle("-fx-accent:" + STRONG_COLOR);
            }
        });
    }

    /**
     * check if password has uppercase and lower case letters, digit and symbol
     *
     * @param password password given in password field
     * @return score determinant how strong is the password
     */
    private int calculatePasswordStrength(String password)
    {
        // set checkboxes
        checkBoxLetter.setSelected(password.matches("(?=.*[A-Z]).*") && password.matches("(?=.*[a-z]).*"));
        checkBoxSymbol.setSelected(password.matches("(?=.*[\\W_]).*"));
        checkBoxNumber.setSelected(password.matches("(?=.*[0-9]).*"));
        checkBoxLonger.setSelected(password.length() >= minLength);

        return PasswordUtil.getPasswordScore(password, minLength);
    }

    public StringProperty passwordPropertyProperty()
    {
        return passwordProperty;
    }

    public int getMinLength()
    {
        return minLength;
    }

    public void setMinLength(int minLength)
    {
        this.minLength = minLength;

        // set min length value in label text after init
        labelPasswordStatus.setText(PasswordFieldConstants.PASSWORD_TITLE.getTranslatedMessage("{{length}}", String.valueOf(minLength)));
    }
}
