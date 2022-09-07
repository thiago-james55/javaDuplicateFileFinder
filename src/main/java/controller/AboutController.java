package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AboutController implements Initializable {

    @FXML
    private ImageView imageViewPhoto;

    @FXML
    public Hyperlink hyperLink;

    public AboutController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        imageViewPhoto.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/about/img.jpgx"))));

        Rectangle clip = new Rectangle(
                imageViewPhoto.getFitWidth(), imageViewPhoto.getFitHeight()
        );

        clip.setArcWidth(20);
        clip.setArcHeight(20);
        imageViewPhoto.setClip(clip);
        imageViewPhoto.setEffect(new DropShadow(20, Color.BLACK));

        hyperLink.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                try {
                    Desktop.getDesktop().browse(new URL("https://github.com/thiago-james55").toURI());
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                }


            }
        });
    }


}
