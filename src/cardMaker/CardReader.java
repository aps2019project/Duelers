package cardMaker;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import server.models.card.Card;

import java.io.*;

public class CardReader {

    public static void main(String[] args) {
        String directory = "jsonData/itemCards/usable";
        File dir = new File(directory);
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                String name = file.getName();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(directory + "/" + name));

                    Card loadedCard = new Gson().fromJson(bufferedReader, Card.class);

                    writeAJsonFile(loadedCard, directory + "/" + name);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void writeAJsonFile(Card card, String address) {
        String json = new GsonBuilder().setPrettyPrinting().create().toJson(card);
        System.out.println(json);

        try {
            FileWriter writer = new FileWriter(address);
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
