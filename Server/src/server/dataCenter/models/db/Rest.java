package server.dataCenter.models.db;

import io.joshworks.restclient.http.HttpResponse;
import io.joshworks.restclient.http.Unirest;
import server.clientPortal.models.JsonConverter;
import server.dataCenter.DataBase;
import server.dataCenter.models.account.Collection;
import server.dataCenter.models.card.Card;
import server.gameCenter.models.game.Story;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Rest implements DataBase {
    private static String[] maps = {"originalCards", "customCards", "collectibleItems", "stories", "originalFlag"};
    final String baseAddress = "http://127.0.0.1:8080/";

    public static void main(String[] args) {
        Rest rest = new Rest();
        System.out.println(rest.getAllValues(maps[0]));

    }

    Rest() {
        for (String s : maps) {
            createMap(s);
        }
    }

    private int put(String name, String key, String value) {
        final String path = "put";
        HttpResponse<String> response = null;
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("name", name);
        parameters.put("key", key);
        parameters.put("value", value);
        try {
            response = Unirest.post(baseAddress + path)
                    .fields(parameters)
                    .asString();
            return response.getStatus();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int createMap(String name) {
        final String path = "init_DB";
        HttpResponse<String> response = null;
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("name", name);
        try {
            response = Unirest.post(baseAddress + path)
                    .fields(parameters)
                    .asString();
            return response.getStatus();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private List getAllKeys(String name) {
        final String path = "get_all_keys";
        return getList(name, path);
    }

    private ArrayList getList(String name, String path) {
        HttpResponse<String> response = null;
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("name", name);
        try {
            response = Unirest.post(baseAddress + path)
                    .fields(parameters)
                    .asString();
            if (response.getStatus() == 200)
                return JsonConverter.fromJson(response.getBody(), ArrayList.class);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }

    private List getAllValues(String name) {
        final String path = "get_all_values";
        return getList(name, path);
    }

    private String getFromDataBase(String name, String key) {
        final String path = "get";
        HttpResponse<String> response = null;
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("name", name);
        parameters.put("key", key);
        try {
            response = Unirest.post(baseAddress + path)
                    .fields(parameters)
                    .asString();
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private int delete(String name, String key) {
        final String path = "del_from_DB";
        HttpResponse<String> response = null;
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("name", name);
        parameters.put("key", key);
        try {
            response = Unirest.post(baseAddress + path)
                    .fields(parameters)
                    .asString();
            return response.getStatus();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    @Override
    public Card getCard(String cardName) {
        String json = getFromDataBase(maps[0], cardName);
        return JsonConverter.fromJson(json, Card.class);
    }

    @Override
    public Collection getOriginalCards() {
        List jsons =  getAllValues(maps[0]);
        Collection collection = new Collection();
        for (Object o :jsons) {
            collection.addCard(JsonConverter.fromJson((String) o, Card.class));
        }
        return collection;
    }

    @Override
    public List<Story> getStories() {
        List list = getAllKeys(maps[3]);
        ArrayList<Story> stories= new ArrayList<>();
        for (Object o
                :list) {
            stories.add(JsonConverter.fromJson((String)o,Story.class));
        }
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
