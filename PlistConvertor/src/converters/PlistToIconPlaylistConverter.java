package converters;

import models.newer.Frame;
import models.newer.IconPlaylist;
import models.old.Plist;
import models.old.PlistFrame;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlistToIconPlaylistConverter implements Converter<IconPlaylist> {
    private static final Pattern framePattern = Pattern.compile("(\\d+),(\\d+)");

    @Override
    public IconPlaylist convert(Plist plist) {
        IconPlaylist iconPlaylist = new IconPlaylist();
        for (Map.Entry<String, PlistFrame> entry : plist.frames.entrySet()) {
            PlistFrame plistFrame = entry.getValue();

            Matcher matcher = framePattern.matcher(plistFrame.frame);
            matcher.find();
            Frame frame = new Frame(
                    Integer.parseInt(matcher.group(1)),
                    Integer.parseInt(matcher.group(2))
            );
            if (entry.getKey().contains("active")) {
                iconPlaylist.active.add(frame);
            } else {
                iconPlaylist.inactive.add(frame);
            }

            matcher.find();
            iconPlaylist.frameWidth = Integer.parseInt(matcher.group(1));
            iconPlaylist.frameHeight = Integer.parseInt(matcher.group(2));
        }
        return iconPlaylist;
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
