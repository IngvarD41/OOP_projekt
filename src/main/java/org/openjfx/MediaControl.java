package org.openjfx;

import javafx.application.Platform;
import java.util.Scanner;

import static org.openjfx.MainApp.mediaView;
import static org.openjfx.MainApp.musicCollection;

class MediaControl implements Runnable {

    @Override
    public void run() {
        mediaControlWithTextInput();
    }

    static void mediaControlWithTextInput() {

        Scanner scan = new Scanner(System.in);
        String input;
        int chosenSongNr = 0;
        String commands = """
                Choose a song by typing the songs number
                'list' - view playlist
                'play' - play chosen song, by default song 1
                'pause' - pause song
                'reload' - start song again
                'next' - play next song
                'prev' - play previous song
                'help' - view commands
                'quit' - quit
                """;

        System.out.println("MP3 player");
        System.out.println(commands);

        while (scan.hasNextLine()) {

            input = scan.nextLine().trim();

            switch (input) {
                case "play" -> {
                    mediaView.getMediaPlayer().play();
                    System.out.println("PLAYING:  " + musicCollection.getSongList().get(chosenSongNr));
                    System.out.println();
                }

                case "pause" -> {
                    mediaView.getMediaPlayer().pause();
                    System.out.println("PAUSED");
                    System.out.println();
                }

                case "reload" -> {
                    mediaView.getMediaPlayer().stop();
                    mediaView.getMediaPlayer().play();
                    System.out.println("STARTING AGAIN: " + musicCollection.getSongList().get(chosenSongNr));
                    System.out.println();
                }

                case "next" -> {
                    if (chosenSongNr == musicCollection.getSongList().size() - 1) {
                        chosenSongNr = 0;
                    }
                    else {
                        chosenSongNr += 1;
                    }
                    mediaView.getMediaPlayer().stop();
                    System.out.println("SONG:");
                    System.out.println(musicCollection.getSongList().get(chosenSongNr));
                    mediaView.setMediaPlayer(musicCollection.getSongList().get(chosenSongNr).getSongMediaPlayer());
                    mediaView.getMediaPlayer().play();
                    System.out.println("PLAYING");
                    System.out.println();
                }

                case "prev" -> {
                    if (chosenSongNr == 0) {
                        chosenSongNr = musicCollection.getSongList().size() - 1;
                    }
                    else {
                        chosenSongNr -= 1;
                    }
                    mediaView.getMediaPlayer().stop();
                    System.out.println("SONG:");
                    System.out.println(musicCollection.getSongList().get(chosenSongNr));
                    mediaView.setMediaPlayer(musicCollection.getSongList().get(chosenSongNr).getSongMediaPlayer());
                    mediaView.getMediaPlayer().play();
                    System.out.println("PLAYING");
                    System.out.println();
                }

                case "list" -> {
                    musicCollection.printMusicCollection();
                    System.out.println();
                }

                case "help" -> System.out.println(commands);
                case "quit" -> Platform.exit();

                default -> {  // to use regex in a switch
                    if (input.matches("\\d+")
                            && Integer.parseInt(input) <= musicCollection.getSongList().size()
                            && Integer.parseInt(input) > 0) {
                        mediaView.getMediaPlayer().stop();
                        System.out.println("CHOSEN SONG:");
                        chosenSongNr = Integer.parseInt(input) - 1;
                        System.out.println(musicCollection.getSongList().get(chosenSongNr));
                        mediaView.setMediaPlayer(musicCollection.getSongList().get(chosenSongNr).getSongMediaPlayer());
                    }
                    else {
                        System.out.println("Incorrect input. Type 'help' to see commands.");
                    }
                    System.out.println();
                }
            }

        }

    }
}
