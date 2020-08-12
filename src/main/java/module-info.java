module abcode {
    requires javafx.controls;
    requires javafx.fxml;

    opens abcode to javafx.fxml;
    exports abcode;
}