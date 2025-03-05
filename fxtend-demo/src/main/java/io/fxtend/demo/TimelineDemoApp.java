package io.fxtend.demo;

import io.fxtend.timeline.TimelineCellFactory;
import io.fxtend.timeline.TimelineEntry;
import io.fxtend.timeline.TimelineTextConstants;
import io.fxtend.timeline.TimelineView;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TimelineDemoApp extends Application
{

    @Override
    public void start(Stage primaryStage)
    {
        final List<TimelineEntry> events = getTimelineEntries();

        TimelineCellFactory<TimelineEntry> cellFactory = (event, isNewDay) -> {
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(5);
            grid.setPadding(new Insets(5));

            ColumnConstraints col1 = new ColumnConstraints();
            col1.setPercentWidth(20);
            ColumnConstraints col2 = new ColumnConstraints();
            col2.setPercentWidth(20);
            ColumnConstraints col3 = new ColumnConstraints();
            col3.setPercentWidth(20);
            ColumnConstraints col4 = new ColumnConstraints();
            col4.setPercentWidth(40);
            grid.getColumnConstraints().addAll(col1, col2, col3, col4);

            Label actionLabel = new Label(event.action().getTranslatedMessage());
            actionLabel.getStyleClass().add("timeline-event-action");
            Label userLabel = new Label(event.username());
            userLabel.getStyleClass().add("timeline-event-user");

            // Format the date using a custom format.
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Label dateLabel = new Label(sdf.format(event.date()));
            dateLabel.getStyleClass().add("timeline-event-date");

            Label descriptionLabel = new Label(event.description());
            descriptionLabel.getStyleClass().add("timeline-event-description");

            grid.add(actionLabel, 0, 0);
            grid.add(userLabel, 1, 0);
            grid.add(dateLabel, 2, 0);
            grid.add(descriptionLabel, 3, 0);

            if (isNewDay)
            {
                grid.getStyleClass().add("timeline-event-new-day");
            }
            return grid;
        };

        TimelineView<TimelineEntry> customTimelineView = new TimelineView<>("Custom Project History", events, cellFactory, TimelineEntry::date, 3,
                                                                      new SimpleDateFormat("MMM dd, yyyy HH:mm"));

        TimelineView<TimelineEntry> defaultTimelineView = new TimelineView<>("Default Project History", events);

        VBox root = new VBox(25, customTimelineView, defaultTimelineView);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(15));
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        primaryStage.setScene(new Scene(scrollPane, 800, 600));
        primaryStage.setTitle("TimelineView - Project History Example");
        primaryStage.show();
    }

    private static List<TimelineEntry> getTimelineEntries()
    {
        Calendar cal = Calendar.getInstance();

        cal.set(2025, Calendar.JANUARY, 1, 10, 30);
        Date date1 = cal.getTime();

        cal.set(2025, Calendar.JANUARY, 1, 13, 15);
        Date date2 = cal.getTime();

        cal.set(2025, Calendar.JANUARY, 2, 9, 0);
        Date date3 = cal.getTime();

        cal.set(2025, Calendar.JANUARY, 2, 15, 45);
        Date date4 = cal.getTime();

        cal.set(2025, Calendar.JANUARY, 3, 11, 20);
        Date date5 = cal.getTime();

        Date date6 = new Date();

        return Arrays.asList(new TimelineEntry(TimelineTextConstants.CREATE, "Alice", "Created the project repository.", date1),
                             new TimelineEntry(TimelineTextConstants.UPDATE, "Bob", "Updated the project requirements.", date2),
                             new TimelineEntry(TimelineTextConstants.UPDATE, "Alice", "Added initial comments on the design.",date3),
                             new TimelineEntry(TimelineTextConstants.UPDATE, "Charlie", "Modified the project timeline.", date4),
                             new TimelineEntry(TimelineTextConstants.UPDATE, "Alice", "Marked milestone 1 as complete.", date5),
                             new TimelineEntry(TimelineTextConstants.ARCHIVE, "Bob", "Archived outdated design docs.", date6));
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
