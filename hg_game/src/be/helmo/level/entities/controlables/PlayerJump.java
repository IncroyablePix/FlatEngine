package be.helmo.level.entities.controlables;

import be.helmo.level.entities.Directions;
import be.helmo.level.entities.Player;
import be.helmo.manager.controls.ControlListener;
import be.helmo.manager.controls.Controls;
import be.helmo.manager.debug.Debug;
import be.helmo.manager.debug.DebugInfo;

public class PlayerJump implements ControlListener {

    private final Player player;
    private double maxVel;
    private boolean paused = false;

    public PlayerJump(final Player player, final double maxVel) {
        this.maxVel = Math.abs(maxVel);
        this.player = player;
    }

    @Override
    public void onKeyInputChanged(int down, int pressed, int released) {
        if (!player.isFrozen()) {
            double xVel = player.getVelX(), yVel = player.getVelY();

            if ((down & Controls.LEFT) == Controls.LEFT) {
                player.setDirection(Directions.RIGHT);
                xVel = Math.max(player.getVelX() - 0.025, -5.0);
            }
            else if ((down & Controls.RIGHT) == Controls.RIGHT) {
                player.setDirection(Directions.LEFT);
                xVel = Math.min(player.getVelX() + 0.025, 5.0);
            }
            else/* if ((down & Controls.LEFT) != Controls.LEFT && (down & Controls.RIGHT) != Controls.RIGHT)*/ {
                if (player.isMoving()) {
                    xVel = Math.max(Math.abs(player.getVelX()) - 0.1, 0.0) *
                            (player.getDirection() == Directions.LEFT ? -1 : 1);
                }
            }

            if ((pressed & Controls.JUMP) == Controls.JUMP &&
                    !player.isFalling() &&
                    !player.hasJumped()) {
                player.setJumped(false);
                yVel = 1.0;
                //gl.onPlayerJump();
            }
            else if ((down & Controls.JUMP) == Controls.JUMP &&
                    !player.isFalling() &&
                    !player.hasJumped()) {
                yVel = Math.min(player.getVelY() + 0.005, maxVel);

                if (yVel >= maxVel)
                    player.setJumped(true);
            }
            else if((released & Controls.JUMP) == Controls.JUMP && player.hasJumped()) {
                player.setJumped(true);
            }

            player.setMovingVector(xVel, yVel);
            Debug.getinfo().setInfo(DebugInfo.DebugInfos.VelocityX, new Object[]{xVel});
            Debug.getinfo().setInfo(DebugInfo.DebugInfos.VelocityY, new Object[]{yVel});
        }
    }

    @Override
    public boolean isPaused() {
        return paused;
    }

    @Override
    public void pause(boolean paused) {
        this.paused = paused;
    }
}
