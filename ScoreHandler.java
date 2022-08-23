package assignment2;

import javax.swing.*;
import java.io.*;
import java.util.*;

public class ScoreHandler {

    static String str = "";
    static BufferedReader in;

    //opens the scores text file
    public static void openScoresFile(){
        try {
            in = new BufferedReader(new FileReader("tread_scores.txt"));

        } catch (IOException ioEx){
            System.out.println("Scores file doesn't exist or could not be opened");
            in = null;
        }
    }

    //adds users score to the end of the scores text file
    public static void outputScore(String namee){

        openScoresFile();

        //if the file already exists, then copy all the lines into a string
        if (in != null) {
            try {
                while (in.ready()) {
                    str = str + in.readLine() + "\n";
                }
            } catch (IOException ioEx) {
                System.out.println("ioEx");
                in = null;
            }
        }

        //output existing scores (if they exist) and add the latest score to the end of the file.
        try {

            PrintWriter writer = new PrintWriter("tread_scores.txt", "UTF-8");
            if (in == null) {
                writer.println("Tread scores");
            }
            writer.print(str);
            writer.println(TreadHandler.score + " " + namee);
            writer.close();
            System.out.println("Outtputed file");

        } catch (FileNotFoundException e){
            System.out.println("Failed to output score - file was not found");
        } catch (UnsupportedEncodingException e2){
            System.out.println("Encoding exception");
        }
    }

    //returns map of the scores sorted in highest to lowests
    public static TreeMap<String, Integer> sortScores(){
        openScoresFile();

        //initialization of variables
        HashMap<String, Integer> scoresMap = new HashMap<>(); //will be used to help with iteration
        CompareMap compareMap = new CompareMap(scoresMap);
        TreeMap<String, Integer> sortedMap = new TreeMap<String, Integer>(compareMap);
        String[] line = {""};
        int score;
        String name = "";


        try {

            //reads first line to skip it since it says "Tread scores"
            in.readLine();

            //iterates through each line of the text file
            while (in.ready()){
                name = "";
                line = in.readLine().split(" ");
                score = Integer.parseInt(line[0]);

                //everything that is after the score is treated as the name, so spaces are allowed
                for (int i = 1; i < line.length; i++){
                    name += line[i];
                    if (i < line.length-1){
                        name += " ";
                    }
                }
                Integer temp;

                //if there are two names in the text file, only the one with the highest score is used in the scores map
                if (scoresMap.containsKey(name)){

                    temp = scoresMap.get(name);

                    if (temp < score){
                        scoresMap.remove(name);
                        scoresMap.put(name, score);
                    }
                } else {
                    scoresMap.put(name, score);
                }
            }

            //map gets sorted when it is put into the sorted tree map
            sortedMap.putAll(scoresMap);

        } catch (IOException e){
            System.out.println("Couldn't sort scores");
        }

        //scores are output to a file where the highest scores are displayed first
        try {

            PrintWriter writer = new PrintWriter("tread_high_scores.txt", "UTF-8");
            if (in == null) {
                writer.println("Tread high scores");
            }

            Iterator<String> it = sortedMap.keySet().iterator();
            int mapScore;
            String mapName;
            int i = 1;
            while (it.hasNext()){
                mapName = it.next();
                mapScore = scoresMap.get(mapName);
                writer.println(i + ") " + mapScore + " " + mapName);
                i++;
            }
            writer.close();
            System.out.println("Outputted high scores file");

        } catch (FileNotFoundException e){
            System.out.println("Failed to output high scores");
        } catch (UnsupportedEncodingException e2){
            System.out.println("Failed");
        }

        return sortedMap;

    }
}
