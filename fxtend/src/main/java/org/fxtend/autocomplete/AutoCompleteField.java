package org.fxtend.autocomplete;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.control.*;
import org.controlsfx.control.PopOver;
import org.fxtend.util.EPath;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class is a TextField that implements an "autocomplete" functionality, based on a supplied list of suggestions.
 */
public class AutoCompleteField extends TextField
{
    private final static double DEFAULT_ROW_HEIGHT = 28d;
    private final Set<String> suggestions = new HashSet<>();
    private final PopOver suggestionsPopup = new PopOver();
    private final ListView<String> suggestionListView = new ListView<>();
    private final ObservableList<String> suggestionListItems = FXCollections.observableArrayList();
    private final DoubleProperty visibleRowCount = new SimpleDoubleProperty(6);

    private final ChangeListener<String> selectSuggestionListener = (observableValue, s, selectedSuggestion) -> {
        if (selectedSuggestion != null && !selectedSuggestion.isEmpty())
        {
            Platform.runLater(() -> onSelectionChange(selectedSuggestion));
        }
    };

    public AutoCompleteField(Collection<? extends String> suggestions)
    {
        super();
        this.suggestions.addAll(suggestions);
        this.getStylesheets().add(Objects.requireNonNull(getClass().getResource(EPath.AUTO_COMPLETE_FIELD_STYLE.getPath())).toExternalForm());
        suggestionListView.getStyleClass().add("auto-complete-list-view");
        initListView();
        initPopup();
        textPropertyHandler();
    }

    public AutoCompleteField(Collection<? extends String> suggestions, int visibleRowCount)
    {
        this(suggestions);
        this.visibleRowCount.set(visibleRowCount);
    }

    private void onSelectionChange(String selectedSuggestion)
    {
        setText(selectedSuggestion);
        positionCaret(selectedSuggestion.length());
        suggestionsPopup.hide();
        suggestionListView.getSelectionModel().clearSelection();
    }

    private void initListView()
    {
        suggestionListView.setItems(suggestionListItems);
        suggestionListView.prefWidthProperty().bind(widthProperty());
        suggestionListView.maxWidthProperty().bind(widthProperty());
        suggestionListView.minWidthProperty().bind(widthProperty());
        suggestionListView.setMaxHeight(DEFAULT_ROW_HEIGHT * visibleRowCount.get());
        suggestionListView.getSelectionModel().selectedItemProperty().addListener(selectSuggestionListener);
    }

    private void initPopup()
    {
        suggestionsPopup.setContentNode(suggestionListView);
        suggestionsPopup.setAutoFix(true);
        suggestionsPopup.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
        suggestionsPopup.setCloseButtonEnabled(false);
        suggestionsPopup.setArrowSize(0);
        suggestionsPopup.setArrowIndent(0);
    }

    private void textPropertyHandler()
    {
        textProperty().addListener((observableValue, s, newText) -> {
            if (!isFocused())
            {
                return;
            }
            if (newText == null || newText.isEmpty())
            {
                suggestionsPopup.hide();
            }
            else
            {
                String lowerText = newText.toLowerCase();
                Set<String> searchResult = suggestions.parallelStream()
                        .filter(item -> item.toLowerCase().contains(lowerText))
                        .collect(Collectors.toSet());

                if (!searchResult.isEmpty())
                {
                    populatePopup(searchResult);
                    Platform.runLater(this::showPopup);
                }
                else
                {
                    suggestionsPopup.hide();
                }
            }
        });
    }

    /**
     * show matching items and handle when item is selected
     *
     * @param searchResult The set of matching strings.
     */
    private void populatePopup(Set<String> searchResult)
    {
        suggestionListItems.clear();
        suggestionListItems.addAll(searchResult);
        if (isFocused() && searchResult.size() < visibleRowCount.get())
        {
            suggestionListView.setMaxHeight(DEFAULT_ROW_HEIGHT * searchResult.size());
        }
        else
        {
            suggestionListView.setMaxHeight(DEFAULT_ROW_HEIGHT * visibleRowCount.get());
        }
    }

    private void showPopup()
    {
        if (!suggestionsPopup.isShowing() && isFocused())
        {
            suggestionsPopup.show(this);
            Bounds bounds = this.localToScreen(this.getBoundsInLocal());
            double popOverY = bounds.getMinY() + bounds.getHeight();
            suggestionsPopup.setY(popOverY - 10);
        }
    }
}