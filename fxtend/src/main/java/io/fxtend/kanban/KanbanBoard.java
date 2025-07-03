package io.fxtend.kanban;

import io.fxtend.util.EPath;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.util.Objects;
import java.util.function.Consumer;

public class KanbanBoard extends Control
{
    // Style classes
    private static final String DEFAULT_STYLE_CLASS = "kanban-board";

    // Control Properties
    private final BooleanProperty allowAddLists = new SimpleBooleanProperty(true);
    private final BooleanProperty allowAddCards = new SimpleBooleanProperty(true);
    private final BooleanProperty allowDragDrop = new SimpleBooleanProperty(true);
    private final StringProperty addListText = new SimpleStringProperty(KanbanTextConstants.ADD_LIST_BUTTON.getTranslatedMessage());
    private final StringProperty addCardText = new SimpleStringProperty(KanbanTextConstants.ADD_CARD_BUTTON.getTranslatedMessage());

    private final ObservableList<KanbanList> lists = FXCollections.observableArrayList();

    private Consumer<KanbanList> onListAdded;

    private Callback<KanbanCard, Region> cardFactory;

    public KanbanBoard()
    {
        super();
        setDefaultCardFactory();
        getStyleClass().add(DEFAULT_STYLE_CLASS);
        getStylesheets().add(Objects.requireNonNull(KanbanBoard.class.getResource(EPath.KANBAN_STYLE.getPath())).toExternalForm());
    }

    @Override
    protected Skin<?> createDefaultSkin()
    {
        return new KanbanBoardSkin(this);
    }

    private void setDefaultCardFactory()
    {
        cardFactory = card -> {
            VBox cardNode = new VBox(8);
            cardNode.setPadding(new Insets(12));
            cardNode.getStyleClass().add("kanban-card");

            Label titleLabel = new Label(card.getTitle());
            titleLabel.setWrapText(true);
            titleLabel.getStyleClass().add("kanban-card-title");

            cardNode.getChildren().add(titleLabel);

            if (card.getDescription() != null && !card.getDescription().isEmpty())
            {
                Label descLabel = new Label(card.getDescription());
                descLabel.setWrapText(true);
                descLabel.getStyleClass().add("kanban-card-description");
                cardNode.getChildren().add(descLabel);
            }

            return cardNode;
        };
    }

    /**
     * Adds a new list to the kanban board
     */
    public void addList(String title)
    {
        KanbanList list = new KanbanList(title);
        lists.add(list);
        if (onListAdded != null)
        {
            onListAdded.accept(list);
        }
    }

    public ObservableList<KanbanList> getLists()
    {
        return lists;
    }

    public boolean isAllowAddLists()
    {
        return allowAddLists.get();
    }

    public BooleanProperty allowAddListsProperty()
    {
        return allowAddLists;
    }

    public boolean isAllowAddCards()
    {
        return allowAddCards.get();
    }

    public BooleanProperty allowAddCardsProperty()
    {
        return allowAddCards;
    }

    public boolean isAllowDragDrop()
    {
        return allowDragDrop.get();
    }

    public BooleanProperty allowDragDropProperty()
    {
        return allowDragDrop;
    }

    public String getAddListText()
    {
        return addListText.get();
    }

    public StringProperty addListTextProperty()
    {
        return addListText;
    }

    public String getAddCardText()
    {
        return addCardText.get();
    }

    public StringProperty addCardTextProperty()
    {
        return addCardText;
    }

    public Callback<KanbanCard, Region> getCardFactory()
    {
        return cardFactory;
    }

    public void setCardFactory(Callback<KanbanCard, Region> cardFactory)
    {
        this.cardFactory = cardFactory != null ? cardFactory : this.cardFactory;
    }
}