module com.tgs.javaduplicatefilefinder {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.tgs.javaduplicatefilefinder to javafx.fxml;
    exports com.tgs.javaduplicatefilefinder;
}