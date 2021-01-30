package be.helmo.level;

public interface LevelEndListener {
    void onGameOver();

    void onLevelFinishes(int level);

    void subscribe(GameLevel level);
}
