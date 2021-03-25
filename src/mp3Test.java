import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.io.IOException;

public class mp3Test {
    public static void main(String[] args) throws InvalidDataException, IOException, UnsupportedTagException {
        Mp3File mp3file = new Mp3File("src/jama.mp3");
        System.out.println(mp3file.getId3v1Tag().getArtist());
    }
}
