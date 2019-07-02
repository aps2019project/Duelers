package view.BattleView;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import models.card.CardType;
import models.comperessedData.CompressedGame;
import models.comperessedData.CompressedPlayer;
import models.comperessedData.CompressedTroop;

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
    private ImageView comboButton;
    private ImageView spellButton;
    private Image comboNotSelectedImage = new Image(new FileInputStream("resources/ui/ranked_chevron_empty@2x.png"));
    private Image comboSelectedImage = new Image(new FileInputStream("resources/ui/ranked_chevron_full@2x.png"));
    private Image spellSelectedImage = new Image(new FileInputStream("resources/ui/quests_glow@2x.png"));
    private Image spellNotSelectedImage = new Image(new FileInputStream("resources/ui/quests@2x.png"));
    private Image manaImage = new Image(new FileInputStream("resources/ui/icon_mana@2x.png"));
    private Image inActiveManaImage = new Image(new FileInputStream("resources/ui/icon_mana_inactive@2x.png"));

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
        addSpellButton();
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

    void refreshComboAndSpell() {
        try {
            comboButton.setImage(comboNotSelectedImage);
            spellButton.setImage(spellNotSelectedImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addSpellButton() throws Exception {
        spellButton = new ImageView(new Image(new FileInputStream("resources/ui/quests@2x.png")));
        spellButton.setFitWidth(spellButton.getImage().getWidth() * Constants.SCALE * 0.75);
        spellButton.setFitHeight(spellButton.getImage().getHeight() * Constants.SCALE * 0.75);
        spellButton.setY(Constants.SCALE * (325));
        if (battleScene.getMyPlayerNumber() == 1)
            spellButton.setX(Constants.SCALE * (180));
        else
            spellButton.setX(Constants.SCREEN_WIDTH - Constants.SCALE * (180) - spellButton.getFitWidth());
        group.getChildren().add(spellButton);
        spellButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                hoverSpellButton();
            }
        });
        spellButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                exitSpellButton();
            }
        });
        spellButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                clickSpellButton();
            }
        });
    }

    private void exitSpellButton() {
        try {
            if (battleScene.getMapBox().isSpellSelected())
                spellButton.setImage(spellSelectedImage);
            else
                spellButton.setImage(spellNotSelectedImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hoverSpellButton() {
        try {
            CompressedTroop troop = battleScene.getMapBox().getSelectedTroop();
            if (battleScene.isMyTurn() && troop != null && troop.getCard().getType() == CardType.HERO
                    && troop.getCard().getSpell() != null &&
                    troop.getCard().getSpell().getCoolDown() + troop.getCard().getSpell().getLastTurnUsed() <=
                            battleScene.getGame().getTurnNumber())
                spellButton.setImage(spellSelectedImage);
            else
                spellButton.setImage(spellNotSelectedImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clickSpellButton() {
        try {
            CompressedTroop troop = battleScene.getMapBox().getSelectedTroop();
            if (battleScene.isMyTurn() && troop != null && troop.getCard().getType() == CardType.HERO
                    && troop.getCard().getSpell() != null &&
                    troop.getCard().getSpell().getCoolDown() + troop.getCard().getSpell().getLastTurnUsed() <=
                            battleScene.getGame().getTurnNumber()) {
                if (battleScene.getMapBox().isSpellSelected()) {
                    battleScene.getMapBox().resetSelection();
                    spellButton.setImage(spellNotSelectedImage);
                } else {
                    battleScene.getMapBox().setSpellSelected();
                    spellButton.setImage(spellSelectedImage);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addComboButton() throws Exception {
        comboButton = new ImageView(comboNotSelectedImage);
        comboButton.setFitWidth(comboButton.getImage().getWidth() * Constants.SCALE * 0.3);
        comboButton.setFitHeight(comboButton.getImage().getHeight() * Constants.SCALE * 0.3);
        comboButton.setY(Constants.SCALE * (260));
        if (battleScene.getMyPlayerNumber() == 1)
            comboButton.setX(Constants.SCALE * (230));
        else
            comboButton.setX(Constants.SCREEN_WIDTH - Constants.SCALE * (230) - comboButton.getFitWidth());
        group.getChildren().add(comboButton);
        comboButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                hoverComboButton();
            }
        });
        comboButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                exitComboButton();
            }
        });
        comboButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                clickComboButton();
            }
        });
    }

    private void exitComboButton() {
        try {
            if (battleScene.getMapBox().isComboSelected())
                comboButton.setImage(comboSelectedImage);
            else
                comboButton.setImage(comboNotSelectedImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hoverComboButton() {
        try {
            if (battleScene.isMyTurn() && battleScene.getMapBox().getSelectedTroop() != null &&
                    battleScene.getMapBox().getSelectedTroop().getCard().isHasCombo())
                comboButton.setImage(comboSelectedImage);
            else
                comboButton.setImage(comboNotSelectedImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clickComboButton() {
        try {
            if (battleScene.isMyTurn() && battleScene.getMapBox().getSelectedTroop() != null &&
                    battleScene.getMapBox().getSelectedTroop().getCard().isHasCombo()) {
                if (battleScene.getMapBox().isComboSelected()) {
                    battleScene.getMapBox().resetSelection();
                    comboButton.setImage(comboNotSelectedImage);
                } else {
                    battleScene.getMapBox().setComboSelected();
                    comboButton.setImage(comboSelectedImage);
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
                ImageView imageView = new ImageView(manaImage);
                imageView.setFitWidth(imageView.getImage().getWidth() * Constants.SCALE * 0.35);
                imageView.setFitHeight(imageView.getImage().getHeight() * Constants.SCALE * 0.35);
                imageView.setX(Constants.SCALE * (250 + i * 40));
                imageView.setY(Constants.SCALE * (150 - i * 2));
                mpGroup.getChildren().add(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (int i = player1.getCurrentMP() + 1; i <= maxMP; i++) {
            try {
                ImageView imageView = new ImageView(inActiveManaImage);
                imageView.setFitWidth(imageView.getImage().getWidth() * Constants.SCALE * 0.35);
                imageView.setFitHeight(imageView.getImage().getHeight() * Constants.SCALE * 0.35);
                imageView.setX(Constants.SCALE * (250 + i * 40));
                imageView.setY(Constants.SCALE * (150 - i * 2));
                mpGroup.getChildren().add(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (int i = 1; i <= player2.getCurrentMP(); i++) {
            try {
                ImageView imageView = new ImageView(manaImage);
                imageView.setFitWidth(imageView.getImage().getWidth() * Constants.SCALE * 0.35);
                imageView.setFitHeight(imageView.getImage().getHeight() * Constants.SCALE * 0.35);
                imageView.setX(Constants.SCREEN_WIDTH - Constants.SCALE * (250 + i * 40) - imageView.getFitWidth());
                imageView.setY(Constants.SCALE * (150 - i * 2));
                mpGroup.getChildren().add(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (int i = player2.getCurrentMP() + 1; i <= maxMP; i++) {
            try {
                ImageView imageView = new ImageView(inActiveManaImage);
                imageView.setFitWidth(imageView.getImage().getWidth() * Constants.SCALE * 0.35);
                imageView.setFitHeight(imageView.getImage().getHeight() * Constants.SCALE * 0.35);
                imageView.setX(Constants.SCREEN_WIDTH - Constants.SCALE * (250 + i * 40) - imageView.getFitWidth());
                imageView.setY(Constants.SCALE * (150 - i * 2));
                mpGroup.getChildren().add(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    Group getGroup() {
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
                    battleScene.getHandBox().resetSelection();
                    battleScene.getMapBox().resetSelection();
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