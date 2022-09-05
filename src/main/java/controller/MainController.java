package controller;

import entities.DuplicatedFile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import services.Scan;
import util.Alerts;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

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

    @FXML
    private Label labelScanningNow;

    private Scan scanner;

    private Path selectedDirectory;

    public MainController() {}

    public void menuItemSearchAction(){
        getTypedPath();
        scanner = new Scan(selectedDirectory,labelScanningNow);
    }

    public void menuItemStopAction(){

        if (scanner != null) {
            scanner.stop();
            scanner = new Scan(selectedDirectory,labelScanningNow);
        }
    }

    public void buttonSelectPathAction(ActionEvent e){
        chooseDirectory(e);
    }

    private void getTypedPath() {

        if ( (selectedDirectory != null) && (textFieldFilePath.getText() != null) &&
            (selectedDirectory.toString().equals(textFieldFilePath.getText())) ) {
            return;
        }
        selectedDirectory = Paths.get(Objects.requireNonNull(textFieldFilePath.getText()));


        if ( !Files.isDirectory(selectedDirectory) ) {
            Alerts.showAlert("Erro!","Caminho selecionado não é uma pasta!","Selecione um caminho válido!", Alert.AlertType.ERROR);
            throw new IllegalArgumentException("Typed directory is not a folder!");
        }

    }

    private void chooseDirectory(ActionEvent e) {

        DirectoryChooser directoryChooser = new DirectoryChooser();

        if (selectedDirectory != null && Files.isDirectory(selectedDirectory)) {
            directoryChooser.setInitialDirectory(selectedDirectory.toFile());
        }

        File pickedDirectory = directoryChooser.showDialog(buttonSelectPath.getScene().getWindow());

        if (pickedDirectory != null) {
            selectedDirectory = pickedDirectory.toPath();
            textFieldFilePath.setText(selectedDirectory.toString());
        }



    }


}