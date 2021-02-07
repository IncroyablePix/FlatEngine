package be.helmo.manager.debug;

import be.helmo.graphics.render.Renderer;
import be.helmo.graphics.texts.Text;
import be.helmo.manager.fonts.Fonts;

import java.awt.*;

public class DebugInfo {

    private static final byte MAX_SIZE = 6;
    private static final int BASE_POSITION = 30;// - (MAX_SIZE * 22);

    private final Text[] info;
    private final byte cursor;

    private double pX;
    private double pY;
    private byte strength;
    private double xVel;
    private double yVel;
    private double waterIncrease;

    public DebugInfo() {
        info = new Text[MAX_SIZE];

        for (byte i = 0; i < MAX_SIZE; i++) {
            info[i] = new Text(20, BASE_POSITION + (i * 22), -1, " ", Color.BLACK, Fonts.COURIER_T);
        }

        cursor = 0;

        pX = 0.0;
        pY = 0.0;

        strength = 0;

        pX = 0.0;
        pY = 0.0;


    }

    public void draw(final Renderer renderer) {
        for (int i = 0; i < MAX_SIZE; i++) {
            if (info[i] != null) {
                info[i].draw(renderer);
            }
        }
    }

    public void setInfo(DebugInfos info, Object[] params) {
        if (params != null && params.length > 0) {
            switch (info) {
                case PlayerX: {
                    if (params[0] instanceof Double) {
                        pX = (double) params[0];
                    }
                    this.info[0].setText("x: " + pX);
                    break;
                }
                case PlayerY: {
                    if (params[0] instanceof Double) {
                        pY = (double) params[0];
                    }
                    this.info[1].setText("y: " + pY);
                    break;
                }
                case Strength: {
                    if (params[0] instanceof Byte) {
                        strength = (byte) params[0];
                    }
                    this.info[2].setText("strength: " + strength);
                    break;
                }
                case VelocityX: {
                    if (params[0] instanceof Double) {
                        xVel = (double) params[0];
                    }
                    this.info[3].setText("vel x: " + xVel);
                    break;
                }
                case VelocityY: {
                    if (params[0] instanceof Double) {
                        yVel = (double) params[0];
                    }
                    this.info[4].setText("vel y: " + yVel);
                    break;
                }
                case WaterIncrease: {
                    if (params[0] instanceof Double) {
                        waterIncrease = (double) params[0];
                    }
                    this.info[5].setText("water increase: " + waterIncrease);
                    break;
                }
            }
        }
    }


    public enum DebugInfos {
        PlayerX,
        PlayerY,
        Strength,
        VelocityX,
        VelocityY,
        WaterIncrease
    }

}
