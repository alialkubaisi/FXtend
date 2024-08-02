package org.fxtend.password;

import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.textfield.CustomPasswordField;
import org.controlsfx.control.textfield.CustomTextField;
import org.fxtend.util.EPath;
import org.fxtend.util.ProjectUtil;
import org.kordamp.ikonli.materialdesign2.MaterialDesignE;

import java.io.IOException;

public class SecurePasswordField extends VBox
{
    public static final double FACTORS_NUMBER = 5;
    private final CustomTextField textField = new CustomTextField();
    private final CustomPasswordField passwordField = new CustomPasswordField();
    private final Label labelEyeIcon = new Label();
    private final Label labelEyeIconValue = new Label();
    private boolean withStrengthValidation;
    private int minLength = 12; // recommended now
    private PopOver strengthPopover;

    public SecurePasswordField()
    {
        super(10);
        getChildren().add(passwordField);

        ProjectUtil.addTextFieldIcon(passwordField, labelEyeIcon, null, MaterialDesignE.EYE_OUTLINE, false);
        ProjectUtil.addTextFieldIcon(textField, labelEyeIconValue, null, MaterialDesignE.EYE_OFF_OUTLINE, false);

        passwordField.textProperty().addListener((observable, oldPass, newPass) -> {
            labelEyeIcon.setVisible(!newPass.isEmpty());
            showStrengthPopover(true);
        });

        textField.textProperty().addListener((observable, oldPass, newPass) -> labelEyeIconValue.setVisible(!newPass.isEmpty()));

        handleLabel();

        textField.textProperty().bindBidirectional(passwordField.textProperty());
    }

    public SecurePasswordField(boolean withStrengthValidation, int minLength)
    {
        this();
        setWithStrengthValidation(withStrengthValidation);
        this.minLength = minLength;
    }

    /**
     * handle when icons clicked to switch between text and password fields
     */
    private void handleLabel()
    {
        labelEyeIcon.setOnMouseClicked(event -> {
            if (getChildren().contains(passwordField))
            {
                switchFields(textField, passwordField);
            }
        });

        labelEyeIconValue.setOnMouseClicked(event -> {
            if (getChildren().contains(textField))
            {
                switchFields(passwordField, textField);
            }
        });
    }

    private void switchFields(TextInputControl fieldToShow, TextInputControl textToHide)
    {
        getChildren().remove(textToHide);
        getChildren().add(0, fieldToShow);
        ProjectUtil.setTextFieldFocus(fieldToShow);
    }

    private void addStrengthPopover()
    {
        strengthPopover = new PopOver();
        strengthPopover.setContentNode(loadPopoverContainer());

        strengthPopover.setArrowLocation(PopOver.ArrowLocation.LEFT_CENTER);
        strengthPopover.setCloseButtonEnabled(false);
        strengthPopover.setDetachable(false);
        strengthPopover.setAutoHide(false);
        strengthPopover.setAnimated(true);

        strengthPopover.setCornerRadius(12); // default 6
        strengthPopover.setFadeInDuration(Duration.seconds(0.4)); // default 0.2
        strengthPopover.setArrowSize(10); // default 12

        focusNextOnTabClicked();

        passwordField.focusedProperty().addListener((observableValue, aBoolean, isFocused) -> showStrengthPopover(isFocused));
        textField.focusedProperty().addListener((observableValue, aBoolean, isFocused) -> showStrengthPopover(isFocused));
    }

    private void focusNextOnTabClicked()
    {
        strengthPopover.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.TAB && !event.isShiftDown() && strengthPopover.isShowing())
            {
                event.consume();
                if (getParent() != null)
                {
                    int currentNodeIndex = getParent().getChildrenUnmodifiable().indexOf(this);
                    Node nextNode = getParent().getChildrenUnmodifiable().get(currentNodeIndex + 1);
                    if (nextNode instanceof SecurePasswordField nextPasswordField)
                    {
                        ProjectUtil.setTextFieldFocus(nextPasswordField.getPasswordField());
                    }
                    else if (nextNode != null)
                    {
                        Platform.runLater(nextNode::requestFocus);
                    }
                }
            }
        });
    }

    private void showStrengthPopover(boolean show)
    {
        if (strengthPopover != null)
        {
            if (show)
            {
                if (!strengthPopover.isShowing())
                {
                    Bounds bounds = this.localToScreen(this.getBoundsInLocal());
                    double popOverX = bounds.getMaxX();
                    double popOverY = bounds.getMinY() + (bounds.getHeight() / 2);
                    strengthPopover.show(this, popOverX, popOverY);
                }
            }
            else if (!textField.isFocused() && !passwordField.isFocused())
            {
                strengthPopover.hide();
            }
        }
    }

    private VBox loadPopoverContainer()
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EPath.PASSWORD_FIELD_FXML.getPath()), I18NPasswordField.getResourceBundle("en"));

        VBox popoverContainer = null;
        try
        {
            popoverContainer = loader.load();
            PasswordPopoverContainer containerController = loader.getController();
            containerController.passwordPropertyProperty().bind(passwordField.textProperty());
            containerController.setMinLength(minLength);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return popoverContainer;
    }

    public boolean onCheck()
    {
        if (isWithStrengthValidation())
        {
            if (PasswordUtil.isPasswordValid(passwordField.getText(), minLength, FACTORS_NUMBER))
            {
                return true;
            }
            else
            {
                String errorMessage = SecurePasswordFieldConstants.PASSWORD_REQUIRED_MESSAGE.getTranslatedMessage();
                String title = SecurePasswordFieldConstants.PASSWORD_REQUIRED_TITLE.getTranslatedMessage();
                // TODO Handle error
                return false;
            }
        }
        return true;
    }

    public CustomTextField getTextField()
    {
        return textField;
    }

    public CustomPasswordField getPasswordField()
    {
        return passwordField;
    }

    public String getText()
    {
        return passwordField.getText();
    }

    public StringProperty textProperty()
    {
        return passwordField.textProperty();
    }

    public String getPromptText()
    {
        return passwordField.getPromptText();
    }

    public void setPromptText(String promptText)
    {
        passwordField.setPromptText(promptText);
    }

    public int getMinLength()
    {
        return minLength;
    }

    public void setMinLength(int minLength)
    {
        this.minLength = minLength;
    }

    public boolean isWithStrengthValidation()
    {
        return withStrengthValidation;
    }

    public void setWithStrengthValidation(boolean withValidation)
    {
        this.withStrengthValidation = withValidation;
        // this can only be done hier in construct is not yet set
        // show strength bar only when the flag is active
        if (withStrengthValidation)
        {
            addStrengthPopover();
        }
    }
}
