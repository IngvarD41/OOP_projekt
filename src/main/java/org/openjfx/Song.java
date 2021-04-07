package org.openjfx;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;

public class Song extends Mp3File {
    private String songTitle;
    private String songArtist;
    private String songAlbum;
    private File songFile;
    private MediaPlayer songMediaPlayer;

    public Song(String canonicalPath) throws InvalidDataException, IOException, UnsupportedTagException {
        super(canonicalPath, 65536, true);
        this.songTitle = this.getId3v1Tag().getTitle();
        this.songArtist = this.getId3v2Tag().getArtist();
        this.songAlbum = this.getId3v2Tag().getAlbum();
        this.songFile = new File(canonicalPath);
        this.songMediaPlayer = new MediaPlayer(new Media(this.getSongURI()));
    }

    public String getSongTitle() {
        return songTitle;
    }

    public String getSongArtist() {
        return songArtist;
    }

    public String getSongAlbum() {
        return songAlbum;
    }

    public MediaPlayer getSongMediaPlayer() {
        return songMediaPlayer;
    }

    public String getSongURI() {
        return this.songFile.toURI().toString();
    }

    public static Comparator<Song> songAlbumComparator = new Comparator<Song>() {
        @Override
        public int compare(Song song1, Song song2) {
            String songAlbum1 = song1.getSongAlbum().toUpperCase();
            String songAlbum2 = song2.getSongAlbum().toUpperCase();

            return songAlbum1.compareTo(songAlbum2);
        }
    };

    public static Comparator<Song> songTitleComparator = new Comparator<Song>() {
        @Override
        public int compare(Song song1, Song song2) {
            String songTitle1 = song1.getSongTitle().toUpperCase();
            String songTitle2 = song2.getSongTitle().toUpperCase();

            return songTitle1.compareTo(songTitle2);
        }
    };

    public static Comparator<Song> songArtistComparator = new Comparator<Song>() {
        @Override
        public int compare(Song song1, Song song2) {
            String songArtist1 = song1.getSongArtist().toUpperCase();
            String songArtist2 = song2.getSongArtist().toUpperCase();

            return songArtist1.compareTo(songArtist2);
        }
    };

    @Override
    public String toString() {
        return songTitle + "\t" + songArtist + "\t" + songAlbum;
    }

    public void printFormattedSong(String formatString) {
        System.out.printf(formatString, songTitle, songArtist, songAlbum);
    }
}
