package controller;

import com.sun.media.jfxmedia.MediaException;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SoundEffectPlayer {
    private static SoundEffectPlayer SEP = new SoundEffectPlayer();
    private static final Map<SoundName, Media> mediaFiles = new HashMap<>();
    private static final String directory = "resources/sfx/";
    private static final String format = ".m4a";

    static {
        Arrays.stream(SoundName.values()).forEach(soundName ->
                mediaFiles.put(soundName, new Media(new File(soundName.path).toURI().toString()))
        );
    }

    public enum SoundName {
        error("sfx_ui_error"),
        hover("sfx_ui_menu_hover"),
        open_dialog("sfx_ui_panel_swoosh_enter"),
        close_dialog("sfx_ui_panel_swoosh_exit"),
        click("sfx_ui_select"),
        enter_page("sfx_ui_modalwindow_swoosh_enter"),
        exit_page("sfx_ui_modalwindow_swoosh_exit"),
        your_turn("sfx_ui_yourturn"),
        ;

        private final String path;

        SoundName(String name) {
            this.path = directory + name + format;
        }
    }

    public static SoundEffectPlayer getInstance() {
        return SEP;
    }

    private SoundEffectPlayer() {
    }

    public void playSound(SoundName soundName) {
        try {
            Media media = mediaFiles.get(soundName);
            System.out.println("currentMedia:" + media.getSource());
            new MediaPlayer(media).play();
        } catch (MediaException ignored) {
        }
    }
}