package org.openjfx;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

public class MainApp extends Application {

    MediaView mediaView = new MediaView();
    MusicCollection musicCollection = new MusicCollection();
    private int chosenSongNr = 0;
    private ImageView imageViewAlbum;
    private Text songName;
    private Text artistName;
    private ImageView imageViewPlayPause;
    private Image imagePause;
    private Image imagePlay;
    private final Text time = new Text("00:00/00:00");

    private int getChosenSongNr() {
        return this.chosenSongNr;
    }

    private void setChosenSongNr(int chosenSongNr) {
        this.chosenSongNr = chosenSongNr;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("ZZZZZZZZZzzzzzzzzzzzzzz");

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("songs/"));

        FileInputStream inputBackground = new FileInputStream("src/main/java/resources/taust.png");
        FileInputStream inputPlay = new FileInputStream("src/main/java/resources/play.png");
        this.imagePlay = new Image(inputPlay);
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

        Button buttonPlayPause = new Button("", imageViewPlayPause);
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
        Button buttonReload = new Button("", imageViewReload);
        buttonReload.setOnAction(e -> {
            mediaView.getMediaPlayer().play();
            mediaView.getMediaPlayer().stop();
            setChosenSong();
            mediaView.getMediaPlayer().play();
            imageViewPlayPause.setImage(imagePause);
        });

        FileInputStream inputList = new FileInputStream("src/main/java/resources/list.png");
        Image imageList = new Image(inputList);
        ImageView imageViewList = new ImageView(imageList);
        Button buttonList = new Button("", imageViewList);
        buttonList.setOnAction(e -> openListWindow());

        FileInputStream inputNext = new FileInputStream("src/main/java/resources/next.png");
        Image imageNext = new Image(inputNext);
        ImageView imageViewNext = new ImageView(imageNext);
        Button buttonNext = new Button("", imageViewNext);
        buttonNext.setOnAction(e -> {
            if (getChosenSongNr() == musicCollection.getSongList().size() - 1) {
                setChosenSongNr(0);
            } else {
                setChosenSongNr(getChosenSongNr() + 1);
            }
            mediaView.getMediaPlayer().stop();
            setChosenSong();
            mediaView.getMediaPlayer().play();
            imageViewPlayPause.setImage(imagePause);
        });

        FileInputStream inputPrev = new FileInputStream("src/main/java/resources/prev.png");
        Image imagePrev = new Image(inputPrev);
        ImageView imageViewPrev = new ImageView(imagePrev);
        Button buttonPrev = new Button("", imageViewPrev);
        buttonPrev.setOnAction(e -> {
            if (getChosenSongNr() == 0) {
                setChosenSongNr(musicCollection.getSongList().size() - 1);
            } else {
                setChosenSongNr(getChosenSongNr() - 1);
            }
            mediaView.getMediaPlayer().stop();
            setChosenSong();
            mediaView.getMediaPlayer().play();
            imageViewPlayPause.setImage(imagePause);
        });

        FileInputStream inputRandom = new FileInputStream("src/main/java/resources/random.png");
        Image imageRandom = new Image(inputRandom);
        ImageView imageViewRandom = new ImageView(imageRandom);
        Button buttonRandom = new Button("", imageViewRandom);
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
        Button buttonStop = new Button("", imageViewStop);
        buttonStop.setOnAction(e -> {
            mediaView.getMediaPlayer().stop();
            setChosenSongNr(0);
            setChosenSong();
            imageViewPlayPause.setImage(imagePlay);
        });

        FileInputStream inputDir = new FileInputStream("src/main/java/resources/dir.png");
        Image imageDir = new Image(inputDir);
        ImageView imageViewDir = new ImageView(imageDir);
        Button buttonDir = new Button("", imageViewDir);
        buttonDir.setOnAction(e -> {
            File selectedDirectory = directoryChooser.showDialog(primaryStage);
            mediaView.getMediaPlayer().stop();
            imageViewPlayPause.setImage(imagePlay);
            try {
                musicCollection.setSongList(selectedDirectory.getAbsolutePath());
                musicCollection.sortByArtist();
                setChosenSongNr(0);
                setChosenSong();
            } catch (Exception ex) {
                try {
                    musicCollection.setSongList("songs/");
                    musicCollection.sortByArtist();
                } catch (Exception exc) {
                    System.out.println(exc.getMessage());
                }
            }
        });

        musicCollection.setSongList("songs/");
        musicCollection.sortByArtist();

        setChosenSong();

        imageViewAlbum.setFitHeight(120);
        imageViewAlbum.setFitWidth(120);

        BorderPane borderPane = new BorderPane();
        HBox hbox = new HBox(10);
        VBox vbox = new VBox(2);
        VBox vbox2 = new VBox(2);
        vbox.getChildren().addAll(songName, artistName);
        hbox.getChildren().addAll(buttonList, buttonStop, buttonPrev, buttonPlayPause, buttonNext, buttonReload, buttonRandom, buttonDir);
        vbox2.getChildren().add(time);
        BorderPane.setMargin(imageViewAlbum, new Insets(0, 20, 0, 20));
        BorderPane.setMargin(hbox, new Insets(15, 20, 20, 20));
        BorderPane.setMargin(vbox, new Insets(30, 0, 10, 20));
        BorderPane.setMargin(vbox2, new Insets(2, 10, 0, 0));
        borderPane.setTop(vbox2);
        borderPane.setBottom(hbox);
        borderPane.setLeft(imageViewAlbum);
        borderPane.setCenter(vbox);
        borderPane.setBackground(new Background(BI));
        hbox.setAlignment(Pos.CENTER);
        vbox2.setAlignment(Pos.TOP_RIGHT);
        Scene scene = new Scene(borderPane, 500, 200);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

    private void setEndofMediaAndTimer() {
        musicCollection.getSongList().get(getChosenSongNr()).getSongMediaPlayer().setOnEndOfMedia(() -> {
            if (getChosenSongNr() == musicCollection.getSongList().size() - 1) {
                setChosenSongNr(0);
            } else {
                setChosenSongNr(getChosenSongNr() + 1);
            }
            mediaView.getMediaPlayer().stop();
            setChosenSong();
            mediaView.getMediaPlayer().play();
            imageViewPlayPause.setImage(imagePause);
        });
        musicCollection.getSongList().get(getChosenSongNr()).getSongMediaPlayer().setOnPlaying(() -> {
            musicCollection.getSongList().get(getChosenSongNr()).getSongMediaPlayer().currentTimeProperty().addListener((observable, oldTime, newTime) -> {
                double totalTimeinSeconds = musicCollection.getSongList().get(getChosenSongNr()).getSongMediaPlayer().getTotalDuration().toSeconds();
                int totalMinutes = Math.floorDiv((int) totalTimeinSeconds, 60);
                int totalSeconds = (int) (totalTimeinSeconds - totalMinutes * 60);
                double currentTimeinSeconds = musicCollection.getSongList().get(getChosenSongNr()).getSongMediaPlayer().getCurrentTime().toSeconds();
                int currentMinutes = Math.floorDiv((int) currentTimeinSeconds, 60);
                int currentSeconds = (int) (currentTimeinSeconds - currentMinutes * 60);
                time.setText(String.format("%02d:%02d/%02d:%02d", currentMinutes, currentSeconds, totalMinutes, totalSeconds));
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

    private void openListWindow() {
        ListView<Song> songsListView = new ListView<>();
        ArrayList<Song> songsArraylist = new ArrayList<>(musicCollection.getSongList());
        ObservableList<Song> songsObs = FXCollections.observableArrayList(songsArraylist);
        songsListView.setItems(songsObs);
        songsListView.setStyle( "-fx-font-family: monospace");
        songsListView.setPrefHeight(300);
        songsListView.setPrefWidth(800);

        Button buttonSortTitle = new Button("Title sort");
        buttonSortTitle.setOnAction(e -> {
            musicCollection.sortByTitle();
            songsArraylist.clear();
            songsArraylist.addAll(musicCollection.getSongList());
            songsObs.clear();
            songsObs.addAll(songsArraylist);
            songsListView.setItems(songsObs);
            mediaView.getMediaPlayer().stop();
            setChosenSongNr(0);
            setChosenSong();
            imageViewPlayPause.setImage(imagePlay);
        });

        Button buttonSortArtist = new Button("Artist Sort");
        buttonSortArtist.setOnAction(e -> {
            musicCollection.sortByArtist();
            songsArraylist.clear();
            songsArraylist.addAll(musicCollection.getSongList());
            songsObs.clear();
            songsObs.addAll(songsArraylist);
            songsListView.setItems(songsObs);
            mediaView.getMediaPlayer().stop();
            setChosenSongNr(0);
            setChosenSong();
            imageViewPlayPause.setImage(imagePlay);
        });

        Button buttonSortAlbum = new Button("Album sort");
        buttonSortAlbum.setOnAction(e -> {
            mediaView.getMediaPlayer().stop();
            musicCollection.sortByAlbum();
            songsArraylist.clear();
            songsArraylist.addAll(musicCollection.getSongList());
            songsObs.clear();
            songsObs.addAll(songsArraylist);
            songsListView.setItems(songsObs);
            setChosenSongNr(0);
            setChosenSong();
            imageViewPlayPause.setImage(imagePlay);
        });

        songsListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    setChosenSongNr(musicCollection.getSongList().indexOf(newValue));
                    mediaView.getMediaPlayer().stop();
                    setChosenSong();
                    mediaView.getMediaPlayer().play();
                    imageViewPlayPause.setImage(imagePause);
                });

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(buttonSortTitle,buttonSortArtist,buttonSortAlbum);
        HBox hbox = new HBox(songsListView,vbox);
        Scene scene = new Scene(hbox, 900, 300);
        Stage stage = new Stage();
        stage.setTitle("");
        stage.setScene(scene);
        stage.show();
    }
}
