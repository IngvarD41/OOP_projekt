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

        MusicCollection musicCollection = new MusicCollection();
        musicCollection.setSongList("songs/");
        musicCollection.sortByArtist();
        musicCollection.printMusicCollection();

        // mediaView-i lisatakse listist üks mediaPlayer
        mediaView.setMediaPlayer(musicCollection.getSongList().get(3).getSongMediaPlayer());

        // uue threadi tekitamine kasutajaga suhtlemiseks
        Runnable runnable = new MediaControl();
        Thread userInput = new Thread(runnable);
        userInput.start();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
