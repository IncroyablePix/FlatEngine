package com.c4nn4.menu.options;

public class Resolution implements Comparable<Resolution> {
    private final int width;
    private final int height;

    public Resolution(final int width, final int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public String getDescription() {
        return this.width + "x" + this.height;
    }

    @Override
    public boolean equals(final Object other) {
        boolean retValue = false;

        if (!retValue && other != null) {
            if (this == other) {
                retValue = true;
            }
            else if (other instanceof Resolution &&
                    this.width == ((Resolution) other).width &&
                    this.height == ((Resolution) other).height) {
                retValue = true;
            }
        }

        return retValue;
    }

    @Override
    public int hashCode() {
        return this.width * this.height;
    }

    @Override
    public int compareTo(final Resolution other) {
        return this.width - other.width;
    }
}
