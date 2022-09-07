package entities;

import javafx.scene.control.Button;
import javafx.scene.text.TextAlignment;
import util.GenerateFileMD5;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class DuplicatedFile {

    private String name;
    private String megaByte;
    private String path;
    private String folder;
    private Button openFolder = new Button();

    private String MD5;

    public DuplicatedFile(String name, String megaByte, String path, String MD5) {
        this.name = name;
        this.megaByte = megaByte;
        this.path = path;
        this.MD5 = MD5;
        setOpenFolderOnAction();
    }

    public DuplicatedFile(File file) throws IOException {
        this.name = file.getName();
        Double bytes = Files.size(file.toPath()) / Math.pow(1024,2);
        this.megaByte = String.format("%.2f",bytes);
        this.path = file.getPath();
        this.MD5 = GenerateFileMD5.generateMD5(file);
        setOpenFolderOnAction();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMegaByte() {
        return megaByte;
    }

    public void setMegaByte(String megaByte) {
        this.megaByte = megaByte;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
        setOpenFolderOnAction();
    }

    public Button getOpenFolder() {
        return openFolder;
    }

    public void setOpenFolder(Button openFolder) {
        this.openFolder = openFolder;
    }

    public String getMD5() {
        return MD5;
    }

    public void setMD5(String MD5) {
        this.MD5 = MD5;
    }

    private void setOpenFolderOnAction(){
        if (path != null) {
            this.openFolder.setOnAction(event -> {
                try {
                    getFolder();
                    Runtime.getRuntime().exec("explorer " + folder);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            openFolder.setText("Abrir pasta");
            openFolder.setPrefWidth(110);


        }
    }

    private void getFolder(){

        File local = new File(path);
        if ( local.isDirectory() ) { folder = local.getPath(); }
        else { folder = local.getParent(); }

    }

}
