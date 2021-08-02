package com.geek.libglide47.glide48;

/**
 * Description:
 * Create by lxj, at 2019/3/4
 */
public enum Glide48ImageType {
    GIF(true),
    JPEG(false),
    RAW(false),
    /** PNG type with alpha. */
    PNG_A(true),
    /** PNG type without alpha. */
    PNG(false),
    /** WebP type with alpha. */
    WEBP_A(true),
    /** WebP type without alpha. */
    WEBP(false),
    /** Unrecognized type. */
    UNKNOWN(false);

    private final boolean hasAlpha;

    Glide48ImageType(boolean hasAlpha) {
        this.hasAlpha = hasAlpha;
    }

    public boolean hasAlpha() {
        return hasAlpha;
    }
}