package models.newer;

import java.util.ArrayList;

public class IconPlaylist {
    public int frameWidth;
    public int frameHeight;
    int frameDuration = 100;
    double extraX;
    double extraY;
    public ArrayList<Frame> inactive = new ArrayList<>();
    public ArrayList<Frame> active = new ArrayList<>();
}
