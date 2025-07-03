package io.fxtend.kanban;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class KanbanCard
{

    private final StringProperty title = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final StringProperty id = new SimpleStringProperty();
    private final ObjectProperty<LocalDateTime> createdAt = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDateTime> updatedAt = new SimpleObjectProperty<>();
    private final ObjectProperty<KanbanList> parentList = new SimpleObjectProperty<>();

    // Custom data storage for extensibility
    private final Map<String, Object> customData = new HashMap<>();

    /**
     * Creates a new KanbanCard with the specified title
     *
     * @param title the title of the card
     */
    public KanbanCard(String title)
    {
        this(title, null);
    }

    /**
     * Creates a new KanbanCard with the specified title and description
     *
     * @param title the title of the card
     * @param description the description of the card
     */
    public KanbanCard(String title, String description)
    {
        this.title.set(title);
        this.description.set(description);
        this.id.set(generateId());
        this.createdAt.set(LocalDateTime.now());
        this.updatedAt.set(LocalDateTime.now());
    }

    /**
     * Creates a new KanbanCard with the specified title, description, and id
     *
     * @param title the title of the card
     * @param description the description of the card
     * @param id the unique identifier for the card
     */
    public KanbanCard(String title, String description, String id)
    {
        this.title.set(title);
        this.description.set(description);
        this.id.set(id);
        this.createdAt.set(LocalDateTime.now());
        this.updatedAt.set(LocalDateTime.now());
    }

    private String generateId()
    {
        return "card_" + System.currentTimeMillis() + "_" + (int) (Math.random() * 1000);
    }

    /**
     * Updates the card's updated timestamp
     */
    public void touch()
    {
        this.updatedAt.set(LocalDateTime.now());
    }

    /**
     * Sets custom data for this card
     *
     * @param key the key for the data
     * @param value the value to store
     */
    public void setCustomData(String key, Object value)
    {
        customData.put(key, value);
        touch();
    }

    /**
     * Gets custom data for this card
     *
     * @param key the key for the data
     * @return the stored value, or null if not found
     */
    public Object getCustomData(String key)
    {
        return customData.get(key);
    }

    /**
     * Gets custom data for this card with a specific type
     *
     * @param key the key for the data
     * @param type the expected type of the data
     * @return the stored value cast to the specified type, or null if not found or wrong type
     */
    @SuppressWarnings("unchecked")
    public <T> T getCustomData(String key, Class<T> type)
    {
        Object value = customData.get(key);
        if (value != null && type.isAssignableFrom(value.getClass()))
        {
            return (T) value;
        }
        return null;
    }

    /**
     * Removes custom data for this card
     *
     * @param key the key for the data to remove
     * @return the removed value, or null if not found
     */
    public Object removeCustomData(String key)
    {
        Object removed = customData.remove(key);
        if (removed != null)
        {
            touch();
        }
        return removed;
    }

    /**
     * Checks if this card has custom data for the specified key
     *
     * @param key the key to check
     * @return true if the key exists, false otherwise
     */
    public boolean hasCustomData(String key)
    {
        return customData.containsKey(key);
    }

    /**
     * Gets all custom data keys
     *
     * @return a set of all custom data keys
     */
    public java.util.Set<String> getCustomDataKeys()
    {
        return customData.keySet();
    }

    /**
     * Clears all custom data
     */
    public void clearCustomData()
    {
        if (!customData.isEmpty())
        {
            customData.clear();
            touch();
        }
    }

    // Property getters and setters

    public String getTitle()
    {
        return title.get();
    }

    public void setTitle(String title)
    {
        this.title.set(title);
        touch();
    }

    public StringProperty titleProperty()
    {
        return title;
    }

    public String getDescription()
    {
        return description.get();
    }

    public void setDescription(String description)
    {
        this.description.set(description);
        touch();
    }

    public StringProperty descriptionProperty()
    {
        return description;
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

    public LocalDateTime getCreatedAt()
    {
        return createdAt.get();
    }

    public void setCreatedAt(LocalDateTime createdAt)
    {
        this.createdAt.set(createdAt);
    }

    public ObjectProperty<LocalDateTime> createdAtProperty()
    {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt()
    {
        return updatedAt.get();
    }

    public void setUpdatedAt(LocalDateTime updatedAt)
    {
        this.updatedAt.set(updatedAt);
    }

    public ObjectProperty<LocalDateTime> updatedAtProperty()
    {
        return updatedAt;
    }

    public KanbanList getParentList()
    {
        return parentList.get();
    }

    public void setParentList(KanbanList parentList)
    {
        this.parentList.set(parentList);
    }

    public ObjectProperty<KanbanList> parentListProperty()
    {
        return parentList;
    }

    @Override
    public String toString()
    {
        return "KanbanCard{" + "title='" + getTitle() + '\'' + ", id='" + getId() + '\'' + ", createdAt=" + getCreatedAt() + ", updatedAt=" + getUpdatedAt() + '}';
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
        KanbanCard that = (KanbanCard) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode()
    {
        return getId().hashCode();
    }
}