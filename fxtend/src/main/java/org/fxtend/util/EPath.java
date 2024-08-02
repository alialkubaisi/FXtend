package org.fxtend.util;

public enum EPath
{
    AUTO_COMPLETE_FIELD_STYLE("auto-complete-field-style.css"),
    PASSWORD_FIELD_FXML("PasswordPopoverContainer.fxml"),
    PASSWORD_FIELD_STYLE("password-field-style.css");

    private String path;

    EPath(String path)
    {
        this.path = path;
    }

    public String getPath()
    {
        return path;
    }
}
