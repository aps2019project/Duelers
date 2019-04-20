package cardMaker;

import com.google.gson.Gson;
import server.models.card.AttackType;
import server.models.card.Card;
import server.models.card.CardType;
import server.models.card.spell.*;
import server.models.map.Position;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CardMaker {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            Card card = makeNewCard();
            if (card == null) return;
            writeAJsonFile(card);
        }
    }

    private static Card makeNewCard() {
        System.out.println("name:");
        String name = scanner.nextLine();

        if (name.matches("exit")) return null;

        System.out.println("description:");
        String description = scanner.nextLine();

        System.out.println("card type: " +
                "(" +
                "0.HERO, " +
                "1.MINION, " +
                "2.SPELL, " +
                "3.FLAG, " +
                "4.USABLE_ITEM, " +
                "5.COLLECTIBLE_ITEM" +
                ")");
        CardType cardType = CardType.values()[Integer.parseInt(scanner.nextLine())];

        AttackType attackType = null;
        if (cardType == CardType.HERO || cardType == CardType.MINION) {
            System.out.println("attack type: (MELEE, RANGED, HYBRID)");
            attackType = AttackType.valueOf(scanner.nextLine());
        }

        System.out.println("number Of spells:");
        Spell[] spells = new Spell[Integer.parseInt(scanner.nextLine())];
        makeSpells(spells);

        //System.out.println("defaultAp:");
        int defaultAp = 0;//Integer.parseInt(scanner.nextLine());

        //System.out.println("defaultHp:");
        int defaultHp = 0;//Integer.parseInt(scanner.nextLine());

        //System.out.println("mannaPoint:");
        int mannaPoint = 0;//Integer.parseInt(scanner.nextLine());

        //System.out.println("range:");
        int range = 0;//Integer.parseInt(scanner.nextLine());

        System.out.println("price:");
        int price = Integer.parseInt(scanner.nextLine());

        //System.out.println("has combo??");
        boolean hasCombo = false;//Boolean.parseBoolean(scanner.nextLine());

        return new Card(name, description, cardType, spells, defaultAp, defaultHp, mannaPoint, price, attackType, range, hasCombo);
    }

    private static void makeSpells(Spell[] spells) {
        for (int i = 0; i < spells.length; i++) {

            spells[i] = makeNewSpell();
        }
    }

    private static Spell makeNewSpell() {
        System.out.println("spell id:");
        String id = scanner.nextLine();

        System.out.println("spell type: " +
                "(0.CELL_EFFECT, " +
                "1.BUFF, " +
                "2.CARD_EFFECT)");
        SpellType spellType = SpellType.values()[Integer.parseInt(scanner.nextLine())];

        System.out.println(
                "spell definite type:\n" +
                        "(" +
                        "0.HOLY_BUFF\n" +
                        "1.POWER_AP_BUFF\n" +
                        "2.POWER_HP_BUFF\n" +
                        "3.POISON_BUFF\n" +
                        "4.WEAKNESS_AP_BUFF\n" +
                        "5.WEAKNESS_HP_BUFF\n" +
                        "6.STUN_BUFF\n" +
                        "7.DISARM_BUFF\n" +
                        "8.POISON_CELL_EFFECT\n" +
                        "9.FIERY_CELL_EFFECT\n" +
                        "10.HOLY_CELL_EFFECT\n" +
                        "11.REMOVE_POSITIVE_BUFF_CARD_EFFECT\n" +
                        "12.REMOVE_NEGATIVE_BUFF_CARD_EFFECT\n" +
                        "13.INCREASE_AP_CARD_EFFECT\n" +
                        "14.INCREASE_HP_CARD_EFFECT\n" +
                        "15.DECREASE_AP_CARD_EFFECT\n" +
                        "16.DECREASE_HP_CARD_EFFECT\n" +
                        "17.SACRIFICE_CARD_EFFECT\n" +
                        "18.NO_DISARM\n" +
                        "19.NO_POISON\n" +
                        "20.NO_BAD_EFFECT\n" +
                        "21.NO_ATTACK_FROM_WEAKER_ONES\n" +
                        "22.ADD_MANNA\n" +
                        "23.ADD_ATTACK_RANGE\n" +
                        "24.KILL_A_MINION\n" +
                        "25.DISABLE_HOLY_BUFF_ON_ATTACK\n" +
                        "26.ADD_SPELL" +
                        ")"
        );
        DefiniteType definiteType = DefiniteType.values()[Integer.parseInt(scanner.nextLine())];

        Target target = makeNewTarget();

        System.out.println(
                "spell availabilityType:\n" +
                        "(" +
                        "0.ON_PUT\n" +
                        "1.PASSIVE\n" +
                        "2.ON_ATTACK\n" +
                        "3.COMBO\n" +
                        "4.ON_DEATH\n" +
                        "5.EVERY_X_TIME\n" +
                        "6.CONTINUOUS\n" +
                        "7.ON_DEFEND\n" +
                        "8.PASSIVE_CONTINUOUS\n" +
                        "9.SPECIAL_POWER\n" +
                        "10.ON_START" +
                        ")"
        );
        AvailabilityType availabilityType = AvailabilityType.values()[Integer.parseInt(scanner.nextLine())];

        System.out.println("spell numberOfChange: ");
        int numberOfChange = Integer.parseInt(scanner.nextLine());

        //System.out.println("spell coolDown: ");
        int coolDown = 0;//Integer.parseInt(scanner.nextLine());

        //System.out.println("spell mannaPoint: ");
        int mannaPoint = 0;//Integer.parseInt(scanner.nextLine());

        int duration = 0;
        if (spellType == SpellType.BUFF || spellType == SpellType.CELL_EFFECT) {
            System.out.println("buff duration: ");
            duration = Integer.parseInt(scanner.nextLine());
        }

        Spell carryingSpell = null;
        if (definiteType == DefiniteType.ADD_SPELL) {
            System.out.println("carrying spell:");
            carryingSpell = makeNewSpell();
        }

        return new Spell(id, spellType, definiteType, target, availabilityType, numberOfChange, coolDown, mannaPoint, duration, carryingSpell);
    }

    private static Target makeNewTarget() {
        System.out.println("is related to card owner position?");
        boolean isRelatedToCardOwnerPosition = parseBoolean(scanner.nextLine());

        System.out.println("is for around own hero?");
        boolean isForAroundOwnHero = parseBoolean(scanner.nextLine());

        System.out.println("dimensions??");
        Position dimensions = new Position(
                Integer.parseInt(scanner.nextLine()), Integer.parseInt(scanner.nextLine())
        );

        System.out.println("is random?");
        boolean isRandom = parseBoolean(scanner.nextLine());

        System.out.println("is for own?");
        boolean own = parseBoolean(scanner.nextLine());

        System.out.println("is for enemy?");
        boolean enemy = parseBoolean(scanner.nextLine());

        Owner owner = new Owner(own, enemy);

        System.out.println("is for cell?");
        boolean cell = parseBoolean(scanner.nextLine());

        System.out.println("is for hero?");
        boolean hero = parseBoolean(scanner.nextLine());

        System.out.println("is for minion?");
        boolean minion = parseBoolean(scanner.nextLine());

        TargetCardType cardType = new TargetCardType(cell, hero, minion);

        System.out.println("is for melee?");
        boolean melee = parseBoolean(scanner.nextLine());

        System.out.println("is for ranged?");
        boolean ranged = parseBoolean(scanner.nextLine());

        System.out.println("is for hybrid?");
        boolean hybrid = parseBoolean(scanner.nextLine());

        CardAttackType attackType = new CardAttackType(melee, ranged, hybrid);

        System.out.println("is for deck cards?");
        boolean isForDeckCards = parseBoolean(scanner.nextLine());

        return new Target(isRelatedToCardOwnerPosition, isForAroundOwnHero, dimensions, isRandom, owner, cardType, attackType, isForDeckCards);
    }

    private static boolean parseBoolean(String num) {
        return Integer.parseInt(num) != 0;
    }

    private static void writeAJsonFile(Card card) {
        String json = new Gson().toJson(card);

        try {
            FileWriter writer = new FileWriter(new File("jsonData/itemCards/", card.getName().replaceAll(" ", "") + ".item.card.json"));
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}