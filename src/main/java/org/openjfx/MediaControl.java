package org.openjfx;

import javafx.application.Platform;
import java.util.Scanner;

class MediaControl implements Runnable {

    @Override
    public void run() {
        mediaControlWithTextInput();
    }

    static void mediaControlWithTextInput() {

        Scanner scan = new Scanner(System.in);
        String input;

        System.out.println("Laulu mängimiseks sisestage 'play', pausile panemiseks sisestage 'pause'");


        while (scan.hasNextLine()) {

            input = scan.nextLine();

            switch (input) {
                case "play" -> {
                    MainApp.mediaView.getMediaPlayer().play();
                    System.out.println("PLAYING");
                }
                case "pause" -> {
                    MainApp.mediaView.getMediaPlayer().pause();
                    System.out.println("PAUSED");
                }
                case "list" -> MainApp.musicCollection.printMusicCollection();
                case "quit" -> Platform.exit();

                default -> System.out.println("Vale sisend. Proovi uuesti");
            }

        }

    }
}
