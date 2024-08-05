module fxtend {
    exports io.fxtend.password;
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.materialdesign2;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.core;

    opens io.fxtend.password to javafx.fxml;
    exports io.fxtend.autocomplete;
}