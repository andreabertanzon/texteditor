package abcode;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Data{

    public ArrayList<String> text = new ArrayList<>();



    public void saveData(String textToSave, File fileName) throws IOException{

        try(FileWriter fileWriter = new FileWriter(fileName)){


            fileWriter.write(textToSave);

        }

    }

    public void loadData(File file) throws IOException {

        try(Scanner scanner = new Scanner(new BufferedReader(new FileReader(file)))){

            while(scanner.hasNext()){
                scanner.useDelimiter(" "); //to be sure I get the correct format when I load the file
                String word = scanner.next();
                text.add(word);
            }

        }

    }


}
