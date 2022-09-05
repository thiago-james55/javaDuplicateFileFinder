package services;


import entities.DuplicatedFile;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import util.Alerts;


import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Duration;
import java.util.*;

public class Scan{

    private boolean scanning;

    private final Path selectedDirectory;

    private final Label totalScanned;

    private String currentScanned;

    Timeline update;

    Thread thread;

    List<DuplicatedFile> duplicatedFiles = new ArrayList<>();

    public Scan(Path selectedDirectory, Label totalScanned) {

        this.selectedDirectory = selectedDirectory;
        this.totalScanned = totalScanned;
        currentScanned = selectedDirectory.toString();
        totalScanned.setText(currentScanned);

        getRoots(selectedDirectory);

    }

    public void stop() {

        scanning = false;
        thread.interrupt();
    }

    private void getRoots(Path path) {

        scanning = true;

        thread = new Thread(() -> {

            Path local;
            scanning = true;
            List<File> files = new ArrayList<>();

            Iterator<Path> roots = null;

            try { roots = Files.walk(path).iterator(); }
            catch (Exception e) { e.printStackTrace(); }

            if (roots != null) {

                File localFile = null;

                while (scanning) {

                    try {
                        if (roots.hasNext()) {
                            localFile = roots.next().toFile();
                            files.add(localFile);
                            currentScanned = localFile.toString();
                            updateLabel();
                        } else {
                            scanning = false;
                        }
                    } catch (Exception e) { System.out.println(e.getMessage()); }
                }


            }

        });

        thread.start();

    }

    private void updateLabel() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                totalScanned.setText(currentScanned);
            }
        });
    }


}


