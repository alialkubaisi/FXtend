package io.fxtend.timeline;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.util.Callback;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;

import java.text.DateFormat;
import java.util.*;

/**
 * A generic timeline view control that displays entries along a vertical line.
 * <p>
 * It supports optional grouping of entries by day (if a date extractor is provided), customizable cell rendering (via a TimelineCellFactory), a
 * configurable date format, and a configurable initial history limit.
 *
 * @param <T> the type of the timeline entry.
 */
public class TimelineView<T> extends VBox
{
    private static final int DEFAULT_INITIAL_HISTORY_LIMIT = 5;

    private int initialHistoryLimit;
    private DateFormat dateFormat;

    private final ToggleButton expandButton = new ToggleButton();
    private final HBox timelineContainer = new HBox(15);
    private final FontIcon iconUp = new FontIcon(MaterialDesignC.CHEVRON_UP);
    private final FontIcon iconDown = new FontIcon(MaterialDesignC.CHEVRON_DOWN);

    private final List<T> timelineEntries;
    private final TimelineCellFactory<T> cellFactory;
    /**
     * A callback to extract a Date from a timeline entry.
     * <p>
     * If {@code null}, new‑day grouping is disabled.
     */
    private final Callback<T, Date> dateExtractor;

    // Lists used for positioning knots next to timeline entry nodes
    private final List<Region> textEntries = new ArrayList<>();
    private final List<Circle> knots = new ArrayList<>();
    private Pane knotsPane;

    /**
     * Creates a TimelineView using default cell factory, default history limit, and default date format. New-day grouping is disabled because no date
     * extractor is provided.
     *
     * @param title the title of the timeline.
     * @param timelineEntries the list of timeline entries.
     */
    public TimelineView(String title, List<T> timelineEntries)
    {
        this(title, timelineEntries, null, null, DEFAULT_INITIAL_HISTORY_LIMIT,
             DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, Locale.getDefault()));
    }

    /**
     * Creates a TimelineView with a custom cell factory and date extractor. Uses the default history limit and date format.
     *
     * @param title the title of the timeline.
     * @param timelineEntries the list of timeline entries.
     * @param dateExtractor a callback to extract a Date from an entry; if null, new‑day grouping is disabled.
     */
    public TimelineView(String title, List<T> timelineEntries, Callback<T, Date> dateExtractor)
    {
        this(title, timelineEntries, null, dateExtractor, DEFAULT_INITIAL_HISTORY_LIMIT,
             DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, Locale.getDefault()));
    }

    /**
     * Creates a TimelineView with a custom cell factory and date extractor. Uses the default history limit and date format.
     *
     * @param title the title of the timeline.
     * @param timelineEntries the list of timeline entries.
     * @param cellFactory a factory for rendering each timeline entry; if null a default is used.
     * @param dateExtractor a callback to extract a Date from an entry; if null, new‑day grouping is disabled.
     */
    public TimelineView(String title, List<T> timelineEntries, TimelineCellFactory<T> cellFactory, Callback<T, Date> dateExtractor)
    {
        this(title, timelineEntries, cellFactory, dateExtractor, DEFAULT_INITIAL_HISTORY_LIMIT,
             DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, Locale.getDefault()));
    }

    /**
     * Creates a fully customizable TimelineView.
     *
     * @param title the title of the timeline.
     * @param timelineEntries the list of timeline entries.
     * @param cellFactory a factory for rendering each timeline entry; if null a default is used.
     * @param dateExtractor a callback to extract a Date from an entry; if null, new‑day grouping is disabled.
     * @param initialHistoryLimit the initial number of entries to show before expansion.
     * @param dateFormat the date format to use in the default cell rendering.
     */
    public TimelineView(String title, List<T> timelineEntries, TimelineCellFactory<T> cellFactory, Callback<T, Date> dateExtractor,
                        int initialHistoryLimit, DateFormat dateFormat)
    {
        this.timelineEntries = Objects.requireNonNull(timelineEntries, "timelineEntries cannot be null");
        this.initialHistoryLimit = initialHistoryLimit;
        this.dateFormat = Objects.requireNonNull(dateFormat, "dateFormat cannot be null");
        this.dateExtractor = dateExtractor;
        this.cellFactory = cellFactory != null ? cellFactory : (TimelineCellFactory<T>) createDefaultCellFactory();

        getStylesheets().add(Objects.requireNonNull(TimelineView.class.getResource("timelineview.css")).toExternalForm());
        getStyleClass().add("timeline-container");
        setFillWidth(true);
        setMaxWidth(Double.MAX_VALUE);

        createTitle(title);
        getChildren().add(timelineContainer);

        // Initially show a subset of entries (if the total exceeds the limit)
        int maxIndex = Math.min(timelineEntries.size(), initialHistoryLimit);
        List<T> entriesToShow = new ArrayList<>(timelineEntries.subList(0, maxIndex));
        createVerticalTimeline(entriesToShow);

        if (timelineEntries.size() > initialHistoryLimit)
        {
            expandButton.getStyleClass().add("expand-button");
            expandButton.setText(
                    TimelineTextConstants.BUTTON_SHOW_MORE.getTranslatedMessage() + " (" + (timelineEntries.size() - initialHistoryLimit) + ")");
            expandButton.setGraphic(iconDown);
            expandButton.setOnAction(event -> toggleExpand());
            HBox buttonContainer = new HBox(expandButton);
            buttonContainer.setAlignment(Pos.CENTER);
            getChildren().add(buttonContainer);
        }
    }

    private void createTitle(String title)
    {
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("timeline-container-title");
        getChildren().add(titleLabel);
    }

    /**
     * Creates the timeline display for the provided entries.
     *
     * @param entriesToShow the entries to display.
     */
    private void createVerticalTimeline(List<T> entriesToShow)
    {
        timelineContainer.getChildren().clear();
        textEntries.clear();
        knots.clear();

        // Create a container for the vertical line.
        StackPane lineContainer = new StackPane();
        lineContainer.setAlignment(Pos.TOP_CENTER);

        Region verticalLine = new Region();
        verticalLine.getStyleClass().add("vertical-line");

        knotsPane = new Pane(verticalLine);
        knotsPane.setPickOnBounds(false);
        lineContainer.getChildren().addAll(verticalLine, knotsPane);

        VBox textContainer = new VBox();
        textContainer.setSpacing(20);
        HBox.setHgrow(textContainer, Priority.ALWAYS);

        Date previousDate = null;
        for (T entry : entriesToShow)
        {
            Date currentDate = dateExtractor != null ? dateExtractor.call(entry) : null;
            // new-day logic only applies if a date extractor was provided
            boolean isNewDay = (dateExtractor != null) && (!isSameDay(previousDate, currentDate));

            Node cell = cellFactory.createCell(entry, isNewDay);
            Region entryContainer = (cell instanceof Region) ? (Region) cell : new StackPane(cell);
            textContainer.getChildren().add(entryContainer);
            textEntries.add(entryContainer);

            // Create a timeline knot (circle)
            Circle knot = new Circle(4);
            knot.getStyleClass().add("timeline-knot");
            if (isNewDay)
            {
                knot.getStyleClass().add("timeline-knot-new-day");
            }
            knots.add(knot);
            knotsPane.getChildren().add(knot);

            previousDate = currentDate;
        }

        timelineContainer.getChildren().addAll(lineContainer, textContainer);
    }

    /**
     * Helper method to compare whether two dates are on the same day.
     */
    private boolean isSameDay(Date date1, Date date2)
    {
        if (date1 == null || date2 == null)
        {
            return false;
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * Toggles between showing an initial subset of entries and all entries.
     */
    private void toggleExpand()
    {
        boolean isExpanded = expandButton.isSelected();
        int maxIndex = isExpanded ? timelineEntries.size() : Math.min(timelineEntries.size(), initialHistoryLimit);
        List<T> entriesToShow = new ArrayList<>(timelineEntries.subList(0, maxIndex));
        createVerticalTimeline(entriesToShow);
        if (isExpanded)
        {
            expandButton.setText(TimelineTextConstants.BUTTON_SHOW_LESS.getTranslatedMessage());
            expandButton.setGraphic(iconUp);
        }
        else
        {
            expandButton.setText(
                    TimelineTextConstants.BUTTON_SHOW_MORE.getTranslatedMessage() + " (" + (timelineEntries.size() - initialHistoryLimit) + ")");
            expandButton.setGraphic(iconDown);
        }
    }

    /**
     * Positions each timeline knot next to its corresponding entry.
     */
    @Override
    protected void layoutChildren()
    {
        super.layoutChildren();
        for (int i = 0; i < textEntries.size(); i++)
        {
            Region entry = textEntries.get(i);
            Circle knot = knots.get(i);
            double entryMidY = entry.getBoundsInParent().getMinY() + entry.getHeight() / 2.0;
            Platform.runLater(() -> {
                knot.setLayoutX(knotsPane.getWidth() / 2.0);
                knot.setLayoutY(entryMidY);
            });
        }
    }

    /**
     * Returns the default cell factory that simply uses the entry's toString() value. If a date extractor is provided, the cell appends the formatted
     * date.
     *
     * @return a TimelineCellFactory for rendering entries.
     */
    private TimelineCellFactory<TimelineEntry> createDefaultCellFactory()
    {
        return (entry, isNewDay) -> {
            GridPane cell = new GridPane();
            cell.setHgap(10);

            final TimelineTextConstants action = entry.action();

            Label actionLabel = new Label(action.getTranslatedMessage());

            switch (action)
            {
                case CREATE -> actionLabel.getStyleClass().add("timeline-entry-action-created");
                case DELETE -> actionLabel.getStyleClass().add("timeline-entry-action-deleted");
                case ARCHIVE -> actionLabel.getStyleClass().add("timeline-entry-action-archived");
                default -> actionLabel.getStyleClass().add("timeline-entry-action-updated");
            }

            Label userLabel = new Label(entry.username());
            userLabel.getStyleClass().add("timeline-entry-user");

            DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, Locale.getDefault());
            Label dateLabel = new Label(dateFormat.format(entry.date()));
            dateLabel.getStyleClass().add("timeline-entry-date");

            // You can add extra styling for new-day entries if desired:
            if (isNewDay)
            {
                cell.getStyleClass().add("timeline-entry-new-day");
            }

            // Configure column widths (optional)
            ColumnConstraints col1 = new ColumnConstraints();
            col1.setPercentWidth(15);
            ColumnConstraints col2 = new ColumnConstraints();
            col2.setPercentWidth(20);
            ColumnConstraints col3 = new ColumnConstraints();
            col3.setPercentWidth(25);
            cell.getColumnConstraints().addAll(col1, col2, col3);

            cell.add(actionLabel, 0, 0);
            cell.add(userLabel, 1, 0);
            cell.add(dateLabel, 2, 0);
            return cell;
        };
    }

    // ===== Public setters for customization =====

    /**
     * Sets a new DateFormat for the control and refreshes the display.
     *
     * @param dateFormat the new DateFormat to use.
     */
    public void setDateFormat(DateFormat dateFormat)
    {
        this.dateFormat = Objects.requireNonNull(dateFormat, "dateFormat cannot be null");
        // Refresh the timeline so that the new format is applied.
        refreshTimeline();
    }

    /**
     * Sets a new initial history limit and refreshes the timeline display.
     *
     * @param initialHistoryLimit the new limit.
     */
    public void setInitialHistoryLimit(int initialHistoryLimit)
    {
        if (initialHistoryLimit < 1)
        {
            throw new IllegalArgumentException("initialHistoryLimit must be at least 1");
        }
        this.initialHistoryLimit = initialHistoryLimit;
        refreshTimeline();
    }

    /**
     * Refreshes the timeline view.
     */
    private void refreshTimeline()
    {
        // Determine how many entries to show based on the current expanded/collapsed state.
        boolean expanded = expandButton.isSelected();
        int maxIndex = expanded ? timelineEntries.size() : Math.min(timelineEntries.size(), initialHistoryLimit);
        List<T> entriesToShow = new ArrayList<>(timelineEntries.subList(0, maxIndex));
        createVerticalTimeline(entriesToShow);
    }

    /**
     * Returns the title of the timeline.
     *
     * @return the timeline title.
     */
    public String getTitle()
    {
        // Assuming the first Label added is the title.
        if (!getChildren().isEmpty() && getChildren().get(0) instanceof Label)
        {
            return ((Label) getChildren().get(0)).getText();
        }
        return "";
    }
}
