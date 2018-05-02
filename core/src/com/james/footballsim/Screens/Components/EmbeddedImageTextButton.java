package com.james.footballsim.Screens.Components;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

public class EmbeddedImageTextButton extends TextButton {

    public EmbeddedImageTextButton(String text, Skin skin) {
        this(text, skin.get(TextButtonStyle.class));
        setSkin(skin);
    }

    public EmbeddedImageTextButton(String text, Skin skin, String styleName) {
        this(text, skin.get(styleName, TextButtonStyle.class));
        setSkin(skin);
    }

    public EmbeddedImageTextButton(String text, TextButtonStyle style) {
        super(text, style);
        getLabelCell().spaceLeft(40f);

    }
}
