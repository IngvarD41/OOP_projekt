import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InvalidDataException, IOException, UnsupportedTagException {
        MusicCollection musicCollection = new MusicCollection();
        musicCollection.setSongList("songs/");
        musicCollection.sortByArtist();
        musicCollection.printMusicCollection();
    }
}
