package io.fxtend.timeline;

import javafx.scene.Node;

/**
 * A functional interface for creating a timeline cell.
 *
 * @param <T> the type of timeline entry.
 */
@FunctionalInterface
public interface TimelineCellFactory<T>
{
    /**
     * Creates a Node (cell) for the given timeline entry.
     *
     * @param entry the timeline entry.
     * @param isNewDay true if this entry is the first one on a new day (if date extraction is enabled).
     * @return a Node representing the timeline entry.
     */
    Node createCell(T entry, boolean isNewDay);
}