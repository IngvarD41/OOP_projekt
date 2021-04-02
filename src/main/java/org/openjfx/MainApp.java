package org.openjfx;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.util.ArrayList;

public class MainApp extends Application {

    static MediaView mediaView = new MediaView(); // saab teisest classist ligi

    @Override
    public void start(Stage primaryStage) throws Exception {

        Media media1 = new Media("file:/home/san/test.mp3");
        Media media2 = new Media("file:/home/san/test2.mp3");

        // tehakse mediaPlayerid ja lisatakse need mediaPlayerite listi
        ArrayList<MediaPlayer> mediaPlayers = new ArrayList<>();
        mediaPlayers.add(new MediaPlayer(media1));
        mediaPlayers.add(new MediaPlayer(media2));

        // mediaView-i lisatakse listist üks mediaPlayer
        mediaView.setMediaPlayer(mediaPlayers.get(0));

        // uue threadi tekitamine kasutajaga suhtlemiseks
        Runnable runnable = new MediaControl();
        Thread userInput = new Thread(runnable);
        userInput.start();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
