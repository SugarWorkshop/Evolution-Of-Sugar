package io.github.sugarmgp.eos.util;

import java.util.Random;

public enum EnumFriendMembers {
    SugarMGP,
    KitraMGP,
    chihuoQwQ,
    jujixiguan(true),
    SEALchanPS(true),
    Tank1014,
    Wubaozi123,
    xtexChooser,
    CalciumSilicate(true);

    private final boolean slim;

    EnumFriendMembers() {
        this.slim = false;
    }

    EnumFriendMembers(boolean slimIn) {
        this.slim = slimIn;
    }

    public static EnumFriendMembers getByKey(int key) {
        return EnumFriendMembers.values()[key];
    }

    public static int randomGetKey(Random rand) {
        int num = rand.nextInt(EnumFriendMembers.values().length);
        return num;
    }

    public int getKey() {
        return this.ordinal();
    }

    public String getId() {
        return this.name().toLowerCase();
    }

    public boolean getSlim() {
        return this.slim;
    }
}
