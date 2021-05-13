package org.openjfx;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;

class Song extends Mp3File {
    private String songTitle;
    private String songArtist;
    private String songAlbum;
    private File songFile;
    private MediaPlayer songMediaPlayer;
    private Image songImage;

    Song(String canonicalPath) throws InvalidDataException, IOException, UnsupportedTagException {
        super(canonicalPath, 65536, true);
        this.songTitle = this.getId3v1Tag().getTitle();
        this.songArtist = this.getId3v2Tag().getArtist();
        this.songAlbum = this.getId3v2Tag().getAlbum();
        this.songFile = new File(canonicalPath);
        this.songMediaPlayer = new MediaPlayer(new Media(this.getSongURI()));
        byte[] imageData = this.getId3v2Tag().getAlbumImage();
        BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageData));
        this.songImage = SwingFXUtils.toFXImage(img, null);
    }

    String getSongTitle() {
        return songTitle;
    }

    String getSongArtist() {
        return songArtist;
    }

    String getSongAlbum() {
        return songAlbum;
    }

    MediaPlayer getSongMediaPlayer() {
        return songMediaPlayer;
    }

    String getSongURI() {
        return this.songFile.toURI().toString();
    }

    public Image getSongImage() {
        return songImage;
    }

    static Comparator<Song> songAlbumComparator = new Comparator<Song>() {
        @Override
        public int compare(Song song1, Song song2) {
            String songAlbum1 = song1.getSongAlbum().toUpperCase();
            String songAlbum2 = song2.getSongAlbum().toUpperCase();

            return songAlbum1.compareTo(songAlbum2);
        }
    };

    static Comparator<Song> songTitleComparator = new Comparator<Song>() {
        @Override
        public int compare(Song song1, Song song2) {
            String songTitle1 = song1.getSongTitle().toUpperCase();
            String songTitle2 = song2.getSongTitle().toUpperCase();

            return songTitle1.compareTo(songTitle2);
        }
    };

    static Comparator<Song> songArtistComparator = new Comparator<Song>() {
        @Override
        public int compare(Song song1, Song song2) {
            String songArtist1 = song1.getSongArtist().toUpperCase();
            String songArtist2 = song2.getSongArtist().toUpperCase();

            return songArtist1.compareTo(songArtist2);
        }
    };

    @Override
    public String toString() {
        return this.returnFormattedSong("| %-30s | %-30s | %-30s");
    }

    void printFormattedSong(String formatString) {
        System.out.printf(formatString, songTitle, songArtist, songAlbum);
    }

   private String returnFormattedSong(String formatString) {
        return String.format(formatString, songTitle, songArtist, songAlbum);
    }
}
