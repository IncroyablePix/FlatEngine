package be.helmo.graphics;

import be.helmo.enums.AbstractTiles;

import java.awt.image.BufferedImage;

public class TileType {
    public static final byte TYPE_NO_COL = 0,
            TYPE_BLOCKED = 1;

    private final BufferedImage image;
    private final AbstractTiles abstractType;
    private final byte type;

    public TileType(BufferedImage image, byte type, AbstractTiles abstractType) {

        if (image == null) {
            throw new IllegalArgumentException("Invalid tile Bitmap");
        }

        this.abstractType = abstractType;
        this.image = image;
        this.type = type;
    }

    public BufferedImage getImage() {
        return image;
    }

    public byte getType() {
        return type;
    }

    public AbstractTiles getAbstractType() {
        return abstractType;
    }

}