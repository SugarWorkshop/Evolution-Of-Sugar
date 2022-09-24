package io.github.sugarmgp.eos.util;

import java.util.Random;

public enum EnumFriendMembers {

    SugarMGP, KitraMGP, chihuoQwQ, jujixiguan, SEALchanPS, Tank1014, Wubaozi123, xtexChooser;

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
}
