package io.github.sugarmgp.eos.util;

import io.github.sugarmgp.eos.handler.ItemHandler;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;

import java.util.Random;

public enum EnumNPCRank {
    A(25, 28.0D, 2.0D, 0.8D, ParticleTypes.FLAME, 2, Items.NETHERITE_SWORD, Items.NETHERITE_BOOTS),
    B(20, 24.0D, 1.5D, 0.75D, ParticleTypes.INSTANT_EFFECT, 1, ItemHandler.itemCuckooSword.get(), ItemHandler.itemFunnyBoots.get()),
    C(15, 20.0D, 1.0D, 0.7D, null, 0, Items.STONE_SWORD, Items.CHAINMAIL_BOOTS);

    private final int experienceValue;
    private final double maxHealth;
    private final double attackDamage;
    private final double movementSpeed;
    private final BasicParticleType particleType;
    private final int regenerationLevel;
    private final Item hand;
    private final Item feet;

    EnumNPCRank(int experienceValue, double maxHealth, double attackDamage, double movementSpeed, BasicParticleType particleType, int regenerationLevel, Item hand, Item feet) {
        this.experienceValue = experienceValue;
        this.maxHealth = maxHealth;
        this.attackDamage = attackDamage;
        this.movementSpeed = movementSpeed;
        this.particleType = particleType;
        this.regenerationLevel = regenerationLevel;
        this.hand = hand;
        this.feet = feet;
    }

    public static EnumNPCRank getByKey(int key) {
        return EnumNPCRank.values()[key];
    }

    public static EnumNPCRank randomGet() {
        int a = new Random().nextInt(100);
        if (a < 10) {
            return EnumNPCRank.A;
        } else if (a < 30) {
            return EnumNPCRank.B;
        } else {
            return EnumNPCRank.C;
        }
    }

    public int getKey() {
        return this.ordinal();
    }

    public int getExperienceValue() {
        return this.experienceValue;
    }

    public double getAttackDamage() {
        return this.attackDamage;
    }

    public double getMovementSpeed() {
        return this.movementSpeed;
    }

    public double getMaxHealth() {
        return this.maxHealth;
    }

    public BasicParticleType getParticleType() {
        return this.particleType;
    }

    public int getRegenerationLevel() {
        return this.regenerationLevel;
    }

    public Item getHand() {
        return this.hand;
    }

    public Item getFeet() {
        return this.feet;
    }

}
