package io.fxtend.kanban;

public enum KanbanTextConstants
{
    ADD_CARD_BUTTON("add_card_button"),
    ADD_LIST_BUTTON("add_list_button"),
    LIST_TITLE("list_title");

    private final String text;

    KanbanTextConstants(String text)
    {
        this.text = text;
    }

    public String getTranslatedMessage()
    {
        return I18NKanban.getTranslatedMessage(text);
    }
}
