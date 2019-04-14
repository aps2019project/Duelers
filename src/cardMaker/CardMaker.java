package cardMaker;

import com.google.gson.Gson;
import models.card.*;
import models.card.spell.*;

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

        System.out.println("defaultAp:");
        int defaultAp = Integer.parseInt(scanner.nextLine());

        System.out.println("defaultHp:");
        int defaultHp = Integer.parseInt(scanner.nextLine());

        System.out.println("mannaPoint:");
        int mannaPoint = Integer.parseInt(scanner.nextLine());

        System.out.println("range:");
        int range = Integer.parseInt(scanner.nextLine());

        System.out.println("price:");
        int price = Integer.parseInt(scanner.nextLine());

        System.out.println("has combo??");
        boolean hasCombo = Boolean.parseBoolean(scanner.nextLine());

        return new Card(name, description, cardType, spells, defaultAp, defaultHp, mannaPoint, price, attackType, range, hasCombo);
    }

    private static void makeSpells(Spell[] spells) {
        for (int i = 0; i < spells.length; i++) {
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
                            "25.DISABLE_HOLY_BUFF_ON_ATTACK" +
                            ")"
            );
            DefiniteType definiteType = DefiniteType.values()[Integer.parseInt(scanner.nextLine())];

            System.out.println(
                    "spell target base:\n" +
                            "(" +
                            "0.SELECTED_CELL\n" +
                            "1.ALL_CELLS\n" +
                            "2.SELECTED_2X2\n" +
                            "3.SELECTED_3X3\n" +
                            "4.CARD_RANGE_2\n" +
                            "5.RANDOM\n" +
                            "6.CARD_3X3\n" +
                            "7.CARD_ROW\n" +
                            "8.HERO_3X3\n" +
                            "9.RANDOM_AROUND_MY_HERO\n" +
                            "10.CARD_ITSELF" +
                            ")"
            );
            TargetBase targetBase = TargetBase.values()[Integer.parseInt(scanner.nextLine())];

            System.out.println(
                    "spell target type:\n" +
                            "(" +
                            "0.CELL\n" +
                            "1.MY_TROOPS\n" +
                            "2.ENEMY_TROOPS\n" +
                            "3.ALL_TROOPS\n" +
                            "4.MY_HERO\n" +
                            "5.ENEMY_HERO\n" +
                            "6.MY_MINION\n" +
                            "7.ENEMY_MINION\n" +
                            "8.RANGED_HYBRID_HERO\n" +
                            "9.RANGED_HYBRID_TROOP" +
                            ")"
            );
            TargetType targetType = TargetType.values()[Integer.parseInt(scanner.nextLine())];

            System.out.println(
                    "spell availabilityType:\n" +
                            "(" +
                            "0.ON_PUT\n" +
                            "1.PASSIVE\n" +
                            "2.ON_ATTACK\n" +
                            "3.COMBO\n" +
                            "4.ON_DEATH\n" +
                            "5.ON_SPAWN\n" +
                            "6.EVERY_X_TIME\n" +
                            "7.CONTINUOUS\n" +
                            "8.ON_DEFEND\n" +
                            "9.PASSIVE_CONTINUOUS\n" +
                            "10.SPECIAL_POWER" +
                            ")"
            );
            AvailabilityType availabilityType = AvailabilityType.values()[Integer.parseInt(scanner.nextLine())];

            System.out.println("spell numberOfChange: ");
            int numberOfChange = Integer.parseInt(scanner.nextLine());

            System.out.println("spell reusePeriod: ");
            int reusePeriod = Integer.parseInt(scanner.nextLine());

            System.out.println("spell mannaPoint: ");
            int mannaPoint = Integer.parseInt(scanner.nextLine());

            int timeOfEffect = 0;
            if (spellType == SpellType.BUFF || spellType == SpellType.CELL_EFFECT) {
                System.out.println("buff timeOfEffect: ");
                timeOfEffect = Integer.parseInt(scanner.nextLine());
            }

            spells[i] = new Spell(id, spellType, definiteType, targetBase, targetType, availabilityType, numberOfChange, reusePeriod, mannaPoint, timeOfEffect);
        }
    }

    private static void writeAJsonFile(Card card) {
        String json = new Gson().toJson(card);

        try {
            FileWriter writer = new FileWriter(new File("jsonData/heroCards/", card.getName().replaceAll(" ","") + ".hero.card.json"));
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}