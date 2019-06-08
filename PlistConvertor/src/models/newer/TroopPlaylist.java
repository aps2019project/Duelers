package models.newer;

import java.util.ArrayList;

public class TroopPlaylist {
    public int frameWidth;
    public int frameHeight;
    int frameDuration = 100;
    double extraX;
    double extraY;
    public ArrayList<Frame> attack = new ArrayList<>();
    public ArrayList<Frame> breathing = new ArrayList<>();
    public ArrayList<Frame> death = new ArrayList<>();
    public ArrayList<Frame> hit = new ArrayList<>();
    public ArrayList<Frame> idle = new ArrayList<>();
    public ArrayList<Frame> run = new ArrayList<>();
}
