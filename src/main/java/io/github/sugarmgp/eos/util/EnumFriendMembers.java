package io.github.sugarmgp.eos.util;

import java.util.Random;

public enum EnumFriendMembers {

    SugarMGP(false),
    KitraMGP(false),
    chihuoQwQ(false),
    jujixiguan(false),
    SEALchanPS(false),
    Tank1014(false),
    Wubaozi123(false),
    xtexChooser(false);

    private final boolean smallArm;
    private final String id;

    EnumFriendMembers(boolean smallArmIn) {
        this.smallArm = smallArmIn;
        this.id = this.name().toLowerCase();
    }

    public static EnumFriendMembers getByKey(int key) {
        return EnumFriendMembers.values()[key];
    }

    public static int randomGetKey() {
        Random rand = new Random();
        int num = rand.nextInt(EnumFriendMembers.values().length);
        return num;
    }

    public boolean getSmallArm() {
        return this.smallArm;
    }

    public int getKey() {
        return this.ordinal();
    }

    public String getId() {
        return this.id;
    }
}
