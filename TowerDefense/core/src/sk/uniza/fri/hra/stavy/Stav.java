package sk.uniza.fri.hra.stavy;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class Stav {

    private ShapeRenderer sr;
    private SpriteBatch sb;
    private MenezerStavov menezerStavov;
    private BitmapFont bitmapFont;


    public Stav(MenezerStavov menezerStavov) {
        this.menezerStavov = menezerStavov;
        this.sr = new ShapeRenderer();
        this.sb = new SpriteBatch();
        this.sr.setAutoShapeType(true);
        this.bitmapFont = new BitmapFont();
    }

    public MenezerStavov getMenezerStavov() {
        return menezerStavov;
    }

    public abstract void render();

    public ShapeRenderer getSr() {
        return sr;
    }

    public SpriteBatch getSb() {
        return sb;
    }

    public BitmapFont getBitmapFont() {
        return bitmapFont;
    }
}
