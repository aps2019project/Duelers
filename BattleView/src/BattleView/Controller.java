package BattleView;

import javafx.application.Application;
import javafx.stage.Stage;
import models.comperessedData.*;
import models.map.Position;

import java.util.ArrayList;

public class Controller extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setResizable(false);
        primaryStage.setFullScreen(true);
        final CompressedCell[][] cells = new CompressedCell[5][8];
        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < 8; i++) {
                cells[j][i] = new CompressedCell(j, i, null, j + i);
            }
        }
        final ArrayList<CompressedTroop> troops = new ArrayList<>();
        final CompressedGameMap map = new CompressedGameMap(cells, troops);
        final CompressedGame game = new CompressedGame(null, null, map, 0, null);
        new Thread(new Runnable() {
            @Override
            public void run() {
                CompressedCard card = new CompressedCard("boss_candypanda", null, null, null,
                        null, 0, 0, 0, null, 2, true);
                CompressedTroop troop1 = new CompressedTroop(card, 5, 6, 5, new Position(2, 2),
                        true, true, false, false, 1, 1);
                map.addTroop(troop1);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                CompressedTroop troop2 = new CompressedTroop(card, 5, 6, 5, new Position(4, 4),
                        true, true, false, false, 1, 2);
                map.addTroop(troop2);
            }
        });
        primaryStage.setScene(new BattleScene(this, game).getScene());
        primaryStage.show();
    }

    public void sendMessage(String string) {
        System.out.println(string);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
