package be.helmo.game;

import be.helmo.enums.GameMenus;
import be.helmo.enums.TileTypes;
import be.helmo.graphics.Renderer;
import be.helmo.graphics.sprites.ActiveSprite;
import be.helmo.level.map.Mapping;
import be.helmo.manager.controls.Clickable;
import be.helmo.manager.controls.CursorObserver;
import be.helmo.manager.debug.Debug;
import be.helmo.manager.GameStateManager;
import be.helmo.manager.image.PixManager;

/**
 * GameDebug
 * <p>
 * Just a place for tests.
 *
 * @author IncroyablePix
 */
public class GameDebug extends GameState {

    private int ticks;

    private Mapping map;

    private Clickable[] tiles;
    private CursorObserver co;

    private ActiveSprite[] activeSprites;

    public GameDebug(GameStateManager gsm) {
        super(gsm);
    }

    /**
     * The initialization phase for this GameState
     * <p>
     * It does anything specific I needed to test and figure out
     *
     * @author IncroyablePix
     */
    @Override
    public void init() {
        Debug.log("GameDebug init");

        /*Debug.log("Creating map...");
        //this.map = new Mapping(BOARD, 1 * 4 + 40);
        Debug.log("Generating map...");

        this.map.initMap(1 + 5, 5, BOARD, 1);*/

        activeSprites = new ActiveSprite[16];

        TileTypes[] types = new TileTypes[]{
                TileTypes.TOP_LEFT,
                TileTypes.TOP_MIDDLE_1,
                TileTypes.TOP_MIDDLE_2,
                TileTypes.TOP_RIGHT,

                TileTypes.TOP_D_LEFT,
                TileTypes.TOP_D_MIDDLE_1,
                TileTypes.TOP_D_MIDDLE_2,
                TileTypes.TOP_D_RIGHT,

                TileTypes.DIRT_LEFT_1,
                TileTypes.DIRT_MIDDLE_1,
                TileTypes.DIRT_MIDDLE_2,
                TileTypes.DIRT_RIGHT_1,

                TileTypes.DIRT_LEFT_2,
                TileTypes.DIRT_MIDDLE_3,
                TileTypes.DIRT_MIDDLE_4,
                TileTypes.DIRT_RIGHT_2
        };

        for (int i = 0; i < 16; i++) {
            activeSprites[i] = new ActiveSprite(i, i, 1, PixManager.get().getTile(types[i]).getImage());
        }


        this.co = new CursorObserver();
    }

    /**
     * Updates whatever needs to be updated (Map, TileMap, Observers, etc.)
     *
     * @author IncroyablePix
     */
    @Override
    public void update() {
        ticks++;
        //gsm.setState(GameStates.MENU);
        //gsm.setMenuState(GameMenus.MAIN_MENU);
    }

    /**
     * Draws anything you might have to draw.
     *
     * @param renderer The drawer to use
     * @author IncroyablePix
     */
    @Override
    public void draw(final Renderer renderer) {
        for (int i = 0; i < 16; i++)
            activeSprites[i].draw(renderer, null);
    }

    @Override
    public void setState(GameMenus menu) {
    }

    @Override
    public void terminate() {

    }

}