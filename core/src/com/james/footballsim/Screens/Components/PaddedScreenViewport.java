package com.james.footballsim.Screens.Components;

import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class PaddedScreenViewport extends ScreenViewport {

    int topPadding;
    int bottomPadding;
    private float unitsPerPixel = 1;

    public PaddedScreenViewport(int topPadding, int bottomPadding){
        this.topPadding = topPadding;
        this.bottomPadding = bottomPadding;
    }

    @Override
    public void update(int screenWidth, int screenHeight, boolean centerCamera) {
        setScreenBounds(0, 400, screenWidth, (screenHeight));
        setWorldSize(screenWidth * unitsPerPixel, (screenHeight+topPadding) * unitsPerPixel);
        apply(centerCamera);
    }

    public float getUnitsPerPixel () {
        return unitsPerPixel;
    }

    /** Sets the number of pixels for each world unit. Eg, a scale of 2.5 means there are 2.5 world units for every 1 screen pixel.
     * Default is 1. */
    public void setUnitsPerPixel (float unitsPerPixel) {
        this.unitsPerPixel = unitsPerPixel;
    }

}
