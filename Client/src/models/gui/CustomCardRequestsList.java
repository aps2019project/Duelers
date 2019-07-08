package models.gui;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.account.Collection;

import java.util.stream.Collectors;

import static models.gui.UIConstants.SCALE;

public class CustomCardRequestsList extends TableView {

    public CustomCardRequestsList() {
        super();

        TableColumn<CardRequestView, Integer> index = new TableColumn<>("Index");
        index.setSortable(false);
        index.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(getItems().indexOf(param.getValue()) + 1)
        );
        index.setMaxWidth(100 * SCALE);

        TableColumn<CardRequestView, String> cardName = new TableColumn<>("Name");
        cardName.setCellValueFactory(new PropertyValueFactory<>("cardName"));
        cardName.setMinWidth(300 * SCALE);

        TableColumn<CardRequestView, String> cardType = new TableColumn<>("Card type");
        cardType.setCellValueFactory(new PropertyValueFactory<>("cardType"));
        cardType.setMinWidth(300 * SCALE);

        TableColumn<CardRequestView, Button> accept = new TableColumn<>("Accept");
        accept.setCellValueFactory(new PropertyValueFactory<>("accept"));
        accept.setMaxWidth(100 * SCALE);

        TableColumn<CardRequestView, Button> reject = new TableColumn<>("Reject");
        reject.setCellValueFactory(new PropertyValueFactory<>("reject"));
        reject.setMaxWidth(100 * SCALE);

        getColumns().addAll(index, cardName, cardType, accept, reject);
    }

    public void setCards(Collection collection) {
        getItems().addAll(collection.getHeroes().stream().map(CardRequestView::new).collect(Collectors.toList()));
        getItems().addAll(collection.getMinions().stream().map(CardRequestView::new).collect(Collectors.toList()));
        getItems().addAll(collection.getSpells().stream().map(CardRequestView::new).collect(Collectors.toList()));
        getItems().addAll(collection.getItems().stream().map(CardRequestView::new).collect(Collectors.toList()));
    }
}
