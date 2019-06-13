package BattleView;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.comperessedData.CompressedPlayer;

import java.io.FileInputStream;

public class PlayerBox {
    private final Controller controller;
    private final CompressedPlayer player1, player2;
    private final Group group;
    private final Group mpGroup;

    public PlayerBox(Controller controller, CompressedPlayer player1, CompressedPlayer player2) {
        this.controller = controller;
        this.player1 = player1;
        this.player2 = player2;
        group = new Group();
        addPhotos();
        mpGroup = new Group();
        group.getChildren().add(mpGroup);
        updateMP(3);
    }

    private void addPhotos() {
        try {
            ImageView imageView1 = new ImageView(new Image(new FileInputStream("resources/photo/general_portrait_image_hex_f5@2x.png")));
            ImageView imageView2 = new ImageView(new Image(new FileInputStream("resources/photo/general_portrait_image_hex_f6-third@2x.png")));
            imageView1.setFitWidth(imageView1.getImage().getWidth() * Constants.SCALE * 0.3);
            imageView1.setFitHeight(imageView1.getImage().getHeight() * Constants.SCALE * 0.3);
            imageView2.setFitWidth(imageView2.getImage().getWidth() * Constants.SCALE * 0.3);
            imageView2.setFitHeight(imageView2.getImage().getHeight() * Constants.SCALE * 0.3);
            imageView1.setX(Constants.SCREEN_WIDTH * 0.01);
            imageView1.setY(-Constants.SCREEN_HEIGHT * 0.02);
            imageView2.setX(Constants.SCREEN_WIDTH * 0.85);
            imageView2.setY(-Constants.SCREEN_HEIGHT * 0.02);
            group.getChildren().addAll(imageView1, imageView2);
            //TODO:NAMES
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateMP(int maxMP) {
        mpGroup.getChildren().clear();
        for (int i = 1; i <= player1.getCurrentMP(); i++) {
            try {
                ImageView imageView = new ImageView(new Image(new FileInputStream("resources/ui/icon_mana@2x.png")));
                imageView.setFitWidth(imageView.getImage().getWidth() * Constants.SCALE * 0.3);
                imageView.setFitHeight(imageView.getImage().getHeight() * Constants.SCALE * 0.3);
                imageView.setX(Constants.SCALE * (250 + i * 40));
                imageView.setY(Constants.SCALE * (150 - i * 3));
                group.getChildren().add(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (int i = player1.getCurrentMP() + 1; i <= maxMP; i++) {
            try {
                ImageView imageView = new ImageView(new Image(new FileInputStream("resources/ui/icon_mana_inactive@2x.png")));
                imageView.setFitWidth(imageView.getImage().getWidth() * Constants.SCALE * 0.3);
                imageView.setFitHeight(imageView.getImage().getHeight() * Constants.SCALE * 0.3);
                imageView.setX(Constants.SCALE * (250 + i * 40));
                imageView.setY(Constants.SCALE * (150 - i * 3));
                group.getChildren().add(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (int i = 1; i <= player2.getCurrentMP(); i++) {
            try {
                ImageView imageView = new ImageView(new Image(new FileInputStream("resources/ui/icon_mana@2x.png")));
                imageView.setFitWidth(imageView.getImage().getWidth() * Constants.SCALE * 0.3);
                imageView.setFitHeight(imageView.getImage().getHeight() * Constants.SCALE * 0.3);
                imageView.setX(Constants.SCREEN_WIDTH - Constants.SCALE * (250 + i * 40) - imageView.getFitWidth());
                imageView.setY(Constants.SCALE * (150 - i * 3));
                group.getChildren().add(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (int i = player2.getCurrentMP() + 1; i <= maxMP; i++) {
            try {
                ImageView imageView = new ImageView(new Image(new FileInputStream("resources/ui/icon_mana_inactive@2x.png")));
                imageView.setFitWidth(imageView.getImage().getWidth() * Constants.SCALE * 0.3);
                imageView.setFitHeight(imageView.getImage().getHeight() * Constants.SCALE * 0.3);
                imageView.setX(Constants.SCREEN_WIDTH - Constants.SCALE * (250 + i * 40) - imageView.getFitWidth());
                imageView.setY(Constants.SCALE * (150 - i * 3));
                group.getChildren().add(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Group getGroup() {
        return group;
    }
}
