package services;

import controller.MainController;
import entities.DuplicatedFile;
import javafx.application.Platform;
import javafx.scene.control.Label;
import util.GenerateFileMD5;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class Scan{

    private boolean scanning;

    private final Path selectedDirectory;

    private final Label totalScanned;

    private String currentScanned;

    private MainController mainController;

    Thread thread;

    List<DuplicatedFile> duplicatedFiles = new ArrayList<>();

    public Scan(Path selectedDirectory, Label totalScanned, MainController mainController) {

        this.selectedDirectory = selectedDirectory;
        this.totalScanned = totalScanned;
        this.mainController = mainController;

    }

    public void init() {
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
                            if (localFile.isFile()) { files.add(localFile); }
                            currentScanned = localFile.toString();
                            updateLabel();
                        } else {
                            break;
                        }
                    } catch (Exception e) { System.out.println(e.getMessage()); }
                }

                try {
                    checkDuplicated(files);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                scanning = false;

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

    private void checkDuplicated(List<File> files) throws IOException {

        Map<String, File> filesMap = new HashMap<>();
        List<DuplicatedFile> dupes = new ArrayList<>();

        for (File file: files) {

            if (!scanning) { break; }

            String localMD5 = GenerateFileMD5.generateMD5(file);

            if (filesMap.containsKey(localMD5)){
                dupes.add(new DuplicatedFile(filesMap.get(localMD5)));
                dupes.add(new DuplicatedFile(file));
                currentScanned = file.toString();
                updateLabel();
            } else {
                filesMap.put(GenerateFileMD5.generateMD5(file),file);
            }

        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mainController.updateTable(dupes);
            }
        });

    }

    public boolean isScanning() {
        return scanning;
    }
}


