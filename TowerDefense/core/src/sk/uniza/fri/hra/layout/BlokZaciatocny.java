package sk.uniza.fri.hra.layout;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BlokZaciatocny extends Blok{



    public BlokZaciatocny(int xSuradnica, int ySuradnica) {
        super(xSuradnica, ySuradnica);
        TextureRegion textureRegion = new TextureRegion(BlokZaciatocny.getTexture());

        super.setTextureRegion(textureRegion);
    }


    public static Texture getTexture() {
        Pixmap pixmap = new Pixmap( Blok.getSirka() , Blok.getVyska(), Pixmap.Format.RGBA8888 );
        pixmap.setColor( 255, 255,255,1 );
        pixmap.fillRectangle(0, 0 , Blok.getSirka(), Blok.getVyska());
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }

}


