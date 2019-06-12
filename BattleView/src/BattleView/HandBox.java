package BattleView;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import models.comperessedData.CompressedPlayer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileInputStream;

public class HandBox implements PropertyChangeListener {
    private final Controller controller;
    private final CompressedPlayer player;
    private final Group handGroup;
    private final ImageView[] cardBackGrounds = new ImageView[5];
    private ImageView nextBackGround;
    private final ImageView[] cardImages = new ImageView[5];
    private ImageView nextImage;

    public HandBox(Controller controller, CompressedPlayer player, double x, double y) throws Exception {
        this.controller = controller;
        this.player = player;
        handGroup = new Group();
        handGroup.setLayoutX(x);
        handGroup.setLayoutY(y);
        addBackGrounds();
        //player.addPropertyChangeListener(this);
    }

    private void addBackGrounds() {
        try {
            nextBackGround = new ImageView(new Image(
                    new FileInputStream("resources/hand/replace_outer_ring_smoke@2x.png")));
            /*nextBackGround.setScaleX(Constants.SCALE*0.25);
            nextBackGround.setScaleY(Constants.SCALE*0.25);
            nextBackGround.setX(-nextBackGround.getImage().getWidth()*0.75);
            nextBackGround.setY(-nextBackGround.getImage().getHeight()*0.75);*/
            //handGroup.getChildren().add(nextBackGround);

            for (int i = 0; i < 5; i++) {
                cardBackGrounds[i] = new ImageView(new Image(
                        new FileInputStream("resources/hand/craftable_prismatic_spell@2x.png")));
                cardBackGrounds[i].setScaleX(Constants.SCALE*0.25);
                cardBackGrounds[i].setScaleY(Constants.SCALE*0.25);
                cardBackGrounds[i].setX(-cardBackGrounds[i].getImage().getWidth()*0.4);
                cardBackGrounds[i].setY(-cardBackGrounds[i].getImage().getHeight()*0.4);
                cardBackGrounds[i].setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        //Shine
                    }
                });
                cardBackGrounds[i].setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        //unShine
                    }
                });
                cardBackGrounds[i].setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        //Select
                    }
                });
                handGroup.getChildren().add(cardBackGrounds[i]);
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Error making hand");
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }

    public Group getHandGroup() {
        return handGroup;
    }
}
