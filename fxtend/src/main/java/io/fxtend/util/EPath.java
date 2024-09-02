package io.fxtend.util;

public enum EPath
{
    // Auto complete field
    AUTO_COMPLETE_FIELD_STYLE("auto-complete-field-style.css"),

    // password field
    PASSWORD_FIELD_FXML("PasswordPopoverContainer.fxml"),
    PASSWORD_FIELD_STYLE("password-field-style.css"),

    // chat view
    CHAT_VIEW_DEFAULT_STYLE("chat-view-default-style.css"),
    CHAT_VIEW_MODERN_STYLE("chat-view-modern-style.css"),
    CHAT_VIEW_DARK_STYLE("chat-view-dark-style.css");

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
