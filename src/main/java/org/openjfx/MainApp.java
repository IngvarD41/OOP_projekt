package org.openjfx;

import javafx.application.Application;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.util.Scanner;

public class MainApp extends Application {

    static MediaView mediaView = new MediaView();
    static MusicCollection musicCollection = new MusicCollection();

    @Override
    public void start(Stage primaryStage) throws Exception {

        System.out.println("Insert path of song folder, 'def' for default folder: ");

        Scanner scanner = new Scanner(System.in);
        String dir = null;
        if (scanner.hasNextLine()) {
            dir = scanner.nextLine().trim();
            if (dir.equals("def")){
                musicCollection.setSongList("songs/");
            }
            else{
                musicCollection.setSongList(dir);
            }
        }

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
