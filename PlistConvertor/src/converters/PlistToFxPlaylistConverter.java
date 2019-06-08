package converters;

import models.newer.Frame;
import models.newer.FxPlayList;
import models.old.Plist;
import models.old.PlistFrame;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlistToFxPlaylistConverter implements Converter<FxPlayList>{
    private final Pattern framePattern = Pattern.compile("(\\d+),(\\d+)");

    @Override
    public FxPlayList convert(Plist plist) {
        FxPlayList fxPlayList = new FxPlayList();
        for (Map.Entry<String, PlistFrame> entry : plist.frames.entrySet()) {
            PlistFrame plistFrame = entry.getValue();

            Matcher matcher = framePattern.matcher(plistFrame.frame);
            matcher.find();
            Frame frame = new Frame(
                    Integer.parseInt(matcher.group(1)),
                    Integer.parseInt(matcher.group(2))
            );

            fxPlayList.frames.add(frame);

            matcher.find();
            fxPlayList.frameWidth = Integer.parseInt(matcher.group(1));
            fxPlayList.frameHeight = Integer.parseInt(matcher.group(2));
        }
        return fxPlayList;
    }

    @Override
    public String getPath() {
        return "../resources/fx";
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
