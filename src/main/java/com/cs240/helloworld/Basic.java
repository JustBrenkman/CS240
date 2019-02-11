package com.cs240.helloworld;

import java.util.Date;

public class Basic {
    int random;

    public void func() {
    }

    public int getRandom() {
        return random;
    }

    public void setRandom(int random) {
        this.random = random;
    }

    enum TYPE {ODD, EVEN};

    boolean rand(int a, int b) {
        if (a == b) {
            return true;
        } else {
           return false;
        }
    }

    void anotherFunction(TYPE type) {
        // fori
        // sout
        // switch

        for (int i = 0; i < 10; i++) {

        }

        System.out.println();

        switch (type) {
            case ODD:
                break;
            case EVEN:
                break;
        }

    }
}
