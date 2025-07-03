package io.fxtend.kanban;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class KanbanList
{

    private final StringProperty title = new SimpleStringProperty();
    private final ObservableList<KanbanCard> cards = FXCollections.observableArrayList();
    private final StringProperty id = new SimpleStringProperty();

    /**
     * Creates a new KanbanList with the specified title
     *
     * @param title the title of the list
     */
    public KanbanList(String title)
    {
        this.title.set(title);
        this.id.set(generateId());
    }

    /**
     * Creates a new KanbanList with the specified title and id
     *
     * @param title the title of the list
     * @param id the unique identifier for the list
     */
    public KanbanList(String title, String id)
    {
        this.title.set(title);
        this.id.set(id);
    }

    private String generateId()
    {
        return "list_" + System.currentTimeMillis() + "_" + (int) (Math.random() * 1000);
    }

    /**
     * Adds a card to this list
     *
     * @param card the card to add
     */
    public void addCard(KanbanCard card)
    {
        cards.add(card);
        card.setParentList(this);
    }

    /**
     * Adds a card to this list at the specified index
     *
     * @param index the index where to insert the card
     * @param card the card to add
     */
    public void addCard(int index, KanbanCard card)
    {
        cards.add(index, card);
        card.setParentList(this);
    }

    /**
     * Removes a card from this list
     *
     * @param card the card to remove
     * @return true if the card was removed, false otherwise
     */
    public boolean removeCard(KanbanCard card)
    {
        boolean removed = cards.remove(card);
        if (removed)
        {
            card.setParentList(null);
        }
        return removed;
    }

    /**
     * Removes the card at the specified index
     *
     * @param index the index of the card to remove
     * @return the removed card
     */
    public KanbanCard removeCard(int index)
    {
        KanbanCard card = cards.remove(index);
        if (card != null)
        {
            card.setParentList(null);
        }
        return card;
    }

    /**
     * Gets the index of the specified card in this list
     *
     * @param card the card to find
     * @return the index of the card, or -1 if not found
     */
    public int indexOf(KanbanCard card)
    {
        return cards.indexOf(card);
    }

    /**
     * Checks if this list contains the specified card
     *
     * @param card the card to check
     * @return true if the list contains the card, false otherwise
     */
    public boolean contains(KanbanCard card)
    {
        return cards.contains(card);
    }

    /**
     * Gets the number of cards in this list
     *
     * @return the number of cards
     */
    public int getCardCount()
    {
        return cards.size();
    }

    /**
     * Checks if this list is empty
     *
     * @return true if the list has no cards, false otherwise
     */
    public boolean isEmpty()
    {
        return cards.isEmpty();
    }

    /**
     * Clears all cards from this list
     */
    public void clear()
    {
        for (KanbanCard card : cards)
        {
            card.setParentList(null);
        }
        cards.clear();
    }

    // Property getters and setters

    public String getTitle()
    {
        return title.get();
    }

    public void setTitle(String title)
    {
        this.title.set(title);
    }

    public StringProperty titleProperty()
    {
        return title;
    }

    public String getId()
    {
        return id.get();
    }

    public void setId(String id)
    {
        this.id.set(id);
    }

    public StringProperty idProperty()
    {
        return id;
    }

    public ObservableList<KanbanCard> getCards()
    {
        return cards;
    }

    @Override
    public String toString()
    {
        return "KanbanList{" + "title='" + getTitle() + '\'' + ", id='" + getId() + '\'' + ", cardCount=" + getCardCount() + '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        KanbanList that = (KanbanList) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode()
    {
        return getId().hashCode();
    }
}