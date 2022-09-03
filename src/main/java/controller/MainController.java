package controller;

import application.MainApplication;
import entities.DuplicatedFile;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import services.Scan;
import util.Alerts;

import java.io.File;

public class MainController {

    @FXML
    private MenuItem menuItemSearch;

    @FXML
    private MenuItem menuItemStop;

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

    @FXML
    private TableColumn<DuplicatedFile,String> tableColumnFileName;
    
    @FXML
    private TableColumn<DuplicatedFile,String> tableColumnFileSize;

    @FXML
    private TableColumn<DuplicatedFile,String> tableColumnFilePath;

    @FXML
    private TableColumn<DuplicatedFile,Button> tableColumnButtonOpenFolder;

    private Scan scanner;

    private File selectedDirectory;

    public MainController() {}

    public void menuItemSearchAction(){
        getTypedPath();
        //scanner = new Scan(selectedDirectory,tableViewFiles,tableColumnFileName,tableColumnFileSize,tableColumnFilePath,tableColumnButtonOpenFolder);
    }

    public void buttonSelectPathAction(ActionEvent e){
        chooseDirectory(e);
    }

    private void getTypedPath() {

        if ( (selectedDirectory != null) && (textFieldFilePath.getText() != null) &&
            (selectedDirectory.toString().equals(textFieldFilePath.getText())) ) {
            return;
        }

        selectedDirectory = new File(textFieldFilePath.getText());

        if ( !selectedDirectory.isDirectory() ) {
            Alerts.showAlert("Erro!","Caminho selecionado não é uma pasta!","Selecione um caminho válido!", Alert.AlertType.ERROR);
            throw new IllegalArgumentException("Typed directory is not a folder!");
        }

    }

    private void chooseDirectory(ActionEvent e) {

        DirectoryChooser directoryChooser = new DirectoryChooser();

        if (selectedDirectory != null && selectedDirectory.isDirectory()) {
            directoryChooser.setInitialDirectory(selectedDirectory);
        }

        selectedDirectory = directoryChooser.showDialog(buttonSelectPath.getScene().getWindow());

        if (selectedDirectory != null) {
            textFieldFilePath.setText(selectedDirectory.toString());
        }

    }



}