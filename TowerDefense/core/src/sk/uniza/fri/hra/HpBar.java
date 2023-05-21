package sk.uniza.fri.hra;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class HpBar {

        private int aktualnehP;
        private int hP;
        private TextureRegion vonkajsi;
        private TextureRegion vnutorny;


    public HpBar(  int hP) {
        this.hP = hP;
        this.aktualnehP = hP;
        Pixmap typ1 = new Pixmap( 62, 6, Pixmap.Format.RGBA8888 );
        typ1.setColor( 0, 1,51,1);
        typ1.fillRectangle(0, 0 , 62, 6 );
        this.vnutorny = new TextureRegion(new Texture(typ1));
        typ1.dispose();

        Pixmap typ2 = new Pixmap(66, 10, Pixmap.Format.RGBA8888 );
        typ2.setColor( 0, 0, 0, 1 );
        typ2.fillRectangle(0, 0 , 64, 10);
        this.vonkajsi = new TextureRegion(new Texture(typ2));
        typ2.dispose();
    }

    public int getAktualnehP() {
        return aktualnehP;
    }

    public void setAktualnehP(int aktualnehP) {
        this.aktualnehP = aktualnehP;
    }

    /**
     *vykresluje aktualne hp
     */


    public void vykresli(SpriteBatch batch , int x , int y) {
        batch.draw(this.vonkajsi, x, y);
        batch.draw(this.vnutorny, x + 2 ,y + 2 , (int) ((62/(double)this.hP ) * this.aktualnehP), 6);
    }
}