package server.detaCenter;

public class DataCenter {
    private static DataCenter ourInstance = new DataCenter();

    public static DataCenter getInstance() {
        return ourInstance;
    }

    private DataCenter() {
    }
}
