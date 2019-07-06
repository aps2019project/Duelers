package view.BattleView;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import models.comperessedData.CompressedCard;
import models.comperessedData.CompressedPlayer;
import models.gui.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import static models.gui.UIConstants.SCALE;

public class HandBox implements PropertyChangeListener {
    private final BattleScene battleScene;
    private final CompressedPlayer player;
    private final Group group;
    private final Pane[] cards = new Pane[5];
    private final Pane next = new Pane();
    private int selectedCard = -1;
    private CardPane cardPane = null;
    private Image backGround = new Image(new FileInputStream("resources/ui/card_background@2x.png"));
    private Image highlightedBackGround = new Image(new FileInputStream("resources/ui/card_background_highlight@2x.png"));


    public HandBox(BattleScene battleScene, CompressedPlayer player, double x, double y) throws Exception {
        this.battleScene = battleScene;
        this.player = player;
        group = new Group();
        group.setLayoutX(x);
        group.setLayoutY(y);

        HBox hBox = new HBox();
        hBox.setLayoutX(300 * Constants.SCALE);
        hBox.setLayoutY(25 * Constants.SCALE);
        group.getChildren().add(hBox);
        for (int i = 0; i < 5; i++) {
            cards[i] = new Pane();
            hBox.getChildren().add(cards[i]);
        }
        updateCards();

        next.setLayoutX(50 * Constants.SCALE);
        next.setLayoutY(0);
        group.getChildren().add(next);
        updateNext();

        addEndTurnButton();
        addGraveYardButton();
        addFinishButton();

        player.addPropertyChangeListener(this);
    }

    private void updateNext() {
        try {
            next.getChildren().clear();
            final ImageView imageView1 = new ImageView();
            next.getChildren().add(imageView1);
            imageView1.setFitWidth(Constants.SCREEN_WIDTH * 0.11);
            imageView1.setFitHeight(Constants.SCREEN_WIDTH * 0.11);
            try {
                imageView1.setImage(new Image(new FileInputStream("resources/ui/replace_background@2x.png")));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            final ImageView imageView2 = new ImageView();
            next.getChildren().add(imageView2);
            imageView2.setFitWidth(Constants.SCREEN_WIDTH * 0.11);
            imageView2.setFitHeight(Constants.SCREEN_WIDTH * 0.11);

            final CardAnimation cardAnimation;
            if (player.getNextCard() != null) {
                cardAnimation = new CardAnimation(next, player.getNextCard(),
                        imageView1.getLayoutY() + imageView1.getFitHeight() / 2, imageView1.getLayoutX() + imageView1.getFitWidth() / 2);
            } else {
                cardAnimation = null;
            }

            try {
                imageView2.setImage(new Image(new FileInputStream("resources/ui/replace_outer_ring_smoke@2x.png")));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            if (cardAnimation != null) {
                next.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        try {
                            cardAnimation.inActive();
                            imageView2.setImage(new Image(new FileInputStream("resources/ui/replace_outer_ring_shine@2x.png")));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });
                next.setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        try {
                            cardAnimation.stop();
                            imageView2.setImage(new Image(new FileInputStream("resources/ui/replace_outer_ring_smoke@2x.png")));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }


        } catch (Exception e) {
            System.out.println("Error showing next");
            e.printStackTrace();
        }
    }

    private void updateCards() {
        try {
            for (int i = 0; i < 5; i++) {
                final int I = i;
                cards[i].getChildren().clear();
                final ImageView imageView = new ImageView();
                cards[i].getChildren().add(imageView);
                imageView.setFitWidth(Constants.SCREEN_WIDTH * 0.085);
                imageView.setFitHeight(Constants.SCREEN_WIDTH * 0.085);

                final CardAnimation cardAnimation;
                if (player.getHand().size() > i) {
                    cardAnimation = new CardAnimation(cards[i], player.getHand().get(i),
                            imageView.getFitHeight() / 2, imageView.getFitWidth() / 2);
                } else {
                    cardAnimation = null;
                }

                if (selectedCard == i) {
                    imageView.setImage(highlightedBackGround);
                    cardAnimation.inActive();
                } else
                    imageView.setImage(backGround);

                if (cardAnimation != null && battleScene.isMyTurn()) {
                    cards[i].setOnMouseEntered(mouseEvent -> {
                        if (cardPane != null)
                            group.getChildren().remove(cardPane);
                        cardAnimation.inActive();
                        try {
                            imageView.setImage(highlightedBackGround);
                            if (player.getHand().size() > I) {
                                cardPane = new CardPane(player.getHand().get(I), false, false, null);
                                cardPane.setLayoutY(-300 * Constants.SCALE + cards[I].getLayoutY());
                                cardPane.setLayoutX(243 * Constants.SCALE + cards[I].getLayoutX());
                                group.getChildren().add(cardPane);
                            }
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    });

                    cards[i].setOnMouseExited(mouseEvent -> {
                        if (cardPane != null) {
                            group.getChildren().remove(cardPane);
                            cardPane = null;
                        }
                        if (selectedCard == I) {
                            imageView.setImage(highlightedBackGround);
                        } else {
                            imageView.setImage(backGround);
                            cardAnimation.pause();
                        }
                    });

                    cards[i].setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            clickOnCard(I);
                        }
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error making hand");
        }
    }

    private void addEndTurnButton() {
        try {
            ImageButton imageButton = new ImageButton(
                    "END TURN", event -> battleScene.getController().endTurn(),
                    new Image(new FileInputStream("resources/ui/button_end_turn_finished@2x.png")),
                    new Image(new FileInputStream("resources/ui/button_end_turn_finished_glow@2x.png"))
            );
            imageButton.setLayoutX(1200 * Constants.SCALE);
            imageButton.setLayoutY(5 * Constants.SCALE);

            group.getChildren().add(imageButton);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addFinishButton() {
        try {
            ImageButton imageButton = new ImageButton(
                    "Finish", event -> battleScene.getController().forceFinish(),
                    new Image(new FileInputStream("resources/ui/button_primary_left@2x.png")),
                    new Image(new FileInputStream("resources/ui/button_primary_left_glow@2x.png"))
            );
            imageButton.setLayoutX(1160 * Constants.SCALE);
            imageButton.setLayoutY(110 * Constants.SCALE);

            group.getChildren().add(imageButton);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addGraveYardButton() {
        try {
            ImageButton imageButton = new ImageButton(
                    "GRAVEYARD", event -> showGraveyard(),
                    new Image(new FileInputStream("resources/ui/button_primary_right@2x.png")),
                    new Image(new FileInputStream("resources/ui/button_primary_right_glow@2x.png"))
            );
            imageButton.setLayoutX(1330 * Constants.SCALE);
            imageButton.setLayoutY(110 * Constants.SCALE);

            group.getChildren().add(imageButton);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showGraveyard() {
        ScrollPane scrollPane = new ScrollPane();
        GridPane cardsPane = new GridPane();
        cardsPane.setVgap(UIConstants.DEFAULT_SPACING * 2);
        cardsPane.setHgap(UIConstants.DEFAULT_SPACING * 2);

        List<CompressedCard> graveyard = player.getGraveyard();
        for (int i = 0; i < graveyard.size(); i++) {
            CompressedCard card = graveyard.get(i);
            try {
                CardPane cardPane = new CardPane(card, false, false, null);
                cardsPane.add(cardPane, i % 3, i / 3);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        scrollPane.setContent(cardsPane);
        scrollPane.setId("background_transparent");
        scrollPane.setMinSize(1600 * SCALE, 1000 * SCALE);
        DialogBox dialogBox = new DialogBox(scrollPane);
        DialogContainer dialogContainer = new DialogContainer(battleScene.root, dialogBox);
        dialogContainer.show();
        dialogBox.makeClosable(dialogContainer);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("next")) {
            Platform.runLater(this::updateNext);
        }
        if (evt.getPropertyName().equals("hand")) {
            Platform.runLater(() -> {
                selectedCard = -1;
                updateCards();
            });
        }
    }

    private void clickOnCard(int i) {
        if (selectedCard == i) {
            selectedCard = -1;
            battleScene.getMapBox().updateMapColors();
        } else {
            selectedCard = i;
            battleScene.getMapBox().resetSelection();
        }
        updateCards();
    }

    Group getGroup() {
        return group;
    }

    CompressedCard getSelectedCard() {
        if (selectedCard >= 0)
            return player.getHand().get(selectedCard);
        return null;
    }

    public void resetSelection() {
        selectedCard = -1;
        updateCards();
    }
}
