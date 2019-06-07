import javafx.collections.FXCollections;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

class DeckListView extends ListView<String> {
    DeckListView(String[] deckNames) {
        super(FXCollections.observableArrayList(deckNames));
        setCellFactory(param -> new ListCell<String>() {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item);
                    setFont(Constants.DEFAULT_FONT);
                } else {
                    setText(null);
                }
            }
        });
    }
}
