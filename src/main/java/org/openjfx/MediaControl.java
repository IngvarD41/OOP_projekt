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
                Choose a song by typing the song's number
                'list' - view playlist
                'play' - play chosen song, by default song 1
                'pause' - pause song
                'reload' - start song again
                'next' - play next song
                'prev' - play previous song
                'random' - plays a random song from the list
                'sort' - gives options to sort the songs
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
                    System.out.print("PLAYING: ");
                    musicCollection.getSongList().get(chosenSongNr).printFormattedSong(" %-2s - %-2s - %-2s %n");
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
                    System.out.print("STARTING AGAIN: ");
                    musicCollection.getSongList().get(chosenSongNr).printFormattedSong(" %-2s - %-2s - %-2s %n");
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
                    System.out.print("PLAYING: ");
                    musicCollection.getSongList().get(chosenSongNr).printFormattedSong(" %-2s - %-2s - %-2s %n");
                    mediaView.setMediaPlayer(musicCollection.getSongList().get(chosenSongNr).getSongMediaPlayer());
                    mediaView.getMediaPlayer().play();
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
                    System.out.print("PLAYING: ");
                    musicCollection.getSongList().get(chosenSongNr).printFormattedSong(" %-2s - %-2s - %-2s %n");
                    mediaView.setMediaPlayer(musicCollection.getSongList().get(chosenSongNr).getSongMediaPlayer());
                    mediaView.getMediaPlayer().play();
                    System.out.println();
                }

                case "list" -> {
                    musicCollection.printMusicCollection();
                    System.out.println();
                }

                case "help" -> System.out.println(commands);
                case "quit" -> Platform.exit();
                case "random" -> {
                    chosenSongNr = (int) (Math.random() * musicCollection.getSongList().size());
                    mediaView.getMediaPlayer().stop();
                    System.out.print("PLAYING: ");
                    musicCollection.getSongList().get(chosenSongNr).printFormattedSong(" %-2s - %-2s - %-2s %n");
                    mediaView.setMediaPlayer(musicCollection.getSongList().get(chosenSongNr).getSongMediaPlayer());
                    mediaView.getMediaPlayer().play();
                    System.out.println();
                }
                case "sort" -> {
                    System.out.println("""
                            Sort by ... 
                            'title' / 'artist' / 'album'
                            """);
                    input = scan.nextLine().trim();
                    switch (input){
                        case "title" -> {
                            musicCollection.sortByTitle();
                            musicCollection.printMusicCollection();
                            System.out.println();
                        }

                        case "artist" -> {
                            musicCollection.sortByArtist();
                            musicCollection.printMusicCollection();
                            System.out.println();
                        }

                        case "album" -> {
                            musicCollection.sortByAlbum();
                            musicCollection.printMusicCollection();
                            System.out.println();
                        }
                        default -> System.out.println("Incorrect input. Exiting sort menu.");
                    }
                }

                default -> {  // to use regex in a switch
                    if (input.matches("\\d+")
                            && Integer.parseInt(input) <= musicCollection.getSongList().size()
                            && Integer.parseInt(input) > 0) {
                        mediaView.getMediaPlayer().stop();
                        System.out.print("CHOSEN SONG: ");
                        chosenSongNr = Integer.parseInt(input) - 1;
                        musicCollection.getSongList().get(chosenSongNr).printFormattedSong(" %-2s - %-2s - %-2s %n");
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
