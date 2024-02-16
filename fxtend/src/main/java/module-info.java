module fxtend {
    exports org.fxtend.password;
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.materialdesign2;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.core;

    opens org.fxtend.password to javafx.fxml;
}