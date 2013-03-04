/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.*;
import java.util.Random;

class WordFinder {
    String getWord() {
        try{
            LineNumberReader  lnr = new LineNumberReader(new FileReader(new File("words.txt")));
            lnr.skip(Long.MAX_VALUE);
            int noOfWords = lnr.getLineNumber();
            // Open the file that is the first 
            // command line parameter
            FileInputStream fstream = new FileInputStream("words.txt");
        
            Random randomGenerator = new Random();
            int randomInt = randomGenerator.nextInt(noOfWords);
            
             // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));     
            int i;
            for (i=0 ; i<randomInt ; i++) 
                br.readLine();
            String strLine = br.readLine(); 
            //Close the input stream
            in.close();
        
            return strLine;
       }catch (Exception e){//Catch exception if any
            return "ERROR";
       }
    }
}