package view.BattleView;

import controller.GameController;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import models.card.CardType;
import models.comperessedData.CompressedGame;
import models.comperessedData.CompressedPlayer;
import models.comperessedData.CompressedTroop;
import models.gui.DefaultText;
import models.gui.ImageLoader;
import models.gui.NormalField;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileInputStream;

import static java.lang.Math.pow;
import static models.gui.UIConstants.DEFAULT_FONT;
import static view.BattleView.Constants.SCALE;

public class PlayerBox implements PropertyChangeListener {
    private static final Image comboNotSelectedImage = ImageLoader.load("resources/ui/ranked_chevron_empty@2x.png");
    private static final Image comboSelectedImage = ImageLoader.load("resources/ui/ranked_chevron_full@2x.png");
    private static final Image spellSelectedImage = ImageLoader.load("resources/ui/quests_glow@2x.png");
    private static final Image spellNotSelectedImage = ImageLoader.load("resources/ui/quests@2x.png");
    private static final Image manaImage = ImageLoader.load("resources/ui/icon_mana@2x.png");
    private static final Image inActiveManaImage = ImageLoader.load("resources/ui/icon_mana_inactive@2x.png");
    private static final Image player1Profile = ImageLoader.load("resources/photo/general_portrait_image_hex_f5@2x.png");
    private static final Image player2Profile = ImageLoader.load("resources/photo/general_portrait_image_hex_f6-third@2x.png");
    private static final Image chatImage = ImageLoader.load("resources/ui/chat_bubble.png");
    private static final double CHAT_BUBBLE_SIZE = 150 * SCALE;
    private final NormalField chatField = new NormalField("Type message and send");
    private final BattleScene battleScene;
    private final CompressedPlayer player1, player2;
    private final Group group;
    private final Group mpGroup;
    private MessageShow player1MessageShow;
    private MessageShow player2MessageShow;
    private ImageView player1Image;
    private ImageView player2Image;
    private ImageView comboButton;
    private ImageView spellButton;

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
        addChatField();
        makeMessageShows();
        game.addPropertyChangeListener(this);
    }

    private void addChatField() {
        double x, y;
        chatField.setMinWidth(CHAT_BUBBLE_SIZE);
        chatField.setMaxWidth(CHAT_BUBBLE_SIZE);
        if (battleScene.getMyPlayerNumber() == 1) {
            x = player1Image.getX() + (player1Image.getFitWidth() - chatField.getMinWidth()) / 2;
            y = player1Image.getY() + player1Image.getFitHeight();
        } else {
            x = player2Image.getX() + (player2Image.getFitWidth() - chatField.getMinWidth()) / 2;
            y = player2Image.getY() + player2Image.getFitHeight();
        }
        chatField.relocate(x, y);
        chatField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                GameController.getInstance().sendChat(chatField.getText());
                battleScene.showMyMessage(chatField.getText());
                chatField.clear();
            }
        });

        group.getChildren().add(chatField);
    }

    private void makeMessageShows() {
        player1MessageShow = new MessageShow(player1Image);
        player2MessageShow = new MessageShow(player2Image);
    }

    private void addPhotos() {
        player1Image = ImageLoader.makeImageView(player1Profile,
                player1Profile.getWidth() * SCALE * 0.3,
                player1Profile.getHeight() * SCALE * 0.3
        );
        player2Image = ImageLoader.makeImageView(player2Profile,
                player2Profile.getWidth() * SCALE * 0.3,
                player2Profile.getHeight() * SCALE * 0.3
        );
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
        spellButton.setFitWidth(spellButton.getImage().getWidth() * SCALE * 0.75);
        spellButton.setFitHeight(spellButton.getImage().getHeight() * SCALE * 0.75);
        spellButton.setY(SCALE * (325));
        if (battleScene.getMyPlayerNumber() == 1)
            spellButton.setX(SCALE * (180));
        else
            spellButton.setX(Constants.SCREEN_WIDTH - SCALE * (180) - spellButton.getFitWidth());
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
                    battleScene.getMapBox().updateMapColors();
                    spellButton.setImage(spellSelectedImage);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addComboButton() throws Exception {
        comboButton = new ImageView(comboNotSelectedImage);
        comboButton.setFitWidth(comboButton.getImage().getWidth() * SCALE * 0.3);
        comboButton.setFitHeight(comboButton.getImage().getHeight() * SCALE * 0.3);
        comboButton.setY(SCALE * (260));
        if (battleScene.getMyPlayerNumber() == 1)
            comboButton.setX(SCALE * (230));
        else
            comboButton.setX(Constants.SCREEN_WIDTH - SCALE * (230) - comboButton.getFitWidth());
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
                    battleScene.getMapBox().updateMapColors();
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
                imageView.setFitWidth(imageView.getImage().getWidth() * SCALE * 0.35);
                imageView.setFitHeight(imageView.getImage().getHeight() * SCALE * 0.35);
                imageView.setX(SCALE * (250 + i * 40));
                imageView.setY(SCALE * (150 - i * 2));
                mpGroup.getChildren().add(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (int i = player1.getCurrentMP() + 1; i <= maxMP; i++) {
            try {
                ImageView imageView = new ImageView(inActiveManaImage);
                imageView.setFitWidth(imageView.getImage().getWidth() * SCALE * 0.35);
                imageView.setFitHeight(imageView.getImage().getHeight() * SCALE * 0.35);
                imageView.setX(SCALE * (250 + i * 40));
                imageView.setY(SCALE * (150 - i * 2));
                mpGroup.getChildren().add(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (int i = 1; i <= player2.getCurrentMP(); i++) {
            try {
                ImageView imageView = new ImageView(manaImage);
                imageView.setFitWidth(imageView.getImage().getWidth() * SCALE * 0.35);
                imageView.setFitHeight(imageView.getImage().getHeight() * SCALE * 0.35);
                imageView.setX(Constants.SCREEN_WIDTH - SCALE * (250 + i * 40) - imageView.getFitWidth());
                imageView.setY(SCALE * (150 - i * 2));
                mpGroup.getChildren().add(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (int i = player2.getCurrentMP() + 1; i <= maxMP; i++) {
            try {
                ImageView imageView = new ImageView(inActiveManaImage);
                imageView.setFitWidth(imageView.getImage().getWidth() * SCALE * 0.35);
                imageView.setFitHeight(imageView.getImage().getHeight() * SCALE * 0.35);
                imageView.setX(Constants.SCREEN_WIDTH - SCALE * (250 + i * 40) - imageView.getFitWidth());
                imageView.setY(SCALE * (150 - i * 2));
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
            Platform.runLater(() -> updateMP((int) evt.getNewValue()));
        }
        if (evt.getPropertyName().equals("mp2")) {
            Platform.runLater(() -> updateMP((int) evt.getNewValue()));
        }
        if (evt.getPropertyName().equals("turn")) {
            Platform.runLater(() -> {
                battleScene.getHandBox().resetSelection();
                battleScene.getMapBox().resetSelection();
                if ((int) evt.getNewValue() % 2 == 1) {
                    player1Image.setOpacity(1);
                    player2Image.setOpacity(0.7);
                } else {
                    player1Image.setOpacity(0.7);
                    player2Image.setOpacity(1);
                }
            });
        }
    }

    void showMessage(CompressedPlayer player, String text) {
        if (player.getPlayerNumber() == 1) {
            player1MessageShow.show(text);
        } else {
            player2MessageShow.show(text);
        }
    }

    class MessageShow extends AnimationTimer {
        private final long showTime = (long) (4 * pow(10, 9));
        private final DefaultText text = new DefaultText("", CHAT_BUBBLE_SIZE * 0.9, DEFAULT_FONT, Color.BLACK);
        private final ImageView chatView = ImageLoader.makeImageView(chatImage, CHAT_BUBBLE_SIZE, CHAT_BUBBLE_SIZE);
        private final StackPane stackPane;
        private final double x;
        private final double y;
        private long initialTime;

        MessageShow(ImageView playerImage) {
            x = playerImage.getX() + (playerImage.getFitWidth() - CHAT_BUBBLE_SIZE) / 2;
            y = playerImage.getY() + playerImage.getFitHeight();
            stackPane = new StackPane(chatView, text);
            stackPane.relocate(x, y);
        }

        void show(String text) {
            this.text.setText(text);
            initialTime = 0;
            start();
        }

        @Override
        public void handle(long now) {
            if (initialTime == 0) {
                initialTime = now;
                group.getChildren().add(stackPane);
            }
            if (now - initialTime > showTime) {
                group.getChildren().remove(stackPane);
                stop();
            }
        }
    }
}