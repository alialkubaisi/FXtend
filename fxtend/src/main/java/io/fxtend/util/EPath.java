package io.fxtend.util;

public enum EPath
{
    // Auto complete field
    AUTO_COMPLETE_FIELD_STYLE("auto-complete-field-style.css"),

    // password field
    PASSWORD_FIELD_FXML("PasswordPopoverContainer.fxml"),
    PASSWORD_FIELD_STYLE("password-field-style.css"),

    // chat view
    CHAT_VIEW_STYLE("chat-view-style.css");

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
