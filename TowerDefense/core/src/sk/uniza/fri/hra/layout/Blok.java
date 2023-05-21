package sk.uniza.fri.hra.layout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import sk.uniza.fri.hra.Pozicia;

public class Blok extends Pozicia {


    protected static final int SIRKA = Gdx.graphics.getWidth() /  20;
    private static final int VYSKA = Gdx.graphics.getHeight() / 16;

    private TextureRegion textureRegion;
    private boolean mozemPolozitVezu;


    public Blok(int xSuradnica, int ySuradnica) {
        super(xSuradnica, ySuradnica);
    }



    public void setTextureRegion(TextureRegion textureRegion){
        this.textureRegion = textureRegion;
    }

    public static int getVyska() {
        return VYSKA;
    }

    public static int getSirka() {
        return SIRKA;
    }

    public void vykresli(SpriteBatch spriteBatch) {
        spriteBatch.draw(this.textureRegion, super.getxSuradnica(), super.getySuradnica());
    }

    public void setMozemPolozitVezu(boolean mozemPolozitVezu) {
        this.mozemPolozitVezu = mozemPolozitVezu;
    }

    public boolean isMozemPolozitVezu() {
        return mozemPolozitVezu;
    }

}
