package plistConverter.converters;

import plistConverter.models.newer.Frame;
import plistConverter.models.newer.Playlist;
import plistConverter.models.old.Plist;
import plistConverter.models.old.PlistFrame;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlistToIconPlaylistConverter implements Converter<Playlist> {
    private static final Pattern framePattern = Pattern.compile("(\\d+),(\\d+)");

    @Override
    public Playlist convert(Plist plist) {
        Playlist playlist = new Playlist();
        ArrayList<Frame> active = new ArrayList<>();
        ArrayList<Frame> inactive = new ArrayList<>();
        playlist.lists.put("active", active);
        playlist.lists.put("inactive", inactive);

        for (Map.Entry<String, PlistFrame> entry : plist.frames.entrySet()) {
            PlistFrame plistFrame = entry.getValue();

            Matcher matcher = framePattern.matcher(plistFrame.frame);
            matcher.find();
            Frame frame = new Frame(
                    Integer.parseInt(matcher.group(1)),
                    Integer.parseInt(matcher.group(2))
            );
            if (entry.getKey().contains("active")) {
                active.add(frame);
            } else {
                inactive.add(frame);
            }

            matcher.find();
            playlist.frameWidth = Integer.parseInt(matcher.group(1));
            playlist.frameHeight = Integer.parseInt(matcher.group(2));
        }
        return playlist;
    }

    @Override
    public String getPath() {
        return "../resources/icons";
    }

    @Override
    public String getInitialFolder() {
        return "/initialJsons";
    }

    @Override
    public String getDestinationFolder() {
        return "/finalJsons";
    }
}
