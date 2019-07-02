package view.BattleView;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import models.card.CardType;
import models.comperessedData.CompressedCard;
import models.comperessedData.CompressedGameMap;
import models.comperessedData.CompressedTroop;
import models.gui.CardPane;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;

public class MapBox implements PropertyChangeListener {
    private final BattleScene battleScene;
    private final CompressedGameMap gameMap;
    private final Group mapGroup;
    private final Polygon[][] cells = new Polygon[5][9];
    private final double[][] cellsX = new double[5][9];
    private final double[][] cellsY = new double[5][9];
    private final HashMap<CompressedTroop, TroopAnimation> troopAnimationHashMap = new HashMap<>();
    private CompressedTroop selectedTroop = null;
    private ArrayList<CompressedTroop> comboTroops = new ArrayList<>();
    private boolean spellSelected = false;
    private boolean comboSelected = false;
    private CardPane cardPane = null;
    private SelectionType selectionType;

    MapBox(BattleScene battleScene, CompressedGameMap gameMap, double x, double y) throws Exception {
        this.battleScene = battleScene;
        this.gameMap = gameMap;
        mapGroup = new Group();
        mapGroup.setLayoutY(y);
        mapGroup.setLayoutX(x);
        makePolygons();
        resetSelection();
        for (CompressedTroop troop : gameMap.getTroops()) {
            updateTroop(null, troop);
        }
        gameMap.addPropertyChangeListener(this);
    }

    private void makePolygons() {
        for (int j = 0; j < 5; j++) {
            double upperWidth = (j * Constants.MAP_DOWNER_WIDTH + (6 - j) * Constants.MAP_UPPER_WIDTH) / 6;
            double downerWidth = ((j + 1) * Constants.MAP_DOWNER_WIDTH + (6 - (j + 1)) * Constants.MAP_UPPER_WIDTH) / 6;
            double upperY = Constants.MAP_HEIGHT * (upperWidth - Constants.MAP_UPPER_WIDTH) /
                    (Constants.MAP_DOWNER_WIDTH - Constants.MAP_UPPER_WIDTH);
            double downerY = Constants.MAP_HEIGHT * (downerWidth - Constants.MAP_UPPER_WIDTH) /
                    (Constants.MAP_DOWNER_WIDTH - Constants.MAP_UPPER_WIDTH);
            for (int i = 0; i < 9; i++) {
                double x1 = (Constants.MAP_DOWNER_WIDTH - upperWidth) / 2 + i * upperWidth / 9;
                double x2 = (Constants.MAP_DOWNER_WIDTH - upperWidth) / 2 + (i + 1) * upperWidth / 9;
                double x3 = (Constants.MAP_DOWNER_WIDTH - downerWidth) / 2 + (i + 1) * downerWidth / 9;
                double x4 = (Constants.MAP_DOWNER_WIDTH - downerWidth) / 2 + i * downerWidth / 9;
                cells[j][i] = new Polygon(x1 + Constants.SPACE_BETWEEN_CELLS / 2,
                        upperY + Constants.SPACE_BETWEEN_CELLS / 2, x2 - Constants.SPACE_BETWEEN_CELLS / 2,
                        upperY + Constants.SPACE_BETWEEN_CELLS / 2, x3 - Constants.SPACE_BETWEEN_CELLS / 2,
                        downerY - Constants.SPACE_BETWEEN_CELLS / 2, x4 + Constants.SPACE_BETWEEN_CELLS / 2,
                        downerY - Constants.SPACE_BETWEEN_CELLS / 2);
                cells[j][i].setFill(Color.DARKBLUE);
                cells[j][i].setOpacity(Constants.CELLS_OPACITY);
                final int I = i, J = j;
                cells[j][i].setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        hoverCell(J, I);
                    }
                });
                cells[j][i].setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        exitCell(J, I);
                    }
                });
                cells[j][i].setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        clickCell(J, I);
                    }
                });
                mapGroup.getChildren().add(cells[j][i]);
                cellsX[j][i] = (x1 + x2 + x3 + x4) / 4;
                cellsY[j][i] = (upperY + downerY) / 2;
            }
        }
    }

    private void addCircles() {
        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < 9; i++) {
                mapGroup.getChildren().add(new Circle(cellsX[j][i], cellsY[j][i], 2));
            }
        }
    }

    private void updateTroop(CompressedTroop oldTroop, CompressedTroop newTroop) {
        final TroopAnimation animation;
        if (newTroop == null) {
            animation = troopAnimationHashMap.get(oldTroop);
            if (animation != null) {
                animation.updateApHp(oldTroop.getCurrentAp(), 0);
                animation.getTroopGroup().setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                    }
                });
                animation.getTroopGroup().setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                    }
                });
                animation.getTroopGroup().setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                    }
                });
                animation.kill();
                troopAnimationHashMap.remove(oldTroop);
            }
        } else if (oldTroop != null && troopAnimationHashMap.containsKey(oldTroop)) {
            animation = troopAnimationHashMap.get(oldTroop);
            troopAnimationHashMap.remove(oldTroop);
            troopAnimationHashMap.put(newTroop, animation);
            animation.updateApHp(newTroop.getCurrentAp(), newTroop.getCurrentHp());
            animation.moveTo(newTroop.getPosition().getRow(), newTroop.getPosition().getColumn());
        } else {
            try {
                animation = new TroopAnimation(mapGroup, cellsX, cellsY, newTroop.getCard().getSpriteName(),
                        newTroop.getPosition().getRow(), newTroop.getPosition().getColumn(),
                        newTroop.getPlayerNumber() == 1,
                        newTroop.getPlayerNumber() == battleScene.getMyPlayerNumber());
                animation.updateApHp(newTroop.getCurrentAp(), newTroop.getCurrentHp());
                animation.getTroopGroup().setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        clickCell(animation.getRow(), animation.getColumn());
                    }
                });
                animation.getTroopGroup().setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        hoverCell(animation.getRow(), animation.getColumn());
                    }
                });
                animation.getTroopGroup().setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        exitCell(animation.getRow(), animation.getColumn());
                    }
                });
                troopAnimationHashMap.put(newTroop, animation);
            } catch (Exception e) {
                System.out.println("Error making animation " + newTroop.getCard().getCardId());
            }
        }
        resetSelection();
    }

    Group getMapGroup() {
        return mapGroup;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("troop")) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    updateTroop((CompressedTroop) evt.getOldValue(), (CompressedTroop) evt.getNewValue());
                }
            });
        }
    }

    void resetSelection() {
        selectedTroop = null;
        comboTroops.clear();
        spellSelected = false;
        comboSelected = false;
        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < 9; i++) {
                cells[j][i].setFill(Color.DARKBLUE);
            }
        }
        for (TroopAnimation animation : troopAnimationHashMap.values()) {
            animation.diSelect();
        }
        battleScene.getPlayerBox().refreshComboAndSpell();
        updateMapColors();
    }

    private void exitCell(int j, int i) {
        CompressedTroop troop = getTroop(j, i);
        if (cardPane != null) {
            mapGroup.getChildren().remove(cardPane);
            cardPane = null;
        }
        if (troop == null) {
            cells[j][i].setFill(Color.DARKBLUE);
            return;
        }
        TroopAnimation animation = troopAnimationHashMap.get(troop);
        if (selectedTroop == troop || comboTroops.contains(troop)) {
            cells[j][i].setFill(Color.DARKGREEN);
            return;
        } else {
            animation.diSelect();
        }
        cells[j][i].setFill(Color.DARKBLUE);
    }

    private void hoverCell(int row, int column) {
        CompressedTroop troop = getTroop(row, column);
        CompressedCard card = battleScene.getHandBox().getSelectedCard();
        if (troop != null) {
            TroopAnimation animation = troopAnimationHashMap.get(troop);
            animation.select();
            if (cardPane != null) {
                mapGroup.getChildren().remove(cardPane);
                cardPane = null;
            }
            try {
                cardPane = new CardPane(troop.getCard(), false, false, null);
                cardPane.setLayoutY(-150 * Constants.SCALE + cellsY[row][column]);
                cardPane.setLayoutX(80 * Constants.SCALE + cellsX[row][column]);
                mapGroup.getChildren().add(cardPane);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        if (!battleScene.isMyTurn())
            return;
        if (card == null) {
            if (troop == null) {
                if (selectedTroop != null) {
                    if (spellSelected) {
                        cells[row][column].setFill(Color.DEEPPINK);//spell
                    } else
                        cells[row][column].setFill(Color.DARKGREEN);//move
                }
            } else {
                if (selectedTroop == null) {
                    if (troop.getPlayerNumber() == battleScene.getMyPlayerNumber())
                        cells[row][column].setFill(Color.DARKGREEN);
                } else {
                    if (troop == selectedTroop) {
                        cells[row][column].setFill(Color.DARKGREEN);
                    } else {
                        if (spellSelected) {
                            cells[row][column].setFill(Color.DEEPPINK);
                        } else {
                            if (comboSelected) {
                                if (troop.getPlayerNumber() == battleScene.getMyPlayerNumber() && troop.getCard().isHasCombo())
                                    cells[row][column].setFill(Color.DARKGREEN);
                                else if (troop.getPlayerNumber() != battleScene.getMyPlayerNumber()) {
                                    cells[row][column].setFill(Color.DARKRED);
                                } else {
                                    cells[row][column].setFill(Color.DARKBLUE);
                                }
                            } else {
                                if (troop.getPlayerNumber() != battleScene.getMyPlayerNumber()) {
                                    cells[row][column].setFill(Color.DARKRED);
                                }
                            }
                        }
                    }
                }
            }
        } else {
            if (card.getType() == CardType.SPELL || card.getType() == CardType.USABLE_ITEM) {
                cells[row][column].setFill(Color.DEEPPINK);
            } else {
                if (troop == null)
                    cells[row][column].setFill(Color.DARKGREEN);
            }
        }
    }

    private void clickCell(int row, int column) {
        CompressedTroop troop = getTroop(row, column);
        CompressedCard card = battleScene.getHandBox().getSelectedCard();
        if (!battleScene.isMyTurn())
            return;
        if (card == null) {
            if (troop == null) {
                if (selectedTroop != null) {
                    if (spellSelected) {
                        battleScene.getController().useSpecialPower(row, column);
                        resetSelection();
                    } else {
                        battleScene.getController().move(selectedTroop, row, column);
                        resetSelection();
                    }
                }
            } else {
                if (selectedTroop == null) {
                    if (troop.getPlayerNumber() == battleScene.getMyPlayerNumber()) {
                        selectedTroop = troop;
                        System.out.println("Select Troop");
                    }
                } else {
                    if (troop == selectedTroop) {
                        resetSelection();
                        System.out.println("diSelect Troop");
                    } else {
                        if (spellSelected) {
                            battleScene.getController().useSpecialPower(row, column);
                            resetSelection();
                        } else {
                            if (comboSelected) {
                                if (troop.getPlayerNumber() == battleScene.getMyPlayerNumber() && troop.getCard().isHasCombo()) {
                                    comboTroops.add(troop);
                                } else if (troop.getPlayerNumber() != battleScene.getMyPlayerNumber()) {
                                    comboTroops.add(selectedTroop);
                                    battleScene.getController().comboAttack(comboTroops, troop);
                                    resetSelection();
                                }
                            } else {
                                if (troop.getPlayerNumber() != battleScene.getMyPlayerNumber()) {
                                    battleScene.getController().attack(selectedTroop, troop);
                                    resetSelection();
                                }
                            }
                        }
                    }
                }
            }
        } else {
            if (card.getType() == CardType.SPELL || card.getType() == CardType.USABLE_ITEM) {
                battleScene.getController().insert(card, row, column);
                resetSelection();
            } else {
                if (troop == null) {
                    battleScene.getController().insert(card, row, column);
                    resetSelection();
                }
            }
            battleScene.getHandBox().resetSelection();
        }
    }

    void updateMapColors() {

    }

    private void updateSelectionType() {
        if (battleScene.getHandBox().getSelectedCard() != null) {
            selectionType = SelectionType.INSERTION;
            return;
        }
        if (selectedTroop == null) {
            selectionType = SelectionType.SELECTION;
            return;
        }
        if (isComboSelected()) {
            selectionType = SelectionType.COMBO;
            return;
        }
        if (isSpellSelected()) {
            selectionType = SelectionType.SPELL;
            return;
        }
        selectionType = SelectionType.NORMAL;
    }

    private CompressedTroop getTroop(int j, int i) {
        for (CompressedTroop troop : troopAnimationHashMap.keySet()) {
            if (troop.getPosition().getRow() == j && troop.getPosition().getColumn() == i)
                return troop;
        }
        return null;
    }

    CompressedTroop getSelectedTroop() {
        return selectedTroop;
    }

    void setSpellSelected() {
        this.spellSelected = true;
    }

    void setComboSelected() {
        this.comboSelected = true;
    }

    boolean isSpellSelected() {
        return spellSelected;
    }

    boolean isComboSelected() {
        return comboSelected;
    }

    CompressedGameMap getGameMap() {
        return gameMap;
    }

    void showAttack(String cardId, int i) {
        if (cardId == null)
            System.out.println("Error0 MapBox");
        CompressedTroop troop = gameMap.getTroop(cardId);
        if (troop == null)
            System.out.println("Error1 MapBox");
        TroopAnimation animation = troopAnimationHashMap.get(troop);
        if (animation == null)
            System.out.println("Error2 MapBox");
        else
            animation.attack(i);
    }

    void showDefend(String cardId, int i) {
        CompressedTroop troop = gameMap.getTroop(cardId);
        if (troop == null)
            System.out.println("Error3 MapBox");
        else {
            TroopAnimation animation = troopAnimationHashMap.get(troop);
            if (animation == null)
                System.out.println("Error4 MapBox");
            else
                animation.hit(i);
        }
    }

    void showSpell(String spriteName, int j, int i) {
        //TODO
    }

    enum SelectionType {
        INSERTION, SELECTION, COMBO, SPELL, NORMAL
    }
}
