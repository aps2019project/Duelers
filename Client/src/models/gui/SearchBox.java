package models.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class SearchBox extends HBox {

    public SearchBox() {
        super(UIConstants.DEFAULT_SPACING);
        setAlignment(Pos.CENTER);

        TextField searchField = new SearchField();
        Button searchButton = new SearchButton(searchField);

        getChildren().addAll(searchField, searchButton);
    }
}