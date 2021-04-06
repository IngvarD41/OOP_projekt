package org.openjfx;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

public class MusicCollection {
    private ArrayList<Song> songList = new ArrayList<>();

    public ArrayList<Song> getSongList() {
        return songList;
    }

    void setSongList(String path) throws IOException, InvalidDataException, UnsupportedTagException {
        this.songList.clear();
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            Song song = new Song(file.getCanonicalPath());
            this.songList.add(song);
        }
    }

    void sortByAlbum() {
        Collections.sort(songList, Song.songAlbumComparator);
    }

    void sortByTitle() {
        Collections.sort(songList, Song.songTitleComparator);
    }

    void sortByArtist() {
        Collections.sort(songList, Song.songArtistComparator);
    }

    void printMusicCollection() {
        System.out.printf("+---------------------------------------------------" +
                "------------------------------------------------------------+%n");
        System.out.printf("| %-2s | %-5s | %-30s | %-30s | %-30s |%n","  " ,"Index", "Title", "Artist", "Album");
        System.out.printf("+--------------------------------------------------" +
                "-------------------------------------------------------------+%n");
        for (Song song : this.getSongList()) {
            System.out.printf("| %-2s ",songList.indexOf(song)+1);
            song.printFormatedSong("| %-5s | %-30s | %-30s | %-30s |%n");
        }
        System.out.printf("+--------------------------------------------------" +
                "-------------------------------------------------------------+%n");
    }
}
