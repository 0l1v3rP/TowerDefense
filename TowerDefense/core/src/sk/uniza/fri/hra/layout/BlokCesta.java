package sk.uniza.fri.hra.layout;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BlokCesta extends Blok {

    public BlokCesta(int xSuradnica, int ySuradnica) {
        super(xSuradnica, ySuradnica);
        super.setTextureRegion(BlokCesta.getTextureRegion());
    }

    public static TextureRegion getTextureRegion() {
        TextureRegion textureRegion = new TextureRegion(new Texture("bloky/Cesta.png"));
        textureRegion.setRegionHeight(Blok.getVyska());
        textureRegion.setRegionWidth(Blok.getSirka() );
        return textureRegion;
    }

}
