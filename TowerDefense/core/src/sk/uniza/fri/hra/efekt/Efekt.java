package sk.uniza.fri.hra.efekt;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import sk.uniza.fri.hra.jednotky.Jednotka;

public abstract class Efekt {
    private int dobaTrvania;
    private Jednotka jednotka;
    private Texture texture;

    public Efekt(Jednotka jednotka,int dobaTrvania ) {
        this.jednotka = jednotka;
        this.dobaTrvania = dobaTrvania;
    }

    public int getDobaTrvania() {
        return this.dobaTrvania;
    }

    public void uberDobaTrvania() {
        this.dobaTrvania--;
    }

    public Jednotka getJednotka() {
        return this.jednotka;
    }

    public void vykresli(SpriteBatch batch) {
        batch.draw(this.texture, this.jednotka.getxSuradnica(),this.jednotka.getySuradnica());
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public abstract void vykonaj();

    public abstract void odstranEfekt();
}
