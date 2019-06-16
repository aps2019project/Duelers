package models;

import models.card.CardType;

public interface ICard {
    CardType getType();

    String getName();

    int getDefaultAp();

    int getDefaultHp();

    int getPrice();

    String getDescription();
}
