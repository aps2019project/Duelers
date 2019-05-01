package server.models.message;

import server.models.card.AttackType;
import server.models.card.Card;
import server.models.card.CardType;
import server.models.card.spell.Spell;

import javax.swing.text.html.HTMLDocument;
import java.util.ArrayList;

public class CompressedCard {
    private String name;
    private String description;
    private String cardId;
    private CardType type;
    private CompressedSpell spell;//just for hero
    private int defaultAp;
    private int defaultHp;
    private int mannaPoint;
    private AttackType attackType;
    private int range;
    private boolean hasCombo;


    public CompressedCard(String name, String description, String cardId, CardType type,
                          ArrayList<Spell> spells, int defaultAp, int defaultHp, int mannaPoint,
                          AttackType attackType, int range, boolean hasCombo) {
        this.name = name;
        this.description = description;
        this.cardId = cardId;
        this.type = type;
        this.defaultAp = defaultAp;
        this.defaultHp = defaultHp;
        this.mannaPoint = mannaPoint;
        this.attackType = attackType;
        this.range = range;
        this.hasCombo = hasCombo;
        if (type == CardType.HERO) {
            for (Spell spell : spells) {
                if (spell.getAvailabilityType().isSpecialPower()) {
                    this.spell = spell.toCompressedSpell();
                }
            }
        }
    }
}
