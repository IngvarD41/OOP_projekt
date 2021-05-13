package org.openjfx;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Math.round;

public class MainApp extends Application {

    static MediaView mediaView = new MediaView();
    static MusicCollection musicCollection = new MusicCollection();
    private static int chosenSongNr = 0;
    private static ImageView imageViewAlbum;
    private static Text songName;
    private static Text artistName;
    private static ImageView imageViewPlayPause;
    private static Image imagePause;
    private static Text time = new Text("00:00/00:00");

    private int getChosenSongNr(){
        return this.chosenSongNr;
    }
    private void setChosenSongNr(int chosenSongNr){
        this.chosenSongNr = chosenSongNr;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("ZZZZZZZZZzzzzzzzzzzzzzz");

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("songs/"));

        FileInputStream inputBackground = new FileInputStream("src/main/java/resources/taust.png");
        FileInputStream inputPlay = new FileInputStream("src/main/java/resources/play.png");
        Image imagePlay = new Image(inputPlay);
        Image imageBackground = new Image(inputBackground);
        BackgroundImage BI = new BackgroundImage(imageBackground,
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        FileInputStream inputPause = new FileInputStream("src/main/java/resources/pause.png");
        this.imagePause = new Image(inputPause);
        this.imageViewAlbum = new ImageView(imagePlay);
        this.imageViewPlayPause = new ImageView(imagePlay);

        this.songName = new Text("ERROR");
        this.artistName = new Text("ERROR");

        songName.setFont(Font.loadFont("file:src/main/java/resources/doves.ttf", 30));
        artistName.setFont(Font.loadFont("file:src/main/java/resources/doves.ttf", 25));

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
            mediaView.getMediaPlayer().play();
            mediaView.getMediaPlayer().stop();
            setChosenSong();
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
            setChosenSong();
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
            mediaView.getMediaPlayer().stop();
            setChosenSong();
            mediaView.getMediaPlayer().play();
            imageViewPlayPause.setImage(imagePause);
        });

        FileInputStream inputRandom = new FileInputStream("src/main/java/resources/random.png");
        Image imageRandom = new Image(inputRandom);
        ImageView imageViewRandom = new ImageView(imageRandom);
        Button buttonRandom = new Button("",imageViewRandom);
        buttonRandom.setOnAction(e -> {
            setChosenSongNr((int) (Math.random() * musicCollection.getSongList().size()));
            mediaView.getMediaPlayer().play();
            mediaView.getMediaPlayer().stop();
            setChosenSong();
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
            setChosenSong();
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
                setChosenSong();
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

        setChosenSong();

        imageViewAlbum.setFitHeight(100);
        imageViewAlbum.setFitWidth(100);

        BorderPane borderPane = new BorderPane();
        HBox hbox = new HBox(10);
        VBox vbox = new VBox(2);
        vbox.getChildren().addAll(songName,artistName);
        hbox.getChildren().addAll(buttonStop,buttonPrev,buttonPlayPause,buttonNext,buttonReload,buttonRandom,buttonDir);
        borderPane.setMargin(imageViewAlbum, new Insets(20,20,0,20));
        borderPane.setMargin(hbox, new Insets(30,20,20,20));
        borderPane.setMargin(vbox, new Insets(50,0,10,20));
        borderPane.setMargin(time,new Insets(10,10,0,0));
        borderPane.setRight(time);
        borderPane.setBottom(hbox);
        borderPane.setLeft(imageViewAlbum);
        borderPane.setCenter(vbox);
        borderPane.setBackground(new Background(BI));
        hbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(borderPane, 500, 200);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }  
    
    private void setEndofMediaAndTimer(){
        musicCollection.getSongList().get(getChosenSongNr()).getSongMediaPlayer().setOnEndOfMedia(() -> {
            if (getChosenSongNr() == musicCollection.getSongList().size() - 1) {
                setChosenSongNr(0);
            }
            else {
                setChosenSongNr(getChosenSongNr()+1);
            }
            mediaView.getMediaPlayer().stop();
            setChosenSong();
            mediaView.getMediaPlayer().play();
            imageViewPlayPause.setImage(imagePause);
        });
        musicCollection.getSongList().get(getChosenSongNr()).getSongMediaPlayer().setOnPlaying(() -> {
            musicCollection.getSongList().get(getChosenSongNr()).getSongMediaPlayer().currentTimeProperty().addListener((observable, oldTime, newTime) -> {
                double totalTimeinSeconds = musicCollection.getSongList().get(getChosenSongNr()).getSongMediaPlayer().getTotalDuration().toSeconds();
                int totalMinutes = Math.floorDiv((int) totalTimeinSeconds,60);
                int totalSeconds = (int) (totalTimeinSeconds - totalMinutes * 60);
                double currentTimeinSeconds = musicCollection.getSongList().get(getChosenSongNr()).getSongMediaPlayer().getCurrentTime().toSeconds();
                int currentMinutes = Math.floorDiv((int) currentTimeinSeconds,60);
                int currentSeconds = (int) (currentTimeinSeconds - currentMinutes * 60);
                time.setText(String.format("%02d:%02d/%02d:%02d",currentMinutes,currentSeconds,totalMinutes,totalSeconds));
            });
        });
    }
    private void setChosenSong() {
        mediaView.setMediaPlayer(musicCollection.getSongList().get(getChosenSongNr()).getSongMediaPlayer());
        imageViewAlbum.setImage(musicCollection.getSongList().get(getChosenSongNr()).getSongImage());
        songName.setText(musicCollection.getSongList().get(getChosenSongNr()).getSongTitle());
        artistName.setText(musicCollection.getSongList().get(getChosenSongNr()).getSongArtist());
        setEndofMediaAndTimer();
    }


}
