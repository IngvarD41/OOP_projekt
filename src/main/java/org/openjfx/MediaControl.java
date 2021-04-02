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
                case "play": {
                    MainApp.mediaPlayer.play();
                    System.out.println("PLAYING");
                    break;
                }
                case "pause": {
                    MainApp.mediaPlayer.pause();
                    System.out.println("PAUSED");
                    break;
                }
                case "quit": {
                    Platform.exit();
                    break;
                }
                default:
                    System.out.println("Vale sisend. Proovi uuesti");
            }

        }

    }
}
