package converters;

import models.newer.Frame;
import models.newer.TroopPlaylist;
import models.old.Plist;
import models.old.PlistFrame;

import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlistToTroopPlaylistConverter implements Converter<TroopPlaylist> {
    private static final Pattern framePattern = Pattern.compile("(\\d+),(\\d+)");

    public TroopPlaylist convert(Plist plist) {
        TroopPlaylist troopPlaylist = new TroopPlaylist();
        for (Entry<String, PlistFrame> entry : plist.frames.entrySet()) {
            PlistFrame plistFrame = entry.getValue();

            Matcher matcher = framePattern.matcher(plistFrame.frame);
            matcher.find();
            Frame frame = new Frame(
                    Integer.parseInt(matcher.group(1)),
                    Integer.parseInt(matcher.group(2))
            );
            if (entry.getKey().contains("attack")) {
                troopPlaylist.attack.add(frame);
            } else if (entry.getKey().contains("breathing")) {
                troopPlaylist.breathing.add(frame);
            } else if (entry.getKey().contains("death")) {
                troopPlaylist.death.add(frame);
            } else if (entry.getKey().contains("hit")) {
                troopPlaylist.hit.add(frame);
            } else if (entry.getKey().contains("idle")) {
                troopPlaylist.idle.add(frame);
            } else if (entry.getKey().contains("run")) {
                troopPlaylist.run.add(frame);
            }

            matcher.find();
            troopPlaylist.frameWidth = Integer.parseInt(matcher.group(1));
            troopPlaylist.frameHeight = Integer.parseInt(matcher.group(2));
        }
        return troopPlaylist;
    }

    @Override
    public String getPath() {
        return "../resources/units";
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
