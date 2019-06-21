package models.gui;

import controller.Client;
import controller.ShopController;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;

import static models.gui.UIConstants.SCALE;

public class ShopSearchBox extends HBox implements PropertyChangeListener {
    private static final double ICON_SIZE = 50 * SCALE;
    private static final String GOLD_ICON_URL = "resources/ui/icon_gold.png";
    private final DefaultLabel money;

    public ShopSearchBox() throws FileNotFoundException {
        super(UIConstants.DEFAULT_SPACING * 2);
        Client.getInstance().getAccount().addPropertyChangeListener(this);
        setAlignment(Pos.CENTER);

        ImageView goldIcon = ImageLoader.loadImage(GOLD_ICON_URL, ICON_SIZE, ICON_SIZE);
        money = new DefaultLabel(
                Client.getInstance().getAccount().getMoney() + "$",
                UIConstants.DEFAULT_FONT, Color.WHITE
        );
        TextField searchField = new SearchField();
        searchField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                ShopController.getInstance().searchInShop(searchField.getText());
                searchField.clear();
            }
        });

        Button searchButton = new SearchButton(event -> {
            ShopController.getInstance().searchInShop(searchField.getText());
            searchField.clear();
        });

        getChildren().addAll(goldIcon, money, searchField, searchButton);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("money")) {
            Platform.runLater(() -> money.setText(evt.getNewValue() + "$"));
        }
    }
}