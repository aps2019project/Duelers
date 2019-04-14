package cardMaker;

import com.google.gson.Gson;
import models.card.Card;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CardReader {

    public static void main(String[] args) {
        Gson gson = new Gson();
        String fileName = CardMaker.scanner.nextLine();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));

            Card loadedCard = gson.fromJson(bufferedReader, Card.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
