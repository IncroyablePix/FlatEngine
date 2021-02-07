package be.helmo.physics;

import be.helmo.level.Platform;
import be.helmo.level.Tile;
import be.helmo.level.TileMap;
import be.helmo.level.entities.Pickup;
import be.helmo.level.entities.Player;
import be.helmo.manager.debug.Debug;

public class TileCollider implements Collider {
    private final TileMap tileMap;

    public TileCollider(TileMap tileMap) {
        this.tileMap = tileMap;
    }

    public PosAndVel checkX(Physical physical, ColParams colParams) {
        final double eX = physical.getX(), eY = physical.getY(),
                vX = physical.getVelX(), vY = physical.getVelY();

        double x, xVel;
        double xToCheck;

        if(colParams.isNoCol())
            return new PosAndVel(eX, vX);

        if (vX > 0)
            xToCheck = eX + physical.getSizeX();
        else if (vX < 0)
            xToCheck = eX;
        else
            return new PosAndVel(eX, vX);

        x = eX;
        xVel = vX;

        Tile[] matches = this.tileMap.searchByRange(
                xToCheck, xToCheck, eY, eY + physical.getSizeY());

        for (Tile match : matches) {
            ColParams col = match.getColParams();

            if (match.isNoCol())
                continue;

            if (xVel > 0 && col.isRight() && colParams.isRight()) {
                if (x + physical.getSizeX() > Math.floor(match.getX())) {
                    x = Math.floor(match.getX()) - physical.getSizeX();
                    xVel = 0;
                    physical.onCollision(new Collision(match,
                            Collision.CollisionDirection.RIGHT));
                    break;
                }
            }
            else if (xVel < 0 && col.isLeft() && colParams.isLeft()) {
                if (x < Math.ceil(match.getX())) {
                    x = Math.ceil(match.getX());
                    xVel = 0;
                    physical.onCollision(new Collision(match,
                            Collision.CollisionDirection.LEFT));
                    break;
                }
            }
        }

        return new PosAndVel(x, xVel);
    }

    public PosAndVel checkY(Physical physical, ColParams colParams) {
        final double eX = physical.getX(), eY = physical.getY(),
                vX = physical.getVelX(), vY = physical.getVelY();

        double y, yVel;
        double yToCheck;

        if(colParams.isNoCol())
            return new PosAndVel(eY, vY);

        if (vY > 0)
            yToCheck = eY;// + physical.getSizeY();
        else if (vY < 0)
            yToCheck = eY;
        else
            return new PosAndVel(eY, vY);

        y = eY;
        yVel = vY;

        Tile[] matches = this.tileMap.searchByRange(
                eX + (physical.getSizeX() / 4), eX + (physical.getSizeX() / 2), yToCheck, yToCheck);

        for (Tile match : matches) {
            ColParams col = match.getColParams();

            if (match.isNoCol())
                continue;

            if (yVel > 0 && col.isTop() && colParams.isTop()) {
                if (y + physical.getSizeY() > match.getY()) {
                    y = match.getY() - physical.getSizeY();
                    yVel = 0;
                    physical.onCollision(new Collision(match,
                            Collision.CollisionDirection.TOP));
                    break;
                }
            }
            else if (yVel < 0 && col.isBottom() && colParams.isBottom()) {
                /*if(camera.getY() + camera.getHeight() + getSizeY() >= getY() &&
                    getY() >= camera.getY() - getSizeY())*/
                if (y < match.getY() + match.getSizeY()) {

                    y = match.getY() + match.getSizeY();

                    if(vY > -15.0)
                        yVel = 0;
                    else
                        yVel = -vY / physical.getBounciness();

                    physical.onCollision(new Collision(match,
                            Collision.CollisionDirection.BOTTOM));
                    break;
                }
            }
        }

        return new PosAndVel(y, yVel);
    }

    @Override
    public boolean isOnTopOfSomething(Physical physical) {
        final double eX = physical.getX(), eY = physical.getY();

        Tile[] matches = this.tileMap.searchByRange(
                eX + (physical.getSizeX() / 4), eX + (physical.getSizeX() / 2), eY - physical.getSizeY(), eY);

        for (Tile match : matches) {
            ColParams col = match.getColParams();

            if (col.isBottom() &&
                    eY <= (match.getY()) + match.getSizeY())
                return true;
        }

        return false;
    }
}
