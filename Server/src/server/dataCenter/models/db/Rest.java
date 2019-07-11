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
    //    private static String[] maps = {"originalCards", "customCards", "collectibleItems", "stories", "originalFlag"};
    private static enum maps {
        ORINGINAL_CARDS("originalCards"),
        CUSTOM_CARDS("customCards"),
        COLLECTIBLE_ITEMS("collectibleItems"),
        STORIES("stories"),
        ORIGINAL_FLAG("originalFlag");

        maps(String s) {
            path = s;
        }

        String path;
    }

    final String baseAddress = "http://127.0.0.1:8080/";

    public static void main(String[] args) {
        Rest rest = new Rest();
        System.out.println(rest.getAllValues(maps.ORINGINAL_CARDS.path));

    }

    Rest() {
        for (maps s : maps.values()) {
            createMap(s.path);
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
        String json = getFromDataBase(maps.ORINGINAL_CARDS.path, cardName);
        return JsonConverter.fromJson(json, Card.class);
    }

    @Override
    public Collection getOriginalCards() {
        List jsons = getAllValues(maps.ORINGINAL_CARDS.path);
        Collection collection = new Collection();
        for (Object o : jsons) {
            collection.addCard(JsonConverter.fromJson((String) o, Card.class));
        }
        return collection;
    }

    @Override
    public List<Story> getStories() {
        return getStories(getAllKeys(maps.COLLECTIBLE_ITEMS.path), Story.class);
    }

    private <T> ArrayList<T> getStories(List list, Class<T> classOfT) {
        ArrayList<T> arrayList = new ArrayList<>();
        for (Object o
                : list) {
            arrayList.add(JsonConverter.fromJson((String) o, classOfT));
        }
        return arrayList;
    }


    @Override
    public List<Card> getCollectibleItems() {
        return getStories(getAllKeys(maps.CUSTOM_CARDS.path), Card.class);
    }

    @Override
    public Collection getNewCustomCards() {
        List jsons = getAllValues(maps.CUSTOM_CARDS.path);
        Collection collection = new Collection();
        for (Object o : jsons) {
            collection.addCard(JsonConverter.fromJson((String) o, Card.class));
        }
        return collection;
    }

    @Override
    public Card getOriginalFlag() {
        return JsonConverter.fromJson(getFromDataBase(maps.ORIGINAL_FLAG.path, maps.ORIGINAL_FLAG.path), Card.class);
    }

    @Override
    public void addNewCustomCards(Card card) {
        put(maps.CUSTOM_CARDS.path,card.getName(),JsonConverter.toJson(card));
    }

    @Override
    public void removeCustomCards(Card card) {
        delete(maps.CUSTOM_CARDS.path,card.getName());
    }

    @Override
    public void addOriginalCard(Card card) {
        put(maps.ORINGINAL_CARDS.path,card.getName(),JsonConverter.toJson(card));
    }

    @Override
    public void addNewCollectible(Card card) {
        put(maps.COLLECTIBLE_ITEMS.path,card.getName(),JsonConverter.toJson(card));

    }

    @Override
    public void setOriginalFlag(Card card) {
        put(maps.ORIGINAL_FLAG.path,maps.ORIGINAL_FLAG.path,JsonConverter.toJson(card));
    }

    @Override
    public void addStory(Story story) {
        put(maps.STORIES.path,JsonConverter.toJson(story),"");

    }
}
