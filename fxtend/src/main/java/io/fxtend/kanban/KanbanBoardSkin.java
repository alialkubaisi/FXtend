package io.fxtend.kanban;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

public class KanbanBoardSkin extends SkinBase<KanbanBoard>
{
    private final static double SPACING = 15d;
    private final ScrollPane kanbanScrollPane;
    private final HBox listsContainer;
    private final Button addListButton;

    private final Map<KanbanList, VBox> listNodes = new HashMap<>();
    private final Map<KanbanCard, Region> cardNodes = new HashMap<>();

    public KanbanBoardSkin(KanbanBoard control)
    {
        super(control);

        listsContainer = new HBox(SPACING);
        listsContainer.setPadding(new Insets(SPACING));
        listsContainer.setAlignment(Pos.TOP_LEFT);

        kanbanScrollPane = new ScrollPane(listsContainer);
        kanbanScrollPane.setFitToHeight(true);
        kanbanScrollPane.setFitToWidth(true);
        kanbanScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        kanbanScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        kanbanScrollPane.getStyleClass().add("kanban-scroll-pane");
        kanbanScrollPane.setMaxHeight(Double.MAX_VALUE);
        kanbanScrollPane.setMaxWidth(Double.MAX_VALUE);
        kanbanScrollPane.setPrefHeight(Double.MAX_VALUE);

        addListButton = new Button();
        addListButton.textProperty().bind(control.addListTextProperty());
        addListButton.getStyleClass().add("kanban-add-list-button");
        addListButton.setOnAction(event -> addNewList());

        addListButton.visibleProperty().bind(control.allowAddListsProperty());
        addListButton.managedProperty().bind(control.allowAddListsProperty());

        setupListeners();

        renderLists();

        getChildren().add(kanbanScrollPane);

        // Ensure the ScrollPane fills the available space in the skin
        kanbanScrollPane.prefWidthProperty().bind(getSkinnable().widthProperty());
        kanbanScrollPane.prefHeightProperty().bind(getSkinnable().heightProperty());
    }

    private void setupListeners()
    {
        getSkinnable().getLists().addListener((InvalidationListener) obs -> renderLists());
        getSkinnable().allowAddListsProperty().addListener(obs -> renderLists());
    }

    private void renderLists()
    {
        listsContainer.getChildren().clear();
        listNodes.clear();

        // Render existing lists
        for (KanbanList list : getSkinnable().getLists())
        {
            VBox listNode = createListNode(list);
            listNodes.put(list, listNode);
            listsContainer.getChildren().add(listNode);
        }

        // Add the "Add List" button
        if (getSkinnable().isAllowAddLists())
        {
            listsContainer.getChildren().add(addListButton);
        }
    }

    private VBox createListNode(KanbanList list)
    {
        VBox listNode = new VBox(8);
        listNode.getStyleClass().add("kanban-list");
        listNode.setMinWidth(280);
        listNode.setMaxWidth(280);

        // Create header
        HBox header = createListHeader(list);
        listNode.getChildren().add(header);

        // Create cards container
        VBox cardsContainer = new VBox(8);
        cardsContainer.getStyleClass().add("kanban-cards-container");

        // Render cards
        for (KanbanCard card : list.getCards())
        {
            Region cardNode = createCardNode(card);
            cardNodes.put(card, cardNode);
            cardsContainer.getChildren().add(cardNode);
        }

        // hide if no card created yet
        final boolean shouldShowContainer = !list.getCards().isEmpty();
        cardsContainer.setVisible(shouldShowContainer);
        cardsContainer.setManaged(shouldShowContainer);

        // Listen for card changes
        list.getCards().addListener((InvalidationListener) obs -> {
            Platform.runLater(() -> updateCardsInList(list, cardsContainer));
        });

        ScrollPane cardsScrollPane = new ScrollPane(cardsContainer);
        cardsScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        cardsScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        cardsScrollPane.getStyleClass().add("kanban-cards-scroll");

        listNode.getChildren().add(cardsScrollPane);

        // Add card button
        if (getSkinnable().isAllowAddCards())
        {
            Button addCardButton = new Button();
            addCardButton.textProperty().bind(getSkinnable().addCardTextProperty());
            addCardButton.getStyleClass().add("kanban-add-card-button");
            addCardButton.setMaxWidth(Double.MAX_VALUE);
            listNode.getChildren().add(addCardButton);
        }

        return listNode;
    }

    private HBox createListHeader(KanbanList list)
    {
        HBox header = new HBox(8);
        header.setAlignment(Pos.CENTER_LEFT);
        header.getStyleClass().add("kanban-list-header");

        Label titleLabel = new Label();
        titleLabel.textProperty().bind(list.titleProperty());
        titleLabel.getStyleClass().add("kanban-list-title");

        Label countLabel = new Label();
        countLabel.textProperty().bind(Bindings.createStringBinding(() -> "(" + list.getCards().size() + ")", list.getCards()));
        countLabel.getStyleClass().add("kanban-list-count");

        header.getChildren().addAll(titleLabel, countLabel);

        // Add context menu for list operations
        ContextMenu contextMenu = new ContextMenu();
        MenuItem editItem = new MenuItem("Edit List");
        MenuItem deleteItem = new MenuItem("Delete List");
        contextMenu.getItems().addAll(editItem, deleteItem);

        header.setOnContextMenuRequested(e -> contextMenu.show(header, e.getScreenX(), e.getScreenY()));

        return header;
    }

    private Region createCardNode(KanbanCard card)
    {
        Region cardNode = getSkinnable().getCardFactory().call(card);

        // Add context menu for card operations
        ContextMenu contextMenu = new ContextMenu();
        MenuItem editItem = new MenuItem("Edit Card");
        MenuItem deleteItem = new MenuItem("Delete Card");
        contextMenu.getItems().addAll(editItem, deleteItem);

        cardNode.setOnContextMenuRequested(e -> contextMenu.show(cardNode, e.getScreenX(), e.getScreenY()));

        return cardNode;
    }

    private void addNewList()
    {
        TextField input = new TextField();
        input.setPromptText(KanbanTextConstants.LIST_TITLE.getTranslatedMessage());
        input.getStyleClass().add("kanban-list-title-text-field");

        VBox wrapper = new VBox(input);
        wrapper.getStyleClass().add("kanban-list");
        wrapper.setMinWidth(280);
        wrapper.setMaxWidth(280);
        wrapper.setPadding(new Insets(5));
        listsContainer.getChildren().add(listsContainer.getChildren().size() - 1, wrapper);
        input.requestFocus();

        input.setOnAction(e -> {
            String title = input.getText().trim();
            if (!title.isEmpty())
            {
                getSkinnable().addList(title.trim());
            }
            listsContainer.getChildren().remove(wrapper);
        });
    }

    private void updateCardsInList(KanbanList list, VBox cardsContainer)
    {
        cardsContainer.getChildren().clear();
        boolean isEmptyContainer = list.getCards().isEmpty();
        cardsContainer.setVisible(!isEmptyContainer);
        cardsContainer.setManaged(!isEmptyContainer);

        for (KanbanCard card : list.getCards())
        {
            Region cardNode = cardNodes.get(card);
            if (cardNode == null)
            {
                cardNode = createCardNode(card);
                cardNodes.put(card, cardNode);
            }
            cardsContainer.getChildren().add(cardNode);
        }
    }
}
