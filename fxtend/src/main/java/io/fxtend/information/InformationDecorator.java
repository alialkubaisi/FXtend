package io.fxtend.information;

import io.fxtend.util.EPath;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.controlsfx.control.PopOver;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignI;

import java.util.Objects;

/**
 * A decorator component that adds an information button to any JavaFX node. This allows easy integration of information tooltips with existing
 * components.
 */
public class InformationDecorator extends HBox
{
    private static final String STYLESHEET;

    static
    {
        STYLESHEET = Objects.requireNonNull(InformationDecorator.class.getResource(EPath.INFORMATION_BUTTON_STYLE.getPath())).toExternalForm();
    }

    private final Node decoratedNode;
    private final Button infoButton;
    private final PopOver informationPopup = new PopOver();
    private final StringProperty informationText = new SimpleStringProperty();
    private Pos buttonPosition = Pos.CENTER_RIGHT;
    private PopOver.ArrowLocation arrowLocation = PopOver.ArrowLocation.LEFT_CENTER;

    /**
     * Creates an information decorator that wraps the given node and adds an info button.
     *
     * @param node The JavaFX node to decorate with an information button
     * @param informationText The text to display in the information popup
     */
    public InformationDecorator(Node node, String informationText)
    {
        this.decoratedNode = node;
        this.informationText.set(informationText);

        // Setup info button
        infoButton = createInfoButton();

        // Apply styles
        getStylesheets().add(STYLESHEET);

        // Configure layout
        setSpacing(5);
        setAlignment(Pos.CENTER);

        updateLayout();
        initInformationPopup();
    }

    /**
     * Alternative constructor that allows setting the button position.
     */
    public InformationDecorator(Node node, String informationText, Pos buttonPosition)
    {
        this(node, informationText);
        this.buttonPosition = buttonPosition;
        updateLayout();
    }

    /**
     * Creates the information button with proper styling.
     */
    private Button createInfoButton()
    {
        Button button = new Button();
        FontIcon infoIcon = new FontIcon(MaterialDesignI.INFORMATION);
        infoIcon.getStyleClass().add("info-icon");
        button.setGraphic(infoIcon);
        button.getStyleClass().add("information-button");
        button.setOnAction(e -> togglePopup());
        return button;
    }

    /**
     * Updates the layout based on the button position.
     */
    private void updateLayout()
    {
        getChildren().clear();

        if (Objects.requireNonNull(buttonPosition) == Pos.CENTER_LEFT)
        {
            getChildren().addAll(infoButton, decoratedNode);
            arrowLocation = PopOver.ArrowLocation.RIGHT_CENTER;
        }
        else
        {
            getChildren().addAll(decoratedNode, infoButton);
        }
    }

    /**
     * Toggles the visibility of the information popup.
     */
    private void togglePopup()
    {
        Runnable popupTask = () -> {
            if (!informationPopup.isShowing())
            {
                informationPopup.setArrowLocation(arrowLocation);
                informationPopup.show(infoButton);
            }
            else
            {
                informationPopup.hide();
            }
        };

        if (Platform.isFxApplicationThread())
        {
            popupTask.run();
        }
        else
        {
            Platform.runLater(popupTask);
        }
    }

    /**
     * Initializes the information popup.
     */
    private void initInformationPopup()
    {
        informationPopup.setContentNode(getInformationContainer());
        informationPopup.setCloseButtonEnabled(false);
        informationPopup.setDetachable(false);
        informationPopup.setAutoHide(true);
        informationPopup.setAnimated(true);
        informationPopup.setCornerRadius(12);
        informationPopup.setFadeInDuration(Duration.seconds(0.4));
        informationPopup.setArrowSize(10);
    }

    /**
     * Creates the content for the information popup.
     */
    private VBox getInformationContainer()
    {
        Label infoLabel = new Label();
        infoLabel.textProperty().bind(informationText);
        infoLabel.setWrapText(true);
        infoLabel.getStyleClass().add("info-label");

        VBox container = new VBox(infoLabel);
        container.getStyleClass().add("info-container");
        container.setMaxWidth(300);

        return container;
    }

    /**
     * Sets the position of the info button relative to the decorated node.
     */
    public void setButtonPosition(Pos position)
    {
        this.buttonPosition = position;
        updateLayout();
    }

    /**
     * Gets the information text.
     */
    public String getInformationText()
    {
        return informationText.get();
    }

    /**
     * Sets the information text.
     */
    public void setInformationText(String text)
    {
        informationText.set(text);
    }

    /**
     * Gets the information text property.
     */
    public StringProperty informationTextProperty()
    {
        return informationText;
    }

    /**
     * Gets the decorated node.
     */
    public Node getDecoratedNode()
    {
        return decoratedNode;
    }
}