package view.BattleView;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import models.comperessedData.CompressedGame;
import models.comperessedData.CompressedPlayer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileInputStream;

public class PlayerBox implements PropertyChangeListener {
    private final BattleScene battleScene;
    private final CompressedPlayer player1, player2;
    private final Group group;
    private final Group mpGroup;
    private ImageView player1Image;
    private ImageView player2Image;
    private ImageView comboImage;

    public PlayerBox(BattleScene battleScene, CompressedGame game) throws Exception {
        this.battleScene = battleScene;
        this.player1 = game.getPlayerOne();
        this.player2 = game.getPlayerTwo();
        group = new Group();
        addPhotos();
        mpGroup = new Group();
        group.getChildren().add(mpGroup);
        updateMP(7);
        addComboButton();
        game.addPropertyChangeListener(this);
    }

    private void addPhotos() throws Exception {
        player1Image = new ImageView(new Image(new FileInputStream("resources/photo/general_portrait_image_hex_f5@2x.png")));
        player2Image = new ImageView(new Image(new FileInputStream("resources/photo/general_portrait_image_hex_f6-third@2x.png")));
        player1Image.setFitWidth(player1Image.getImage().getWidth() * Constants.SCALE * 0.3);
        player1Image.setFitHeight(player1Image.getImage().getHeight() * Constants.SCALE * 0.3);
        player2Image.setFitWidth(player2Image.getImage().getWidth() * Constants.SCALE * 0.3);
        player2Image.setFitHeight(player2Image.getImage().getHeight() * Constants.SCALE * 0.3);
        player1Image.setX(Constants.SCREEN_WIDTH * 0.01);
        player1Image.setY(-Constants.SCREEN_HEIGHT * 0.02);
        player2Image.setX(Constants.SCREEN_WIDTH * 0.85);
        player2Image.setY(-Constants.SCREEN_HEIGHT * 0.02);
        group.getChildren().addAll(player1Image, player2Image);
        //TODO:NAMES
    }

    public void refreshComboAndSpell() {
        try {
            comboImage.setImage(new Image(new FileInputStream("resources/ui/ranked_chevron_empty@2x.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addComboButton() throws Exception {
        comboImage = new ImageView(new Image(new FileInputStream("resources/ui/ranked_chevron_empty@2x.png")));
        comboImage.setFitWidth(comboImage.getImage().getWidth() * Constants.SCALE * 0.3);
        comboImage.setFitHeight(comboImage.getImage().getHeight() * Constants.SCALE * 0.3);
        comboImage.setY(Constants.SCALE * (260));
        if (battleScene.getMyPlayerNumber() == 1)
            comboImage.setX(Constants.SCALE * (230));
        else
            comboImage.setX(Constants.SCREEN_WIDTH - Constants.SCALE * (230) - comboImage.getFitWidth());
        group.getChildren().add(comboImage);
        comboImage.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                hoverComboButton();
            }
        });
        comboImage.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                exitComboButton();
            }
        });
        comboImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                clickComboButton();
            }
        });
    }

    private void exitComboButton() {
        try {
            if (battleScene.getMapBox().isComboSelected())
                comboImage.setImage(new Image(new FileInputStream("resources/ui/ranked_chevron_full@2x.png")));
            else
                comboImage.setImage(new Image(new FileInputStream("resources/ui/ranked_chevron_empty@2x.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(battleScene.getMapBox().isComboSelected());
    }

    private void hoverComboButton() {
        try {
            if (battleScene.isMyTurn() && battleScene.getMapBox().getSelectedTroop() != null &&
                    battleScene.getMapBox().getSelectedTroop().getCard().isHasCombo())
                comboImage.setImage(new Image(new FileInputStream("resources/ui/ranked_chevron_full@2x.png")));
            else
                comboImage.setImage(new Image(new FileInputStream("resources/ui/ranked_chevron_empty@2x.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clickComboButton() {
        try {
            if (battleScene.isMyTurn() && battleScene.getMapBox().getSelectedTroop() != null &&
                    battleScene.getMapBox().getSelectedTroop().getCard().isHasCombo()) {
                if (battleScene.getMapBox().isComboSelected()) {
                    battleScene.getMapBox().setComboSelected(false);
                    comboImage.setImage(new Image(new FileInputStream("resources/ui/ranked_chevron_empty@2x.png")));
                } else {
                    battleScene.getMapBox().setComboSelected(true);
                    comboImage.setImage(new Image(new FileInputStream("resources/ui/ranked_chevron_full@2x.png")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateMP(int maxMP) {
        mpGroup.getChildren().clear();
        for (int i = 1; i <= player1.getCurrentMP(); i++) {
            try {
                ImageView imageView = new ImageView(new Image(new FileInputStream("resources/ui/icon_mana@2x.png")));
                imageView.setFitWidth(imageView.getImage().getWidth() * Constants.SCALE * 0.35);
                imageView.setFitHeight(imageView.getImage().getHeight() * Constants.SCALE * 0.35);
                imageView.setX(Constants.SCALE * (250 + i * 40));
                imageView.setY(Constants.SCALE * (150 - i * 2));
                group.getChildren().add(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (int i = player1.getCurrentMP() + 1; i <= maxMP; i++) {
            try {
                ImageView imageView = new ImageView(new Image(new FileInputStream("resources/ui/icon_mana_inactive@2x.png")));
                imageView.setFitWidth(imageView.getImage().getWidth() * Constants.SCALE * 0.35);
                imageView.setFitHeight(imageView.getImage().getHeight() * Constants.SCALE * 0.35);
                imageView.setX(Constants.SCALE * (250 + i * 40));
                imageView.setY(Constants.SCALE * (150 - i * 2));
                group.getChildren().add(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (int i = 1; i <= player2.getCurrentMP(); i++) {
            try {
                ImageView imageView = new ImageView(new Image(new FileInputStream("resources/ui/icon_mana@2x.png")));
                imageView.setFitWidth(imageView.getImage().getWidth() * Constants.SCALE * 0.35);
                imageView.setFitHeight(imageView.getImage().getHeight() * Constants.SCALE * 0.35);
                imageView.setX(Constants.SCREEN_WIDTH - Constants.SCALE * (250 + i * 40) - imageView.getFitWidth());
                imageView.setY(Constants.SCALE * (150 - i * 2));
                group.getChildren().add(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (int i = player2.getCurrentMP() + 1; i <= maxMP; i++) {
            try {
                ImageView imageView = new ImageView(new Image(new FileInputStream("resources/ui/icon_mana_inactive@2x.png")));
                imageView.setFitWidth(imageView.getImage().getWidth() * Constants.SCALE * 0.35);
                imageView.setFitHeight(imageView.getImage().getHeight() * Constants.SCALE * 0.35);
                imageView.setX(Constants.SCREEN_WIDTH - Constants.SCALE * (250 + i * 40) - imageView.getFitWidth());
                imageView.setY(Constants.SCALE * (150 - i * 2));
                group.getChildren().add(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Group getGroup() {
        return group;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("mp1")) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    updateMP((int) evt.getNewValue());
                }
            });
        }
        if (evt.getPropertyName().equals("mp2")) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    updateMP((int) evt.getNewValue());
                }
            });
        }
        if (evt.getPropertyName().equals("turn")) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    if ((int) evt.getNewValue() % 2 == 1) {
                        player1Image.setOpacity(1);
                        player2Image.setOpacity(0.7);
                    } else {
                        player1Image.setOpacity(0.7);
                        player2Image.setOpacity(1);
                    }
                }
            });
        }
    }
}