package controller;

import entities.DuplicatedFile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.Scan;
import util.Alerts;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;


public class MainController implements Initializable{

    @FXML
    private MenuItem menuItemSearch;

    @FXML
    private MenuItem menuItemStop;

    @FXML
    private MenuItem menuItemNewSearch;

    @FXML
    private MenuItem menuItemAbout;

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

    public MainController() { }

    public void menuItemSearchAction(){

        if (scanner != null) {
            menuItemStopAction();
        }

        getTypedPath();
        scanner = new Scan(selectedDirectory,labelScanningNow,this);
        scanner.init();

    }

    public void menuItemStopAction(){

        if (scanner != null) {
            scanner.stop();
            tableViewFiles.getItems().clear();
        }
    }

    public void setMenuItemNewSearchAction() {
        menuItemStopAction();
        labelScanningNow.setText("");
        textFieldFilePath.setText("");
    }

    public void menuAboutAction(ActionEvent e) {

        Stage parentStage = (Stage) ((MenuItem) e.getTarget()).getParentPopup().getOwnerWindow();
        createDialogForm(parentStage);

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

    public void updateTable(List<DuplicatedFile> dupes) {

        if (dupes != null && dupes.size() > 0) {

            setTableFactory();
            tableViewFiles.getItems().clear();
            ObservableList<DuplicatedFile> duplicatedFiles = FXCollections.observableArrayList(dupes);
            tableViewFiles.setItems(duplicatedFiles);

        }
    }

    private void setTableFactory() {
        tableColumnFileName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnFileSize.setCellValueFactory(new PropertyValueFactory<>("megaByte"));
        tableColumnFilePath.setCellValueFactory(new PropertyValueFactory<>("path"));
        tableColumnButtonOpenFolder.setCellValueFactory(new PropertyValueFactory<>("openFolder"));
    }

    private void createDialogForm(Stage parentStage) {

        try {

            FXMLLoader loader = new FXMLLoader(AboutController.class.getResource("/gui/about.fxml"));

            Pane pane = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Sobre");
            dialogStage.setScene(new Scene(pane));
            dialogStage.setResizable(false);
            dialogStage.initOwner(parentStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), Alert.AlertType.ERROR);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String path = Objects.requireNonNull(getClass().getResource("")).getPath();
        File file = new File(path);

        textFieldFilePath.setText(file.getPath());

    }
}