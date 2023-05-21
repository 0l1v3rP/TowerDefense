package sk.uniza.fri.hra.veze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import sk.uniza.fri.hra.GoldSystem;
import sk.uniza.fri.hra.Pozicia;
import sk.uniza.fri.hra.enumy.STAV_JEDNOTKY;
import sk.uniza.fri.hra.jednotky.Jednotka;
import sk.uniza.fri.hra.layout.Mapa;
import java.util.ArrayList;

/**
 * Objekt veza
 */


public class Veza extends Pozicia {


    private int dosah;
    private Jednotka zameriavanie;
    private int dlzkaNabijania;
    private int poskodenie;
    private int trvanieEfektu;
    private Texture texture;
    private STAV_JEDNOTKY stav_jednotky;
    private int casDovyStrelu;
    private boolean zobrazVystrel;
    private Jednotka pomJednotka;
    private int cena;



    public Veza(int xSuradnica, int ySuradnica, int dosah, int poskodenie, int dlzkaNabijania, int trvanieEfektu, int cena) {
        super(xSuradnica, ySuradnica);
        this.trvanieEfektu = trvanieEfektu;
        this.zameriavanie = null;
        this.casDovyStrelu = 0;
        this.stav_jednotky = STAV_JEDNOTKY.OK;
        this.zobrazVystrel = false;
        this.pomJednotka = null;
        this.cena = cena;
        this.dosah = dosah;
        this.poskodenie = poskodenie;
        this.dlzkaNabijania = dlzkaNabijania;
    }

    public int getCena() {
        return cena;
    }

    /**
     * Vrati true ak mozem vezu polozit na mapu a zaroven mam dostatok penazi
     */

    public boolean mozemPolozit(Mapa mapa, float x, float y) {
        return mapa.nachadzamSa(x,y).isMozemPolozitVezu() &&
                mapa.nachadzamSa(x + this.texture.getWidth(),y).isMozemPolozitVezu() &&
                mapa.nachadzamSa(x,y + this.texture.getHeight()).isMozemPolozitVezu() &&
                mapa.nachadzamSa(x + this.texture.getWidth(),y + this.texture.getHeight()).isMozemPolozitVezu()
                && GoldSystem.getInstance().getPeniaze() >= this.cena;

    }

    /**
     *vykresli vezu
     */

    public void vykresli(SpriteBatch spriteBatch) {
        spriteBatch.draw(this.getTexture(), this.getxSuradnica() - (float)this.getTexture().getWidth() / 2, this.getySuradnica() - (float)this.getTexture().getHeight() / 2);
}

    public boolean klikolSomMySomNaVezu() {
        return Gdx.input.isTouched() && nachadzamSaMysouNadVezou();
    }

    private boolean nachadzamSaMysouNadVezou() {
        return (Gdx.graphics.getHeight() - Gdx.input.getY() <= super.getySuradnica() + this.getTexture().getHeight() / 2) && Gdx.graphics.getHeight() - Gdx.input.getY() >= super.getySuradnica() - this.getTexture().getHeight() / 2
                && (Gdx.input.getX() <= super.getxSuradnica() + this.getTexture().getWidth() / 2) && Gdx.input.getX() >= super.getxSuradnica() - this.getTexture().getWidth() / 2;
    }


    /**
     *ak ukazem myskou na vezu tak zobrazi jej dosah
     */

    public void vykresli(ShapeRenderer shapeRenderer) {
        if (this.nachadzamSaMysouNadVezou()) {
            shapeRenderer.set(ShapeRenderer.ShapeType.Line);
            shapeRenderer.circle(super.getxSuradnica()
                    , super.getySuradnica(), this.dosah);

        }
        this.renderVystrel(shapeRenderer);
    }

    /**
     *ak veza neni zamera na jednotku tak sa pokusi na nu zamerat,
     *ak sa jednotka dostane mimo dosah veze tak veza si hlada novu jednotku na zameranie
     */

    public void zameraj(ArrayList<Jednotka> jednotky) {

        if (this.zameriavanie == null || this.zameriavanie.getStav_jednotky() == STAV_JEDNOTKY.RIP) {
            for (Jednotka j :
                    jednotky) {

                if (Math.sqrt(((j.getxSuradnica() + (float)j.getTexture().getWidth()/2 - super.getxSuradnica()) * (j.getxSuradnica() - super.getxSuradnica() + (float)j.getTexture().getHeight()/2)) +
                        (j.getySuradnica() - super.getySuradnica()) * (j.getySuradnica() - super.getySuradnica())) <= this.dosah) {

                    this.zameriavanie = j;
                    this.pomJednotka = j;
                    break;
                }
            }
        } else if (Math.sqrt(((this.zameriavanie.getxSuradnica() - super.getxSuradnica()) * (this.zameriavanie.getxSuradnica() - super.getxSuradnica())) +
                (this.zameriavanie.getySuradnica() - super.getySuradnica()) * (this.zameriavanie.getySuradnica() - super.getySuradnica())) > this.dosah){
                this.zameriavanie = null;
        }


    }

    /**
     *veza vystreli a uplatni svoj efekt na jednotku ak je ready striela a nepriatel je stale zivy
     */

    public boolean vystrel() {

        if (this.zameriavanie != null && this.casDovyStrelu == 0) {
            if (this.zameriavanie.getStav_jednotky() == STAV_JEDNOTKY.RIP) {
                this.zobrazVystrel = false;
                this.pomJednotka = null;
            } else {
                this.zobrazVystrel = true;
            }
            this.setEfekt(this.zameriavanie);
            this.zameriavanie.uberHp(this.poskodenie);
            this.casDovyStrelu++;
            return true;
        }
        if (this.casDovyStrelu != 0) {
            this.casDovyStrelu++;
            this.casDovyStrelu = this.casDovyStrelu%this.dlzkaNabijania;
        }
        return false;
    }

    /**
     *zobrazy vystrel ako ciaru od jednotky k vezi
     */

    private void renderVystrel(ShapeRenderer sr) {
        if (this.zobrazVystrel) {
            sr.set(ShapeRenderer.ShapeType.Line);
            sr.line( this.pomJednotka.getxSuradnica() + (float)this.pomJednotka.getTexture().getWidth()/2,
                    this.pomJednotka.getySuradnica() +  (float)this.pomJednotka.getTexture().getHeight()/2,
                    super.getxSuradnica(), super.getySuradnica());
            this.zobrazVystrel = false;

            if (this.zameriavanie == null) {
                this.pomJednotka = null;
            }
        }

    }

    public void setEfekt(Jednotka jednotka){}

    public int getTrvanieEfektu() {
        return trvanieEfektu;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Jednotka getZameriavanie() {
        return zameriavanie;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setZameriavanie(Jednotka zameriavanie) {
        this.zameriavanie = zameriavanie;
    }

}
