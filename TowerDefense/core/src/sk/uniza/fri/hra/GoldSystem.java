package sk.uniza.fri.hra;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GoldSystem extends Pozicia {

    private int peniaze;
    private BitmapFont bitmapFont;

    /**
     *trieda podla navrhoveho vzoru singleton
     *uchovava si informacie o goldoch
     */


    private static GoldSystem instance = null;
    private GoldSystem() {
        super(Gdx.graphics.getWidth() - 80, Gdx.graphics.getHeight() - 10);
        this.peniaze = 0;

    }

    public static GoldSystem getInstance()
    {
        if (instance == null)
            instance = new GoldSystem();

        return instance;
    }

    public void vykresli(SpriteBatch spriteBatch) {
        this.bitmapFont.draw(spriteBatch, "GOLD: " + this.peniaze ,super.getxSuradnica(), super.getySuradnica());
    }

    public void setBitmapFont(BitmapFont bitmapFont) {
        this.bitmapFont = bitmapFont;

    }

    public int getPeniaze() {
        return peniaze;
    }

    public void setPeniaze(int peniaze) {
        this.peniaze = peniaze;
    }

    public void pridajGoldy(int g){
        this.peniaze += g;
    }

}
