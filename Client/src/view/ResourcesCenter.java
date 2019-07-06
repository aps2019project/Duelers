package view;

import com.google.gson.Gson;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import models.gui.ImageLoader;

import java.io.*;
import java.util.HashMap;

public class ResourcesCenter {
    private final static ResourcesCenter ourInstance = new ResourcesCenter();
    private HashMap<String, Image> imageHashMap = new HashMap<>();
    private HashMap<String, PlayList> playListHashMap = new HashMap<>();
    private HashMap<String, Media> mediaHashMap = new HashMap<>();

    private static final String PATH = "resources";

    private ResourcesCenter() {
    }

    private static void readData() throws IOException {
        File file = new File(PATH);
        readFile(file);
    }

    private static void readFile(File file) throws IOException {
        if (file.getName().contains("troop"))
            return;
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files == null)
                return;
            for (File file1 :
                    files) {
                readFile(file1);
            }
        } else {
            if (file.getName().contains(".plist.json")) {
                readPlayList(file);
            }
            if (file.getName().contains(".png")) {
                Image image = ImageLoader.load(file.getPath());
                ourInstance.imageHashMap.put(file.getPath(), image);
            }
            if (file.getName().contains(".m4a")) {
//                Media media = new Media(file.getPath());
//                ourInstance.mediaHashMap.put(file.getPath(), media);
            }
        }
    }

    private static void readPlayList(File file) throws IOException {
        PlayList playlist = new Gson().fromJson(new FileReader(file), PlayList.class);
        ourInstance.playListHashMap.put(file.getPath(), playlist);
    }

    public static void main(String[] args) {
        try {
            System.out.println(Runtime.getRuntime().totalMemory()/1000000);
            readData();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            System.out.println(Runtime.getRuntime().totalMemory()/1000000);
            System.out.println("ho");

        }
        System.out.println("x");
    }

    public static ResourcesCenter getInstance() {
        return ourInstance;
    }

}
