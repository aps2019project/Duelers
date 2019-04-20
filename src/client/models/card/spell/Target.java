package client.models.card.spell;

import server.models.map.Position;

import java.util.Scanner;

public class Target {
    private boolean isRelatedToCardOwnerPosition;
    private boolean isForAroundOwnHero;
    private boolean isRandom;
    private boolean isForDeckCards;
    private Position dimensions;
    private Owner owner;
    private TargetCardType cardType;
    private CardAttackType attackType;

    public Target(boolean isRelatedToCardOwnerPosition, boolean isForAroundOwnHero, Position dimensions, boolean isRandom, Owner owner, TargetCardType cardType, CardAttackType attackType, boolean isForDeckCards) {
        this.isRelatedToCardOwnerPosition = isRelatedToCardOwnerPosition;
        this.isForAroundOwnHero = isForAroundOwnHero;
        this.dimensions = dimensions;
        this.isRandom = isRandom;
        this.owner = owner;
        this.cardType = cardType;
        this.attackType = attackType;
        this.isForDeckCards = isForDeckCards;
    }



    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {

        }
    }
}
