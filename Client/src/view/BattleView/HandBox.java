package view.BattleView;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import models.comperessedData.CompressedCard;
import models.comperessedData.CompressedPlayer;
import models.gui.CardPane;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class HandBox implements PropertyChangeListener {
    private final BattleScene battleScene;
    private final CompressedPlayer player;
    private final Group group;
    private final Pane[] cards = new Pane[5];
    private final Pane next = new Pane();
    private int selectedCard = -1;
    private CardPane cardPane = null;


    public HandBox(BattleScene battleScene, CompressedPlayer player, double x, double y){
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
        addMenuButton();

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

            if (cardAnimation != null && battleScene.isMyTurn()) {
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
                            imageView.getLayoutY() + imageView.getFitHeight() / 2, imageView.getLayoutX() + imageView.getFitWidth() / 2);
                } else {
                    cardAnimation = null;
                }

                if (selectedCard == i)
                    imageView.setImage(new Image(new FileInputStream("resources/ui/card_background_highlight@2x.png")));
                else
                    imageView.setImage(new Image(new FileInputStream("resources/ui/card_background@2x.png")));

                if (cardAnimation != null && battleScene.isMyTurn()) {
                    cards[i].setOnMouseEntered(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            if (cardPane != null)
                                group.getChildren().remove(cardPane);
                            cardAnimation.inActive();
                            try {
                                imageView.setImage(new Image(new FileInputStream("resources/ui/card_background_highlight@2x.png")));
//                                cardPane = new CardPane(player.getHand().get(I), false, false);
//                                cardPane.setLayoutY(-300 * Constants.SCALE);
//                                cardPane.setLayoutX(300 * Constants.SCALE);
//                                group.getChildren().add(cardPane);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    cards[i].setOnMouseExited(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            if (cardPane != null) {
                                group.getChildren().remove(cardPane);
                                cardPane = null;
                            }
                            try {
                                if (selectedCard == I) {
                                    imageView.setImage(new Image(new FileInputStream("resources/ui/card_background_highlight@2x.png")));
                                } else {
                                    imageView.setImage(new Image(new FileInputStream("resources/ui/card_background@2x.png")));
                                    cardAnimation.pause();
                                }
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
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
            ImageView endTurnButton = new ImageView(new Image(new FileInputStream("resources/ui/button_end_turn_finished@2x.png")));
            endTurnButton.setFitWidth(endTurnButton.getImage().getWidth() * Constants.SCALE * 0.5);
            endTurnButton.setFitHeight(endTurnButton.getImage().getHeight() * Constants.SCALE * 0.5);
            endTurnButton.setX(1200 * Constants.SCALE);
            endTurnButton.setY(5 * Constants.SCALE);
            group.getChildren().add(endTurnButton);
            endTurnButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    try {
                        endTurnButton.setImage(new Image(new FileInputStream("resources/ui/button_end_turn_finished_glow@2x.png")));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
            endTurnButton.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    try {
                        endTurnButton.setImage(new Image(new FileInputStream("resources/ui/button_end_turn_finished@2x.png")));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
            endTurnButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    battleScene.getController().endTurn();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addMenuButton() {
        try {
            ImageView menuButton = new ImageView(new Image(new FileInputStream("resources/ui/button_primary_left@2x.png")));
            menuButton.setFitWidth(menuButton.getImage().getWidth() * Constants.SCALE * 0.5);
            menuButton.setFitHeight(menuButton.getImage().getHeight() * Constants.SCALE * 0.5);
            menuButton.setX(1160 * Constants.SCALE);
            menuButton.setY(110 * Constants.SCALE);
            group.getChildren().add(menuButton);
            menuButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    try {
                        menuButton.setImage(new Image(new FileInputStream("resources/ui/button_primary_left_glow@2x.png")));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
            menuButton.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    try {
                        menuButton.setImage(new Image(new FileInputStream("resources/ui/button_primary_left@2x.png")));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
            menuButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    System.out.println("Go To Menu");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addGraveYardButton() {
        try {
            ImageView graveYardButton = new ImageView(new Image(new FileInputStream("resources/ui/button_primary_right@2x.png")));
            graveYardButton.setFitWidth(graveYardButton.getImage().getWidth() * Constants.SCALE * 0.5);
            graveYardButton.setFitHeight(graveYardButton.getImage().getHeight() * Constants.SCALE * 0.5);
            graveYardButton.setX(1325 * Constants.SCALE);
            graveYardButton.setY(110 * Constants.SCALE);
            group.getChildren().add(graveYardButton);
            graveYardButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    try {
                        graveYardButton.setImage(new Image(new FileInputStream("resources/ui/button_primary_right_glow@2x.png")));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
            graveYardButton.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    try {
                        graveYardButton.setImage(new Image(new FileInputStream("resources/ui/button_primary_right@2x.png")));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
            graveYardButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    System.out.println("GraveYard");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        if (selectedCard == i)
            selectedCard = -1;
        else {
            battleScene.getMapBox().resetSelection();
            selectedCard = i;
        }

        updateCards();
    }

    public Group getGroup() {
        return group;
    }

    public CompressedCard getSelectedCard() {
        if (selectedCard >= 0)
            return player.getHand().get(selectedCard);
        return null;
    }

    public void resetSelection() {
        selectedCard = -1;
        updateCards();
    }
}
