package be.helmo.level;

import be.helmo.graphics.hud.HUD;
import be.helmo.graphics.hud.elements.textfield.TextField;
import be.helmo.graphics.render.Renderer;
import be.helmo.graphics.Speed;
import be.helmo.graphics.hud.Observer;
import be.helmo.graphics.overimages.Img;
import be.helmo.graphics.sprites.ActiveSprite;
import be.helmo.graphics.texts.Alignement;
import be.helmo.graphics.texts.Text;
import be.helmo.graphics.texts.TypeText;
import be.helmo.level.entities.Cat;
import be.helmo.level.entities.Pickup;
import be.helmo.level.entities.Player;
import be.helmo.level.entities.Sushi;
import be.helmo.level.entities.controlables.PlayerJump;
import be.helmo.level.entities.particles.Particles;
import be.helmo.level.entities.particles.types.ParticleFabric;
import be.helmo.level.map.MapGenerator;
import be.helmo.level.map.Mapping;
import be.helmo.level.map.RandomGenerator;
import be.helmo.main.GameThread;
import be.helmo.main.screen.Screen;
import be.helmo.manager.audio.AudioManager;
import be.helmo.manager.controls.Controls;
import be.helmo.manager.debug.Debug;
import be.helmo.manager.fonts.Fonts;
import be.helmo.manager.image.PixManager;
import be.helmo.physics.ColParams;
import be.helmo.physics.Collider;
import be.helmo.physics.coords.Coords;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.*;

import static be.helmo.main.screen.Screen.WIN_WIDTH;
import static be.helmo.manager.image.PixManager.TILE_SIZE;

public class GameLevel {
    public static final int     BOARD = WIN_WIDTH / TILE_SIZE,
            BOARD_HEIGHT = 18;

    private final int level;
    private final Mapping map;
    private final HUD hud;

    private final Img closing;
    private byte end;
    private final Text finishText;

    private final Observer observer;
    private LevelEndListener levelEndListener;

    private final AudioManager am = AudioManager.get();

    private final Player player;
    private final Camera camera;
    private final Cat cat;
    private final List<Pickup> pickups;
    private final Observer particles;
    private final Liquid liquid;

    private final PlayerJump pjump;

    //private Layer background;

    public GameLevel(int level) {
        loadImages();
        this.level = level;

        this.camera = new Camera(0, 0);

        am.stop("menu");
        am.unload("menu");

        if(!am.isLoaded("ingame") || !am.isPlaying("ingame")) {
            am.load("/be/helmo/resources/Sound/Music/ambiantlow.mp3", "ingame");
            am.setVolume("ingame", -10);
            am.loop("ingame");
        }

        Debug.log("Creating map...");
        this.map = new Mapping(this, BOARD, level * 4 + 20, camera, new RandomGenerator(level, BOARD));
        Debug.log("Generating map...");

        long now = System.nanoTime();
        this.map.initMap(level + 5, (level < 15 ? 5 - (int) Math.ceil(level / 5.0) : 2), BOARD);

        Debug.log("Map generated in " + (System.nanoTime() - now) / 1000000 + " ms!");

        observer = new Observer();
        hud = new HUD();
        
        /*background = new BackgroundLayer(GameWindow.WIN_WIDTH, GameWindow.WIN_HEIGHT);
        background.pasteImage(PixManager.get().sky(), 0, 0);*/

        player = createPlayer();
        cat = createCat();

        pjump = createPlayerJump();
        setPlayerOnFirstPlatform(this.map.getFirstPlatformMiddlePos());
        setCatOnLastPlatform(this.map.getLastPlatformMiddlePos());

        pickups = new LinkedList<>();
        setSushis();

        particles = new Observer();

        liquid = new Liquid(BOARD, PixManager.WATER_COLOR);
        liquid.toggleWaterMovement(true);

        hud.add(new TextField(20, 715, Alignement.LEFT, false, Debug::log));
        /*hud.add(new Button(Screen.WIN_WIDTH / 2, Screen.WIN_HEIGHT, 300, 50, new Text(WIN_WIDTH / 2, Screen.WIN_HEIGHT, -1, "TEST", Color.RED, Fonts.ORATOR_T)) {
            @Override
            public void selectElement(boolean select) {

            }
        });*/

        //---END

        closing = new Img(0, Screen.WIN_HEIGHT, Screen.WIN_WIDTH, Screen.WIN_HEIGHT, -1, buildClosingImage(), 1.f);

        finishText = new TypeText(Screen.WIN_WIDTH / 2, Screen.WIN_HEIGHT / 2, 150,
                Speed.SLOW, "Niveau " + level + " terminé !", Color.WHITE,
                Fonts.CHAMP_LIMO_S);

        finishText.setAlignement(Alignement.CENTER);

        end = 0;
    }

    private void loadImages() {
        PixManager.get().loadBigImage(PixManager.SKIES, "SKY");
        PixManager.get().loadSpriteSheet(PixManager.SUSHI, "SUSHI", 54, 54);
        PixManager.get().loadSpriteSheet(PixManager.BALL, "BALL", 15, 15);
        PixManager.get().loadSpriteSheet(PixManager.TREE, "TREE", 120, 240);
        PixManager.get().loadSpriteSheet(PixManager.DIRT, "DIRT", 6, 6);
        PixManager.get().loadSpriteSheet(PixManager.STARS, "STARS", 40, 40);
        PixManager.get().loadSpriteSheet(PixManager.PASCAL, "PASCAL", 60, 74);
        PixManager.get().unloadSpriteSheet("TILES");
        PixManager.get().loadSpriteSheet(PixManager.TILES, "TILES", TILE_SIZE, TILE_SIZE);
        PixManager.get().loadSpriteSheet(PixManager.WATER, "LIQUID", TILE_SIZE, TILE_SIZE);
        PixManager.get().loadSpriteSheet(PixManager.CAT, "CAT", TILE_SIZE, TILE_SIZE);
        PixManager.get().loadSpriteSheet(PixManager.PLAYER, "PLAYER", TILE_SIZE, TILE_SIZE);
    }

    public Collider getCollider() {
        return map.getCollider();
    }

    public Camera getCamera() {
        return this.camera;
    }

    private void setPlayerOnFirstPlatform(Coords coords) {
        player.setPos(coords.getX(), coords.getY() + 1.0);
    }

    private void setCatOnLastPlatform(Coords coords) {
        cat.setPos(coords.getX(), coords.getY() + 1);
    }

    private Player createPlayer() {
        return new Player(this,
                new ActiveSprite(0.0, 0.0, 10,
                        PixManager.get().getSprites("PLAYER", 0, 2)),
                new ActiveSprite(0.0, 0.0, 10,
                        PixManager.get().getSprites("PLAYER", 1, 3)),
                0, 0);
    }

    private Cat createCat() {
        Cat cat = new Cat(this,
                new ActiveSprite(0.0, 0.0, 25,
                        PixManager.get().getSprites("CAT", 0, 2)),

                new ActiveSprite(0.0, 0.0, 25,
                        PixManager.get().getSprites("CAT", 1, 3)),
                0, 0);

        cat.setEntityAttachement(player);
        return cat;
    }

    private void setSushis() {
        Coords[] coords = map.getEmptyRandomPlatforms(level + 2);

        BufferedImage sushi = PixManager.get().getSprite("SUSHI", 0);//.sushi();

        for(Coords coord : coords) {
            double x = coord.getX();
            double y = coord.getY() + 1.0;
            pickups.add(new Sushi(this, new ActiveSprite(x, y, 1, sushi), x, y));
        }
    }

    private PlayerJump createPlayerJump() {
        PlayerJump pjump = new PlayerJump(player, 15.0 + (level * 0.5));
        Controls.get().addListener(pjump);

        return pjump;
    }

    public int getLevel() {
        return level;
    }

    public void update() {
        hud.update();

        updateScrolling();

        if (this.map != null)
            this.map.update();

        updateSushis();

        if (this.cat != null)
            this.cat.update(map.getCollider());

        updatePlayer();

        if (liquid != null)
            liquid.update();

        if(particles != null)
            particles.update();

        if (observer != null)
            observer.update();

        handleEnd();
    }

    private void updateSushis() {
        Set<Pickup> toDelete = new HashSet<>();

        if (this.pickups != null) {
            for(Pickup pickup : this.pickups) {
                if (pickup != null) {
                    pickup.update(map.getCollider());
                    if (pickup.isInRangeOf(player, 1.0)) {
                        toDelete.add(pickup);
                        onPlayerPickUpPickup(player, pickup);
                    }
                }
            }

            this.pickups.removeAll(toDelete);
        }
    }

    public void draw(final Renderer renderer) {
        renderer.drawImage(PixManager.get().getBigImage("SKY"), 0, Screen.WIN_HEIGHT, Screen.WIN_WIDTH, Screen.WIN_HEIGHT, 1.f);
        //background.draw(drawer, null);

        if (this.map != null)
            map.draw(renderer);

        if (this.pickups != null) {
            try {
                for (Pickup pickup : this.pickups) {
                    if (pickup != null)
                        pickup.draw(renderer, camera);
                }
            }
            catch(ConcurrentModificationException e) {

            }
        }

        if (cat != null)
            cat.draw(renderer, camera);

        if (player != null)
            player.draw(renderer, camera);

        if(particles != null)
            particles.draw(renderer);

        liquid.draw(renderer, camera);

        if (observer != null)
            observer.draw(renderer);

        hud.draw(renderer);
    }

    public int getMinBorder() {
        return 0;
    }

    public int getMaxBorder() {
        return BOARD - 1;
    }

    public Platform getGroundPos(double x, double y) {
        return map.getGroundPos(x, y);
    }

    private void finish() {
        observer.add(closing);

        closing.setPos(0, Screen.WIN_HEIGHT * 2);
        closing.setVelocity(0, -15.0);

        am.load("/be/helmo/resources/Sound/SFX/cat" + MapGenerator.randomEx(1, 6) + ".wav", "cat");
        am.setVolume("cat", 0);
        am.play("cat");

        end = 1;
    }

    public void addScrolling(double x, double y) {
        map.addScrolling(x, y);
    }

    public void addParticles(Particles particles) {
        if(particles != null) {
            this.particles.add(particles);
        }
    }

    public void onPlayerReachesPlatform(final Player player, final Platform platform) {
        if (platform != null) {
            //ParticleFabric.createParticles(ParticleFabric.ParticleType.DIRT_PARTICLE, player.getX(), player.getY(), 10, 300,false, this);
            ParticleFabric.createParticles(ParticleFabric.ParticleType.DIRT_PARTICLE, player.getX(), player.getY(), 10, 300,false, this);
            //observer.add(new FlashImage(Speed.MEDIUM, FlashImage.FlashColor.RED));
            //addParticles(new Particles(this, 10, 300, player.getX(), player.getY()));
            /*am.load("/be/helmo/resources/Sound/SFX/pascal.mp3", "pascal", true);
            am.setVolume("pascal", 0);
            am.play("pascal");*/

            //map.toggleWaterMovement(true);
            if (Platform.isPlatformWater(platform)) {
                onPlayerFallsInWater(player);
            }
            else if (map.isLastPlatform(platform)) {
                Debug.log("Player reaches last platform");

                int sushis = getPickups();
                if (sushis > 0) {
                    observer.add(new TypeText(50, Screen.WIN_HEIGHT / 2, 150,
                            Speed.MEDIUM, "Il vous reste " + sushis + " sushi" + (sushis == 1 ? "" : "s") + " à récupérer !", Color.WHITE,
                            Fonts.CHAMP_LIMO_S));
                }
                else {
                    player.setFrozen(true);
                    liquid.toggleWaterMovement(false);
                    finish();
                }
            }
            else {
                //Debug.log("Player reaches platform on " + platform.getY());
            }
        }
    }

    public void onPlayerJump(Player player) {
        //map.toggleWaterMovement(false);
        //map.increaseWaterVel(2.0);
    }

    private void onPlayerPickUpPickup(Player player, Pickup pickup) {
        ParticleFabric.createParticles(ParticleFabric.ParticleType.STAR_PARTICLE, pickup.getX(), pickup.getY(), 5, 90, false, this);
        Debug.log("Pickup picked up!");
        //water.increaseWaterVel(-0.25);
    }

    public void onPlayerFallsInWater(Player player) {
        if(this.player.isAlive()) {
            Debug.log("Player fell into water");

            AudioManager.get().play("drawn");
            //player.setFrozen(true);
            player.setAlive(false);
            pjump.pause(true);
            player.setColParams(ColParams.NO_COL);

            liquid.toggleWaterMovement(true);
        }
    }

    public int getPickups() {
        return pickups.size();
    }

    public double getWaterLevel() {
        return liquid.getWaterLevel();
    }

    public void increaseWaterVel(double vel) {
        liquid.increaseWaterVel(vel);
    }

    private BufferedImage buildClosingImage() {
        BufferedImage end = new BufferedImage(Screen.WIN_WIDTH, Screen.WIN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < Screen.WIN_WIDTH; x++)
            for (int y = 0; y < Screen.WIN_HEIGHT; y++)
                end.setRGB(x, y, 0xFF000000);

        return end;
    }

    private void handleEnd() {
        //Debug.log("handleEnd - closing : " + closing.getX() + " " + closing.getY());// + " - " + closing.getXVel() + " " + closing.getYVel());

        if (end == 1 && !closing.isMoving()) {
            finishText.resetStartingTick();
            observer.add(finishText);
            end++;
        }
        else if (end == 2 && !observer.contains(finishText)) {
            if (levelEndListener != null)
                levelEndListener.onLevelFinishes(level);

            terminate();
        }
    }

    public void subscribe(LevelEndListener lel) {
        if (lel != null)
            levelEndListener = lel;
    }

    public void gameOver() {
        if (levelEndListener != null) {
            levelEndListener.onGameOver();
        }

        terminate();
    }

    private void updatePlayer() {
        if (this.player != null) {
            if (!this.player.isAlive())
                player.setMovingVector(0, -0.3);

            this.player.update(map.getCollider());

            if (this.player.getY() < getWaterLevel() - 1.0)
                gameOver();
        }
    }

    private void updateScrolling() {
        double xScrolling = 0, yScrolling = 0;
        double height = GameLevel.BOARD_HEIGHT;
        double width = GameLevel.BOARD;

        double scrollRight = (width / 2) + (width / 8);
        double scrollLeft = (width / 2) - (width / 8);

        double scrollHigh = (height / 2) + (height / 8);
        double scrollLow = (height / 2) - (height / 4);

        double xPlayer = player.getX() - camera.getX();
        double yPlayer = player.getY() - camera.getY();

        if (xPlayer < scrollLeft) {
            double xVel = player.getVelX();
            xScrolling = xVel < -0.1 ? xVel * 0.8 : -0.1;
        }
        else if (xPlayer > scrollRight) {
            xScrolling = 0.2;
        }

        if (yPlayer < scrollLow) {
            double yVel = player.getVelY();
            yScrolling = yVel < -0.1 ? yVel * 0.8 : -0.1;
        }
        else if (yPlayer > scrollHigh) {
            yScrolling = 0.2;
        }

        xScrolling *= GameThread.actionFactor * 30;
        yScrolling *= GameThread.actionFactor * 30;

        camera.increment(xScrolling, yScrolling);
    }

    private void terminate() {
        hud.terminate();
    }
}
