package view;

import controller.Client;
import controller.CollectionMenuController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import models.account.Collection;
import models.card.Deck;
import models.gui.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;
import java.util.concurrent.atomic.AtomicBoolean;

import static models.gui.UIConstants.*;

public class CollectionMenu extends Show implements PropertyChangeListener {
    private static final Background DECKS_BACKGROUND = new Background(
            new BackgroundFill(Color.rgb(39, 35, 40), CornerRadii.EMPTY, Insets.EMPTY)
    );
    private static final Font TITLE_FONT = Font.font("DejaVu Sans Light", FontWeight.EXTRA_LIGHT, 45 * SCALE);
    private static final double COLLECTION_WIDTH = SCENE_WIDTH * 0.8;
    private static final double DECKS_WIDTH = SCENE_WIDTH * 0.2;
    private static final double SCROLL_HEIGHT = SCENE_HEIGHT - DEFAULT_SPACING * 13;
    private static final Insets DECKS_PADDING = new Insets(20, 40, 0, 40);
    private VBox cardsBox;
    private VBox decksBox;
    private Collection showingCards;

    CollectionMenu() {
        setCollectionCards();

        try {
            root.setBackground(DEFAULT_ROOT_BACKGROUND);

            BorderPane background = BackgroundMaker.getMenuBackground();
            BackButton backButton = new BackButton(event -> new MainMenu().show());

            HBox collectionPane = new HBox();

            VBox decksPane = new VBox(DEFAULT_SPACING);
            decksPane.setPadding(DECKS_PADDING);
            decksPane.setMinSize(DECKS_WIDTH, SCENE_HEIGHT);
            decksPane.setAlignment(Pos.TOP_CENTER);
            decksPane.setBackground(DECKS_BACKGROUND);

            StackPane newDeckButton = new ImageButton("NEW DECK", event -> showNewDeckDialog());

            decksBox = new VBox(DEFAULT_SPACING);
            decksBox.setAlignment(Pos.TOP_CENTER);

            for (Deck deck : Client.getInstance().getAccount().getDecks()) {
                decksBox.getChildren().addAll(new DeckBox(deck));
            }

            ScrollPane decksScroll = new ScrollPane(decksBox);
            decksScroll.setId("background_transparent");

            decksPane.getChildren().addAll(newDeckButton, decksScroll);

            VBox collectionBox = new VBox(DEFAULT_SPACING * 4);
            collectionBox.setPadding(new Insets(DEFAULT_SPACING * 3));
            collectionBox.setAlignment(Pos.CENTER);
            collectionBox.setMinSize(COLLECTION_WIDTH, SCENE_HEIGHT);
            collectionBox.setMaxSize(COLLECTION_WIDTH, SCENE_HEIGHT);

            HBox searchBox = new CollectionSearchBox();

            DefaultLabel heroesLabel = new DefaultLabel("HEROES", TITLE_FONT, Color.WHITE);
            CollectionCardsGrid heroesGrid = new CollectionCardsGrid(showingCards.getHeroes());

            DefaultLabel minionsLabel = new DefaultLabel("MINIONS", TITLE_FONT, Color.WHITE);
            CollectionCardsGrid minionsGrid = new CollectionCardsGrid(showingCards.getMinions());

            DefaultLabel spellsLabel = new DefaultLabel("SPELLS", TITLE_FONT, Color.WHITE);
            CollectionCardsGrid spellsGrid = new CollectionCardsGrid(showingCards.getSpells());

            DefaultLabel itemsLabel = new DefaultLabel("ITEMS", TITLE_FONT, Color.WHITE);
            CollectionCardsGrid itemsGrid = new CollectionCardsGrid(showingCards.getItems());

            cardsBox = new VBox(DEFAULT_SPACING * 4,
                    heroesLabel, heroesGrid, minionsLabel, minionsGrid, spellsLabel, spellsGrid, itemsLabel, itemsGrid
            );
            cardsBox.setMinSize(COLLECTION_WIDTH * 0.95, SCROLL_HEIGHT * 0.95);
            cardsBox.setAlignment(Pos.TOP_CENTER);

            ScrollPane cardsScroll = new ScrollPane(cardsBox);
            cardsScroll.setMinSize(COLLECTION_WIDTH, SCROLL_HEIGHT);
            cardsScroll.setMaxWidth(COLLECTION_WIDTH);
            cardsScroll.setId("background_transparent");

            collectionBox.getChildren().addAll(searchBox, cardsScroll);

            collectionPane.getChildren().addAll(collectionBox, decksPane);

            AnchorPane sceneContents = new AnchorPane(background, collectionPane, backButton);

            root.getChildren().addAll(sceneContents);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void showNewDeckDialog() {
        DialogText name = new DialogText("Please enter deck's name");
        NormalField nameField = new NormalField("deck name");
        DialogBox dialogBox = new DialogBox(name, nameField);
        DialogContainer dialogContainer = new DialogContainer(root, dialogBox);

        dialogBox.makeButton("CREATE", buttonEvent -> {
            if (nameField.getText().equals("")) return;
            CollectionMenuController.getInstance().newDeck(nameField.getText());
            dialogContainer.close();
        });

        dialogContainer.show(root);
        makeDialogClosable(dialogBox, dialogContainer);
    }

    private void makeDialogClosable(DialogBox dialogBox, DialogContainer dialogContainer) {
        AtomicBoolean shouldBeClosed = new AtomicBoolean(true);
        dialogContainer.makeClosable(shouldBeClosed);
        dialogBox.preventClosingOnClick(shouldBeClosed);
    }

    private void setCollectionCards() {
        CollectionMenuController.getInstance().addPropertyChangeListener(this);
        showingCards = CollectionMenuController.getInstance().getCurrentShowingCards();
    }

    @Override
    public void show() {
        super.show();
        Client.getInstance().getAccount().addPropertyChangeListener(this);
        BackgroundMaker.makeMenuBackgroundFrozen();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("search_result")) {
            showingCards = (Collection) evt.getNewValue();
            try {
                cardsBox.getChildren().set(1, new CollectionCardsGrid(showingCards.getHeroes()));
                cardsBox.getChildren().set(3, new CollectionCardsGrid(showingCards.getMinions()));
                cardsBox.getChildren().set(5, new CollectionCardsGrid(showingCards.getSpells()));
                cardsBox.getChildren().set(7, new CollectionCardsGrid(showingCards.getItems()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
