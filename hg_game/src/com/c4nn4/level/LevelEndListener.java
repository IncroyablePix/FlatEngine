package com.c4nn4.level;

public interface LevelEndListener {
    void onGameOver();

    void onLevelFinishes(int level);

    void subscribe(HigherGroundsLevel level);
}
