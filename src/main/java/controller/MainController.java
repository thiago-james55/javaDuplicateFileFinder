package controller;

import application.MainApplication;
import entities.DuplicatedFile;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;

import java.io.File;

public class MainController {

    @FXML
    private MenuItem menuItemNewSearch;

    @FXML
    private Menu menuAbout;

    @FXML
    private Button buttonSelectPath;

    @FXML
    private TextField textFieldFilePath;

    @FXML
    private TableView<DuplicatedFile> tableViewFiles;

    private File selectedDirectory;

    public void buttonSelectPathClick(ActionEvent e){

        chooseDirectory(e);

    }

    private void chooseDirectory(ActionEvent e) {

        DirectoryChooser directoryChooser = new DirectoryChooser();

        if (selectedDirectory != null) {
            directoryChooser.setInitialDirectory(selectedDirectory);
        }

        selectedDirectory = directoryChooser.showDialog(buttonSelectPath.getScene().getWindow());

        if (selectedDirectory != null) {
            textFieldFilePath.setText(selectedDirectory.toString());
        }

    }



}