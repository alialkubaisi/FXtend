package io.fxtend.timeline;

import java.util.Locale;
import java.util.ResourceBundle;

public class I18NTimeline
{
    public static ResourceBundle getResourceBundle(String language)
    {
        Locale loc = new Locale(language);
        String baseName = "io.fxtend.timeline.timeline";

        return ResourceBundle.getBundle(baseName, loc);
    }

    /**
     * @param message which you want to have translated
     * @return String the translated Message as String
     */
    public static String getTranslatedMessage(String message)
    {
        return getMessageOfLocale(message, Locale.getDefault().getLanguage());
    }

    public static String getMessageOfLocale(String message, String locale)
    {
        return getResourceBundle(locale).getString(message);
    }
}
