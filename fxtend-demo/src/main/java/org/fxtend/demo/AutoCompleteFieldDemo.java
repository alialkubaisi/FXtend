package org.fxtend.demo;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.fxtend.autocomplete.AutoCompleteField;

import java.util.List;

public class AutoCompleteFieldDemo extends Application
{
    @Override
    public void start(Stage stage)
    {
        VBox root = new VBox();
        AutoCompleteField autoCompleteTextField = new AutoCompleteField(buildSuggestions());
        autoCompleteTextField.setPrefWidth(300);
        autoCompleteTextField.setMaxWidth(300);

        root.getChildren().add(autoCompleteTextField);
        root.setAlignment(Pos.CENTER);
        stage.setScene(new Scene(root, 500, 500));
        stage.setTitle("AutoComplete TextField Demo");
        stage.show();
    }

    private List<String> buildSuggestions()
    {
        return List.of("apple", "banana", "cherry", "date", "elderberry", "fig", "grape", "honeydew", "kiwi", "lemon", "mango", "nectarine", "orange",
                       "papaya", "quince", "raspberry", "strawberry", "tangerine", "ugli fruit", "vanilla", "watermelon", "xigua",
                       "yellow passion fruit", "zucchini", "apricot", "blackberry", "cantaloupe", "dragonfruit", "elderflower", "feijoa",
                       "gooseberry", "huckleberry", "jackfruit", "kumquat", "lime", "mulberry", "navel orange", "olive", "pineapple", "quandong",
                       "red currant", "soursop", "tomato", "ugni", "valencia orange", "white currant", "ximenia", "yuzu", "ziziphus", "acerola",
                       "boysenberry", "cloudberry", "damson", "entawak", "finger lime", "grapefruit", "horned melon", "ilama", "jambul", "kava",
                       "longan", "marionberry", "nannyberry", "opuntia", "peach", "quararibea", "rowanberry", "salmonberry", "tayberry",
                       "umbrella fruit", "voavanga", "wolfberry", "xylocarp", "yangmei", "zebra melon", "ackee", "bilberry", "chokeberry", "dewberry",
                       "emery grape", "figapple", "genip", "huito", "indian fig", "jostaberry", "kiwifruit", "lemonade fruit", "mamoncillo", "nance",
                       "oregon grape", "pigeon plum", "quenepa", "rambutan", "safou", "tamarillo", "uvaia", "velvet apple", "whortleberry",
                       "xigua melon", "yew berry", "zabala", "amla", "barberry", "cactus pear", "dwarf banana", "emperor grape", "frangipane",
                       "golden apple", "honeyberry", "ice apple", "juneberry", "kabosu", "limequat", "miracle fruit", "naranjilla", "oroblanco",
                       "pepino", "queensland nut", "rata", "saskatoon berry", "thornberry", "usuma", "victoria plum", "walnut", "xangpi", "yangtao",
                       "zostera", "anise", "blackcurrant", "chinese bayberry", "durian", "elderberry", "foxtail palm", "gac", "hawthorn",
                       "indigo berry", "jaboticaba", "kabosu fruit", "limeberry", "monstera", "noni", "orthodox blackcurrant", "pequi",
                       "quararibea cordata", "rose apple", "santol", "tahitian lime", "urava", "viburnum", "white sapote", "xique-xique",
                       "yellow pitaya", "zinfandel grape", "almond", "blood orange", "cherimoya", "dingle berry", "elephant apple", "figleaf gourd",
                       "guavaberry", "hog plum", "indian jujube", "juniper berry", "kala jamun", "loguat", "medlar", "northern spy", "okra",
                       "palm fruit", "quararibea", "sapodilla", "tree tomato", "ugli fruit", "vitamin c berry", "wolf apple", "xiao hong",
                       "yellow mangosteen", "zanzibar", "apios", "beach plum", "chestnut", "duku", "engkalak", "frost grape", "gold kiwi",
                       "himalayan mulberry", "italian prune", "june plum", "kei apple", "lucuma", "mangaba", "nannyberry", "otome", "passion fruit",
                       "quandong nut", "rose hip", "sea buckthorn", "tayberry", "utazi", "voavanga fruit", "water apple", "ximenia fruit",
                       "yellow pear", "zarzaparilla", "arbutus", "bitou", "chrysophyllum", "double coconut", "elephant ear fig", "fruit-salad plant",
                       "goji", "hairless rambutan", "insulin plant", "jambos", "kola nut", "longan fruit", "mangosteen", "nutmeg", "oleaster",
                       "pili nut", "quararibea", "ramontchi", "sapote", "texas persimmon", "uchuva", "vigna subterranea", "wax apple", "xoconostle",
                       "yellow watermelon", "zapotillo");
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
