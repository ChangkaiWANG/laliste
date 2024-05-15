module com.laliste {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;

    opens com.laliste to javafx.fxml;
    exports com.laliste;
}
