package org.openjfx;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;

public class MainApp extends Application {

    static MediaPlayer mediaPlayer; // saab teisest classist ligi

    @Override
    public void start(Stage primaryStage) throws Exception {

        //Initialising path of the media file, replace this with your file path
        String path = "/home/san/test.mp3";

        //Instantiating Media class
        Media media = new Media(new File(path).toURI().toString());

        //Instantiating MediaPlayer class
        mediaPlayer = new MediaPlayer(media);

        Runnable runnable = new MediaControl();
        Thread userInput = new Thread(runnable);
        userInput.start();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
