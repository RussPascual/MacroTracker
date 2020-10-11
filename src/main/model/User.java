package model;

import java.util.Date;
import java.util.List;

public class User {

    private static final int LOSE_WEIGHT = 0;
    private static final int MAINTAIN_WEIGHT = 1;
    private static final int GAIN_MUSCLE = 2;

    private String name;
    private double weight; // in kilograms
    private int goal; // 0 (lose weight), 1 (maintain weight), 2 (gain muscle)
    private Macros macrosNeeded;
    private Journal journal;
    private Favourites saved;

}
