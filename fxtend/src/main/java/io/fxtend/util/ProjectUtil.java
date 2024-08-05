package io.fxtend.util;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import org.controlsfx.control.textfield.CustomPasswordField;
import org.controlsfx.control.textfield.CustomTextField;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;

public class ProjectUtil
{
    /**
     * add icon to text field on both or one side of text field
     *
     * @param textField text field that need an icon
     * @param right icon on the right side of text field if null nothing will show
     * @param left icon on the left side of text field if null nothing will show
     * @param icon use ikonli icons and show in text field if null only of label will be showing
     * @param show show or hide icon
     */
    public static void addTextFieldIcon(TextField textField, Label right, Label left, Ikon icon, boolean show)
    {
        FontIcon fontIcon = null;
        if (icon != null)
        {
            fontIcon = new FontIcon(icon);
            fontIcon.setIconSize(16);
        }

        if (right != null)
        {
            if (fontIcon != null)
            {
                right.setGraphic(fontIcon);
            }
            right.setVisible(show);
            if (textField instanceof CustomTextField)
            {
                ((CustomTextField) textField).setRight(right);
            }
            else if (textField instanceof CustomPasswordField)
            {
                ((CustomPasswordField) textField).setRight(right);
            }
        }
        if (left != null)
        {
            if (fontIcon != null)
            {
                left.setGraphic(fontIcon);
            }
            left.setVisible(show);
            if (textField instanceof CustomTextField)
            {
                ((CustomTextField) textField).setLeft(left);
            }
            else if (textField instanceof CustomPasswordField)
            {
                ((CustomPasswordField) textField).setLeft(left);
            }
        }
    }

    public static void setTextFieldFocus(TextInputControl textInputControl)
    {
        Platform.runLater(() -> {
            textInputControl.requestFocus();
            // jump to the right (to tha last character)
            textInputControl.positionCaret(textInputControl.getLength());
        });
    }
}
