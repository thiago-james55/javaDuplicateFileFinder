package entities;

import javafx.scene.control.Button;

public class DuplicatedFile {

    private String name;
    private String megaByte;
    private String path;
    private Button openFolder = new Button();

    public DuplicatedFile(String name, String megaByte, String path) {
        this.name = name;
        this.megaByte = megaByte;
        this.path = path;
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
    }

    private void setOpenFolderOnAction(){
        if (path != null) {
            this.openFolder.setOnAction(event -> {

            });
        }
    }
}
