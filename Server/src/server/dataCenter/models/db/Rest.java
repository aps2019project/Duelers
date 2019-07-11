package server.dataCenter.models.db;

import io.joshworks.restclient.http.HttpResponse;
import io.joshworks.restclient.http.Unirest;
import server.clientPortal.models.JsonConverter;
import server.dataCenter.DataBase;
import server.dataCenter.models.account.Collection;
import server.dataCenter.models.card.Card;
import server.gameCenter.models.game.Story;

import java.util.HashMap;
import java.util.List;

public class Rest implements DataBase {
    private String[] maps = {"originalCards", "customCards", "collectibleItems", "stories", "originalFlag"};
    final String baseAddress = "http://127.0.0.1:8080/";

    public static void main(String[] args) {
        Rest rest = new Rest();
    }

    Rest() {
        for (String s : maps) {
            createMap(s);
        }
    }

    private void createMap(String name) {
        final String path = "init_DB";
        HttpResponse<String> response = null;
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("name", name);
        try {
            response = Unirest.post(baseAddress + path)
                    .fields(parameters)
                    .asString();
            if (response.getStatus()==200)
                System.out.println("init db: "+name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List getAllKeys(String name){
        final String path = "get_all_keys";
        HttpResponse<String> response = null;
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("name", name);
        try {
            response = Unirest.post(baseAddress + path)
                    .fields(parameters)
                    .asString();
            if (response.getStatus()==200)
                return JsonConverter.fromJson(response.getBody(),List.class);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Card getCard(String cardName) {
        return null;
    }

    @Override
    public Collection getOriginalCards() {
        return null;
    }

    @Override
    public List<Story> getStories() {
        return null;
    }

    @Override
    public List<Card> getCollectibleItems() {
        return null;
    }

    @Override
    public Collection getNewCustomCards() {
        return null;
    }

    @Override
    public Card getOriginalFlag() {
        return null;
    }

    @Override
    public void addNewCustomCards(Card card) {

    }

    @Override
    public void removeCustomCards(Card card) {

    }

    @Override
    public void addOriginalCard(Card card) {

    }

    @Override
    public void addNewCollectible(Card card) {

    }

    @Override
    public void setOriginalFlag(Card loadFile) {

    }

    @Override
    public void addStory(Story story) {

    }
}
