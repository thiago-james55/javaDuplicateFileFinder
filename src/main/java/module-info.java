module application {
    requires javafx.controls;
    requires javafx.fxml;
    requires commons.codec;
    requires java.desktop;

    opens application to javafx.fxml;
    exports application;

    opens entities to javafx.fxml;
    exports entities;

    exports controller;
    opens controller to javafx.fxml;
    exports services;
    opens services to javafx.fxml;


}