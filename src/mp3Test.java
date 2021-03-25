import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.io.File;
import java.io.IOException;

public class mp3Test {
    public static void main(String[] args) throws InvalidDataException, IOException, UnsupportedTagException {

        File folder = new File("songs/");
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles){
            Mp3File mp3file = new Mp3File(file.getCanonicalPath());
            System.out.println(mp3file.getId3v1Tag().getTitle() + " - " + mp3file.getId3v1Tag().getArtist());
        }
    }
}
