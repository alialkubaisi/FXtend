package io.fxtend.information;

import javafx.geometry.Pos;
import javafx.scene.Node;

/**
 * Factory class providing static helper methods to easily add information buttons to existing components.
 */
public class InformationFactory
{

    /**
     * Adds an information button to any JavaFX node.
     *
     * @param node The node to decorate
     * @param informationText The text to display in the information popup
     * @return A decorator containing the node and an information button
     */
    public static InformationDecorator addInfoTo(Node node, String informationText)
    {
        return new InformationDecorator(node, informationText);
    }

    /**
     * Adds an information button to any JavaFX node with specified position.
     *
     * @param node The node to decorate
     * @param informationText The text to display in the information popup
     * @param position The position of the information button
     * @return A decorator containing the node and an information button
     */
    public static InformationDecorator addInfoTo(Node node, String informationText, Pos position)
    {
        return new InformationDecorator(node, informationText, position);
    }
}