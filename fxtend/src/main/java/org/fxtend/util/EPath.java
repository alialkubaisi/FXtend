package org.fxtend.util;

public enum EPath
{
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
