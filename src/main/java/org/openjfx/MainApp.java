package org.openjfx;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class MainApp extends Application {

    static MediaView mediaView = new MediaView();
    static MusicCollection musicCollection = new MusicCollection();
    private static int chosenSongNr = 0;

    private int getChosenSongNr(){
        return this.chosenSongNr;
    }
    private void setChosenSongNr(int chosenSongNr){
        this.chosenSongNr = chosenSongNr;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Music player");

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("songs/"));

        FileInputStream inputPlay = new FileInputStream("src/main/java/resources/play.png");
        Image imagePlay = new Image(inputPlay);
        FileInputStream inputPause = new FileInputStream("src/main/java/resources/pause.png");
        Image imagePause = new Image(inputPause);
        ImageView imageViewAlbum = new ImageView(imagePlay);
        ImageView imageViewPlayPause = new ImageView(imagePlay);
        Button buttonPlayPause = new Button("",imageViewPlayPause);
        buttonPlayPause.setOnAction(e -> {
            if (mediaView.getMediaPlayer().getStatus().equals(MediaPlayer.Status.PLAYING)) {
                mediaView.getMediaPlayer().pause();
                imageViewPlayPause.setImage(imagePlay);
            } else {
                mediaView.getMediaPlayer().play();
                imageViewPlayPause.setImage(imagePause);
            }
        });

        FileInputStream inputReload = new FileInputStream("src/main/java/resources/reload.png");
        Image imageReload = new Image(inputReload);
        ImageView imageViewReload = new ImageView(imageReload);
        Button buttonReload = new Button("",imageViewReload);
        buttonReload.setOnAction(e -> {
            mediaView.getMediaPlayer().stop();
            mediaView.setMediaPlayer(musicCollection.getSongList().get(getChosenSongNr()).getSongMediaPlayer());
            mediaView.getMediaPlayer().play();
            imageViewPlayPause.setImage(imagePause);
        });

        FileInputStream inputNext = new FileInputStream("src/main/java/resources/next.png");
        Image imageNext = new Image(inputNext);
        ImageView imageViewNext = new ImageView(imageNext);
        Button buttonNext = new Button("",imageViewNext);
        buttonNext.setOnAction(e -> {
            if (getChosenSongNr() == musicCollection.getSongList().size() - 1) {
                setChosenSongNr(0);
            }
            else {
                setChosenSongNr(getChosenSongNr()+1);
            }
            mediaView.getMediaPlayer().stop();
            mediaView.setMediaPlayer(musicCollection.getSongList().get(getChosenSongNr()).getSongMediaPlayer());
            imageViewAlbum.setImage(musicCollection.getSongList().get(getChosenSongNr()).getSongImage());
            mediaView.getMediaPlayer().play();
            imageViewPlayPause.setImage(imagePause);
        });

        FileInputStream inputPrev = new FileInputStream("src/main/java/resources/prev.png");
        Image imagePrev = new Image(inputPrev);
        ImageView imageViewPrev = new ImageView(imagePrev);
        Button buttonPrev = new Button("",imageViewPrev);
        buttonPrev.setOnAction(e -> {
            if (getChosenSongNr() == 0) {
                setChosenSongNr(musicCollection.getSongList().size() - 1);
            }
            else {
                setChosenSongNr(getChosenSongNr()-1);
            }
            mediaView.getMediaPlayer().dispose();
            mediaView.setMediaPlayer(musicCollection.getSongList().get(getChosenSongNr()).getSongMediaPlayer());
            imageViewAlbum.setImage(musicCollection.getSongList().get(getChosenSongNr()).getSongImage());
            mediaView.getMediaPlayer().play();
            imageViewPlayPause.setImage(imagePause);
        });

        FileInputStream inputRandom = new FileInputStream("src/main/java/resources/random.png");
        Image imageRandom = new Image(inputRandom);
        ImageView imageViewRandom = new ImageView(imageRandom);
        Button buttonRandom = new Button("",imageViewRandom);
        buttonRandom.setOnAction(e -> {
            setChosenSongNr((int) (Math.random() * musicCollection.getSongList().size()));
            mediaView.getMediaPlayer().stop();
            mediaView.setMediaPlayer(musicCollection.getSongList().get(getChosenSongNr()).getSongMediaPlayer());
            imageViewAlbum.setImage(musicCollection.getSongList().get(getChosenSongNr()).getSongImage());
            mediaView.getMediaPlayer().play();
            imageViewPlayPause.setImage(imagePause);
        });

        FileInputStream inputStop = new FileInputStream("src/main/java/resources/stop.png");
        Image imageStop = new Image(inputStop);
        ImageView imageViewStop = new ImageView(imageStop);
        Button buttonStop = new Button("",imageViewStop);
        buttonStop.setOnAction(e -> {
            mediaView.getMediaPlayer().stop();
            setChosenSongNr(0);
            mediaView.setMediaPlayer(musicCollection.getSongList().get(getChosenSongNr()).getSongMediaPlayer());
            imageViewAlbum.setImage(musicCollection.getSongList().get(getChosenSongNr()).getSongImage());
            imageViewPlayPause.setImage(imagePlay);
        });

        FileInputStream inputDir = new FileInputStream("src/main/java/resources/dir.png");
        Image imageDir = new Image(inputDir);
        ImageView imageViewDir = new ImageView(imageDir);
        Button buttonDir = new Button("",imageViewDir);
        buttonDir.setOnAction(e -> {
            File selectedDirectory = directoryChooser.showDialog(primaryStage);
            mediaView.getMediaPlayer().stop();
            mediaView.getMediaPlayer().dispose();
            imageViewPlayPause.setImage(imagePlay);
            try {
                musicCollection.setSongList(selectedDirectory.getAbsolutePath());
                musicCollection.sortByArtist();
                musicCollection.printMusicCollection();
                setChosenSongNr(0);
                mediaView.setMediaPlayer(musicCollection.getSongList().get(getChosenSongNr()).getSongMediaPlayer());
                imageViewAlbum.setImage(musicCollection.getSongList().get(getChosenSongNr()).getSongImage());
            } catch (Exception ex) {
                try {
                    musicCollection.setSongList("songs/");
                } catch (Exception exc) {
                    System.out.println(exc.getMessage());
                }
            }
        });

        musicCollection.setSongList("songs/");
        musicCollection.sortByArtist();
        musicCollection.printMusicCollection();

        mediaView.setMediaPlayer(musicCollection.getSongList().get(getChosenSongNr()).getSongMediaPlayer());
        imageViewAlbum.setImage(musicCollection.getSongList().get(getChosenSongNr()).getSongImage());

        imageViewAlbum.setFitHeight(100);
        imageViewAlbum.setFitWidth(100);

        BorderPane borderPane = new BorderPane();
        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(buttonStop,buttonPrev,buttonPlayPause,buttonNext,buttonReload,buttonRandom,buttonDir);
        borderPane.setMargin(imageViewAlbum, new Insets(20,20,0,20));
        BorderPane.setMargin(hbox, new Insets(30,20,20,20));
        borderPane.setBottom(hbox);
        borderPane.setLeft(imageViewAlbum);
        hbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(borderPane, 500, 200);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }


}
