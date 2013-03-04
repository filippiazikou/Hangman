package server;

import java.awt.List;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import sun.misc.IOUtils;

public class Game {

    private BufferedInputStream in;
    private BufferedOutputStream out;
    private String chosenWord;
    private int lives;
    private int correctLetters;
    private int score;

    Game(BufferedInputStream in, BufferedOutputStream out) {
        this.in = in;
        this.out = out;
        chosenWord = "";
        lives = 6;
        correctLetters = 0;
        score = 0;

        ChooseAction();
    }

    private void ChooseAction() {
        String msg = ReceiveMessage();
        if (msg.startsWith("ERROR")) {
            System.out.println("An Error occured at ChooseAction");
        } else if (msg.startsWith("START")) {
            StartGame();
        } else if (msg.startsWith("LETTER")) {
            CheckLetter(msg.charAt(6));
        } else if (msg.startsWith("WORD")) {
            String[] splitted;
            splitted = msg.split("~");
            CheckWord(splitted[1]);
        }
    }

    private void StartGame() {
        /*Choose a random word from the file and convert all letters to upper case*/
        WordFinder word = new WordFinder();
        chosenWord = word.getWord();
        chosenWord = chosenWord.toUpperCase();
        int noOfLetters = chosenWord.length();
        lives = 6;
        correctLetters = 0;

        System.out.println(chosenWord);
        //Send the message with '~' character at the end of string
        SendMessage(Integer.toString(noOfLetters) + "~" + Integer.toString(score) + "~");

        //Wait for the next action of user
        ChooseAction();
    }

    private void CheckLetter(char charAt) {
        if (chosenWord.contains(String.valueOf(charAt))) {
            /*Create an array of integers - 0 if the letter is tha same - 1 otherwise
             * eg word: car, charAt = 'a' array:[0][1][0]
             */
            char[] positions = new char[chosenWord.length()];
            for (int i = 0; i < chosenWord.length(); i++) {
                if (chosenWord.charAt(i) == charAt) {
                    positions[i] = '1';
                    correctLetters += 1;
                } else {
                    positions[i] = '0';
                }
            }
            String positionsS = new String(positions);
            if (correctLetters == chosenWord.length()) {
                //Send the message with '~' character at the end of string and then the score and the total games played
                score = score + 1;
                SendMessage("WON" + "~" + Integer.toString(score) + "~");
            } else {
                SendMessage("EXIST~" + positionsS + "~");
            }
        } else {
            lives--;
            if (lives > 0) {
                SendMessage("NOT_EXIST~" + Integer.toString(lives));
            } else {
                score = score-1;
                SendMessage("LOST" + "~" + Integer.toString(score) + "~");
            }
        }
        ChooseAction();
    }

    private void SendMessage(String msg) {
        try {
            byte[] toClient = msg.getBytes();
            out.write(toClient, 0, toClient.length);
            out.flush();
        } catch (IOException ioExeption) {
            System.out.println("Unable to Send Message to Client:" + ioExeption.toString());
        }
    }

    private String ReceiveMessage() {
        try {
            byte[] msg = new byte[4096];
            int n = in.read(msg, 0, msg.length);
            return (new String(msg));
        } catch (IOException ioExeption) {
            System.out.println("Unable to Recieve Message to Server:" + ioExeption.toString());
            return "ERROR";
        }
    }

    private void CheckWord(String string) {
        System.out.println(string);
        if (string.toUpperCase().equals(chosenWord)){
             score = score + 1;
             SendMessage("WON" + "~" + Integer.toString(score) + "~");
        }
        else {
             SendMessage("LOST" + "~" + Integer.toString(score) + "~");
        }
        ChooseAction();
    }
}