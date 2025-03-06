package io.fxtend.timeline;

public enum TimelineTextConstants
{
    // buttons
    BUTTON_SHOW_MORE("timeline_button_show_more"),
    BUTTON_SHOW_LESS("timeline_button_show_less"),
    // actions
    CREATE("timeline_action_create"),
    UPDATE("timeline_action_update"),
    ARCHIVE("timeline_action_archive"),
    DELETE("timeline_action_delete");

    private final String text;

    TimelineTextConstants(String text)
    {
        this.text = text;
    }

    public String getTranslatedMessage()
    {
        return I18NTimeline.getTranslatedMessage(text);
    }

    public String getTranslatedMessage(String target, String replacement)
    {
        if (target == null || replacement == null)
        {
            return getTranslatedMessage();
        }

        final String translatedMessage = I18NTimeline.getTranslatedMessage(text);
        if (translatedMessage.contains(target))
        {
            return translatedMessage.replace(target, replacement);
        }
        return translatedMessage;
    }
}
