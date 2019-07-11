package view.BattleView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.card.CardType;
import models.comperessedData.*;
import models.game.CellEffect;
import models.game.GameActions;
import models.game.map.Position;

import java.util.ArrayList;

public class Calibrate extends Application implements GameActions {
    private final int playerNumber = 1;
    private BattleScene battleScene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setResizable(false);
        battleScene = new BattleScene(this, calibrateGame(), playerNumber, "battlemap6_middleground@2x");
        battleScene.getMapBox().addCircles();
        Scene scene = new Scene(battleScene.root, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private CompressedGame calibrateGame() {
        final CompressedCell[][] cells = new CompressedCell[5][9];
        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < 9; i++) {
                cells[j][i] = new CompressedCell(j, i, null, 0);
            }
        }
        final CompressedPlayer player1 = new CompressedPlayer("Ali", 2, new ArrayList<>(), null,
                null, null, 1, 0, null, null);
        final CompressedPlayer player2 = new CompressedPlayer("Ali1", 1, new ArrayList<>(), null,
                null, null, 2, 0, null, null);
        final CompressedPlayer myPlayer;
        if (playerNumber == 1)
            myPlayer = player1;
        else
            myPlayer = player2;
        final ArrayList<CompressedTroop> troops = new ArrayList<>();
        final CompressedGameMap map = new CompressedGameMap(cells, troops);
        final CompressedGame game = new CompressedGame(player1, player2, map, 7, null);

        new Thread(() -> {
            String troop1 = "boss_andromeda";
            String spell1 = "fx_buff";

            CompressedCard card = new CompressedCard(troop1, null, "a1", CardType.MINION,
                    null, 0, 0, 0, null, 2, true);
            CompressedTroop troop = new CompressedTroop(card, 5, 6, 5, new Position(0, 0),
                    true, true, false, false, 1, 1);
            map.updateTroop(troop);
            player1.addCardToNext(card);
            player1.addNextCardToHand();
            player1.removeCardFromNext();
            player1.addCardToNext(card);

            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            battleScene.spell(spell1, new Position(2, 2));

        }).start();
        return game;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void attack(CompressedTroop selectedTroop, CompressedTroop troop) {
        battleScene.attack(selectedTroop.getCard().getCardId(), troop.getCard().getCardId());
        battleScene.defend(troop.getCard().getCardId(), selectedTroop.getCard().getCardId());
        battleScene.attack(troop.getCard().getCardId(), selectedTroop.getCard().getCardId());
    }

    @Override
    public void comboAttack(ArrayList<CompressedTroop> comboTroops, CompressedTroop troop) {
        for (CompressedTroop comboAttacker : comboTroops) {
            battleScene.attack(comboAttacker.getCard().getCardId(), troop.getCard().getCardId());
        }
        battleScene.defend(troop.getCard().getCardId(), comboTroops.get(comboTroops.size() - 1).getCard().getCardId());
        battleScene.attack(troop.getCard().getCardId(), comboTroops.get(comboTroops.size() - 1).getCard().getCardId());
    }

    @Override
    public void move(CompressedTroop selectedTroop, int j, int i) {
        battleScene.getMapBox().getGameMap().updateTroop(new CompressedTroop(selectedTroop, j, i));
    }

    @Override
    public void endTurn() {
        battleScene.getGame().gameUpdate(battleScene.getGame().getTurnNumber() + 1, 3,
                0, 3, 0, new CellEffect[]{});
        System.out.println("end turn");
        System.out.println("new turn:" + battleScene.getGame().getTurnNumber());
    }

    @Override
    public void insert(CompressedCard compressedCard, int row, int column) {
        System.out.println("insert");
    }

    @Override
    public void useSpecialPower(int row, int column) {
        System.out.println("use spell");
    }

    @Override
    public void forceFinish() {

    }
}
