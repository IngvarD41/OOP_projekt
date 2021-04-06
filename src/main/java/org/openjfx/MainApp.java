package org.openjfx;

import javafx.application.Application;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class MainApp extends Application {

    static MediaView mediaView = new MediaView();
    static MusicCollection musicCollection = new MusicCollection();

    @Override
    public void start(Stage primaryStage) throws Exception {

        musicCollection.setSongList("songs/");
        musicCollection.sortByArtist();
        musicCollection.printMusicCollection();

        // assigning a MediaPlayer object to mediaView
        mediaView.setMediaPlayer(musicCollection.getSongList().get(0).getSongMediaPlayer());

        // new thread for user text input
        Runnable runnable = new MediaControl();
        Thread userInput = new Thread(runnable);
        userInput.start();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
