package server.models.card.spell;


public class SpellAction {
    private int enemyHitChanges;
    private int apChange;
    private int hpChange;
    private int mpChange;
    private boolean poison;
    private boolean makeStun;
    private boolean makeDisarm;
    private boolean actionAtTheEndOfTurn;
    private boolean noDisarm;
    private boolean noPoison;
    private boolean noStun;
    private boolean noBadEffect;
    private boolean noAttackFromWeakerOnes;
    private boolean disableHolyBuff;
    private boolean addSpell;
    private boolean killsTarget;
    private boolean isForGladiator;
    private boolean durable;
    private int removeBuffs; // -1 for negative buffs and +1 for positive ones
    private int duration;
    private int delay;
    private Spell carryingSpell;

    public SpellAction(SpellAction action) {
        this.enemyHitChanges = action.enemyHitChanges;
        this.apChange = action.apChange;
        this.hpChange = action.hpChange;
        this.mpChange = action.mpChange;
        this.poison = action.poison;
        this.makeStun = action.makeStun;
        this.makeDisarm = action.makeDisarm;
        this.actionAtTheEndOfTurn = action.actionAtTheEndOfTurn;
        this.noDisarm = action.noDisarm;
        this.noPoison = action.noPoison;
        this.noStun = action.noStun;
        this.noBadEffect = action.noBadEffect;
        this.noAttackFromWeakerOnes = action.noAttackFromWeakerOnes;
        this.disableHolyBuff = action.disableHolyBuff;
        this.addSpell = action.addSpell;
        this.killsTarget = action.killsTarget;
        this.isForGladiator = action.isForGladiator;
        this.durable = action.durable;
        this.removeBuffs = action.removeBuffs;
        this.delay = action.delay;
        if (action.carryingSpell != null)
            this.carryingSpell = new Spell(action.carryingSpell);
        this.duration = action.duration;
    }

    public SpellAction(int enemyHitChanges, int apChange, int hpChange, int mpChange, boolean poison, boolean makeStun,
                       boolean makeDisarm, boolean actionAtTheEndOfTurn, boolean noDisarm, boolean noPoison,
                       boolean noStun, boolean noBadEffect, boolean noAttackFromWeakerOnes, boolean disableHolyBuff,
                       boolean addSpell, boolean killsTarget, boolean isForGladiator, boolean durable, int removeBuffs,
                       int duration, int delay, Spell carryingSpell) {
        this.enemyHitChanges = enemyHitChanges;
        this.apChange = apChange;
        this.hpChange = hpChange;
        this.mpChange = mpChange;
        this.poison = poison;
        this.makeStun = makeStun;
        this.makeDisarm = makeDisarm;
        this.actionAtTheEndOfTurn = actionAtTheEndOfTurn;
        this.noDisarm = noDisarm;
        this.noPoison = noPoison;
        this.noStun = noStun;
        this.noBadEffect = noBadEffect;
        this.noAttackFromWeakerOnes = noAttackFromWeakerOnes;
        this.disableHolyBuff = disableHolyBuff;
        this.addSpell = addSpell;
        this.killsTarget = killsTarget;
        this.isForGladiator = isForGladiator;
        this.durable = durable;
        this.removeBuffs = removeBuffs;
        this.delay = delay;
        this.carryingSpell = carryingSpell;
        if (duration == -1) {
            this.duration = Integer.MAX_VALUE;
            return;
        }
        this.duration = duration;
    }

    public int getEnemyHitChanges() {
        return enemyHitChanges;
    }

    public int getApChange() {
        return apChange;
    }

    public int getHpChange() {
        return hpChange;
    }

    public int getMpChange() {
        return mpChange;
    }

    public boolean isPoison() {
        return poison;
    }

    public boolean isMakeStun() {
        return makeStun;
    }

    public boolean isMakeDisarm() {
        return makeDisarm;
    }

    public boolean isActionAtTheEndOfTurn() {
        return actionAtTheEndOfTurn;
    }

    public boolean isNoDisarm() {
        return noDisarm;
    }

    public boolean isNoPoison() {
        return noPoison;
    }

    public boolean isNoStun() {
        return noStun;
    }

    public boolean isNoBadEffect() {
        return noBadEffect;
    }

    public boolean isNoAttackFromWeakerOnes() {
        return noAttackFromWeakerOnes;
    }

    public boolean isDisableHolyBuff() {
        return disableHolyBuff;
    }

    public boolean isAddSpell() {
        return addSpell;
    }

    public boolean isKillsTarget() {
        return killsTarget;
    }

    public boolean isForGladiator() {
        return isForGladiator;
    }

    public boolean isDurable() {
        return durable;
    }

    public int getRemoveBuffs() {
        return removeBuffs;
    }

    public int getDuration() {
        return duration;
    }

    public int getDelay() {
        return delay;
    }

    public Spell getCarryingSpell() {
        return carryingSpell;
    }
}