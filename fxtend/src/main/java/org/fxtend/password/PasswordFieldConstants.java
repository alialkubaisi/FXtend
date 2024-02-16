package org.fxtend.password;

public enum PasswordFieldConstants
{
    PASSWORD_TITLE("password_title"),
    PASSWORD_REQUIRED_MESSAGE("password_required_message"),
    PASSWORD_REQUIRED_TITLE("password_required_title"),
    PASSWORD_WEAK("password_weak"),
    PASSWORD_AVERAGE("password_average"),
    PASSWORD_GOOD("password_good"),
    PASSWORD_STRONG("password_strong");

    private final String constant;

    PasswordFieldConstants(String constant)
    {
        this.constant = constant;
    }

    public String getMessage()
    {
        return this.constant;
    }

    public String getTranslatedMessage()
    {
        return I18NPasswordField.getTranslatedMessage(constant);
    }

    public String getTranslatedMessage(String target, String replacement)
    {
        if (target == null || replacement == null)
        {
            return getTranslatedMessage();
        }

        final String translatedMessage = I18NPasswordField.getTranslatedMessage(constant);
        if (translatedMessage.contains(target))
        {
            return translatedMessage.replace(target, replacement);
        }
        return translatedMessage;
    }

    public String getTranslatedMessageOfLocale(String locale)
    {
        return I18NPasswordField.getMessageOfLocale(constant, locale);
    }
}