package com.choisong.flyon.member.domain;

import java.util.Arrays;
import java.util.Comparator;

public enum GliderBadge {
    Baekdu("백두산", 2744),
    Halla("한라산", 1947),
    Seorak("설악산", 1708),
    Taebaek("태백산", 1567),
    Chiak("치악산", 1288),
    Mudeung("무등산", 1187),
    Bukhansan("북한산", 836),
    Inwang("인왕산", 338),
    Gwanak("관악산", 632),
    Namsan("남산", 262);

    private final String name;
    private final Integer height;

    GliderBadge(final String name, final Integer height) {
        this.name = name;
        this.height = height;
    }

    public static GliderBadge fromAltitude(int meters) {
        GliderBadge lowest = Arrays.stream(values())
            .min(Comparator.comparingInt(GliderBadge::getHeight))
            .orElse(Namsan);

        return Arrays.stream(values())
            .filter(b -> meters >= b.getHeight())
            .max(Comparator.comparingInt(GliderBadge::getHeight))
            .orElse(lowest);
    }

    public String getDisplayName() {
        return name;
    }

    public int getHeight() {
        return height;
    }
}
