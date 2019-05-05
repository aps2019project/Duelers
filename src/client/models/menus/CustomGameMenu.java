package client.models.menus;

import client.Client;
import client.models.card.Deck;
import client.models.game.GameType;
import client.models.message.Message;
import client.view.View;
import client.view.request.InputException;

public class CustomGameMenu extends Menu {
    private static CustomGameMenu ourInstance;
    private String help;

    private CustomGameMenu() {
    }

    public static CustomGameMenu getInstance() {
        if (ourInstance == null) {
            ourInstance = new CustomGameMenu();
        }
        return ourInstance;
    }

    public void startGame(String deckName, int mode, int numberOfFlags, Client client, String serverName) throws InputException {
        client.addToSendingMessages(
                Message.makeNewCustomGameMessage(
                        client.getClientName(), serverName, GameType.values()[mode - 1], numberOfFlags, deckName, 0
                )
        );
        client.sendMessages();

        if (!client.getValidation()) {
            throw new InputException(client.getErrorMessage());
        }
    }

    @Override
    public void exit(Client client) {
        client.setCurrentMenu(SinglePlayerMenu.getInstance());
    }

    @Override
    public void showHelp() {
        View.getInstance().showHelp(help);
    }

    public void setHelp(Client client) {
        StringBuilder help = new StringBuilder(
                "Custom Game:\n" +
                        "valid decks:\n"
        );
        for (Deck deck : client.getAccount().getDecks()) {
            if (deck.isValid()) {
                help.append(deck.getName()).append(" - Hero name: ")
                        .append(deck.getHero().getName()).append("\n");
            }
        }
        help.append("game modes:\n").append("1. kill hero\n").append("2. one flag\n").append("3. multi flag\n")
                .append("\"start game [deck name] [mode(number)] [number of flags(if needed)]\"\n")
                .append("\"exit\"");
        this.help = help.toString();
    }
}
