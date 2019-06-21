package models.gui;

import controller.CollectionMenuController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;

public class CollectionSearchBox extends HBox {

    public CollectionSearchBox() {
        super(UIConstants.DEFAULT_SPACING * 2);
        setAlignment(Pos.CENTER);

        TextField searchField = new SearchField();
        searchField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                CollectionMenuController.getInstance().search(searchField.getText());
                searchField.clear();
            }
        });
        Button searchButton = new SearchButton(event -> {
            CollectionMenuController.getInstance().search(searchField.getText());
            searchField.clear();
        });

        getChildren().addAll(searchField, searchButton);
    }
}
