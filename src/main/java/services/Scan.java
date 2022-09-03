package services;

import entities.DuplicatedFile;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.File;

public class Scan {

    private boolean scanning;

    private TableView<DuplicatedFile> tableViewFiles;

    private TableColumn<DuplicatedFile,String> tableColumnFileName;

    private TableColumn<DuplicatedFile,String> tableColumnFileSize;

    private TableColumn<DuplicatedFile,String> tableColumnFilePath;

    private TableColumn<DuplicatedFile, Button> tableColumnButtonOpenFolder;

    private File selectedDirectory;


}
