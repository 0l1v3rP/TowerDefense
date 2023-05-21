package sk.uniza.fri.hra.efekt;

import com.badlogic.gdx.graphics.Texture;
import sk.uniza.fri.hra.jednotky.Jednotka;

public class SpomalovaciEfekt extends Efekt  {

    private double spomalO;
    private boolean vykonaj;

    public SpomalovaciEfekt(Jednotka jednotka, int dobaTrvania, double spomalO) {
        super(jednotka, dobaTrvania);
        this.spomalO = spomalO;
        this.vykonaj = false;
        super.setTexture(new Texture("Efekty/LAD.png"));

    }


    @Override
    public void vykonaj() {
        if (!this.vykonaj) {
            super.getJednotka().setAktualnaRychlost(this.spomalO);
            this.vykonaj = true;
        }
    }


    @Override
    public void odstranEfekt() {
        super.getJednotka().setAktualnaRychlost(super.getJednotka().getBaseRychlost());
    }

}
