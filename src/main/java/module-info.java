module com.stygigoth.fosspad {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.stygigoth.fosspad to javafx.fxml;
    exports com.stygigoth.fosspad;
}