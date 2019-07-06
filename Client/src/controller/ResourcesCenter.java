package controller;

import com.google.gson.Gson;
import view.PlayList;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;

public class ResourcesCenter {
    private final static ResourcesCenter ourInstance = new ResourcesCenter();
    private HashMap<String, byte[]> imageHashMap = new HashMap<>();
    private HashMap<String, PlayList> playListHashMap = new HashMap<>();
    private HashMap<String, byte[]> stringMediaHashMap = new HashMap<String, byte[]>();



    private static final String PATH = "resources";

    private ResourcesCenter() {
    }

    private static void readData() throws IOException {
        File file = new File(PATH);
        readFile(file);
    }

    private static void readFile(File file) throws IOException {
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
                byte[] x = Files.readAllBytes(file.toPath());
                ourInstance.imageHashMap.put(file.getPath(), x);
            }
            if (file.getName().contains(".m4a")) {
                byte[] x = Files.readAllBytes(file.toPath());
                ourInstance.stringMediaHashMap.put(file.getPath(), x);
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
            System.out.println("ho");
        }
        System.out.println(Runtime.getRuntime().totalMemory()/1000000);

        System.out.println("x");
    }

    public static ResourcesCenter getInstance() {
        return ourInstance;
    }

}
