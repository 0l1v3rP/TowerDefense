package sk.uniza.fri.hra.efekt;

import com.badlogic.gdx.graphics.Texture;
import sk.uniza.fri.hra.jednotky.Jednotka;

public class HoriaciEfekt extends Efekt {

    private int dmg;
    private int casovac;

    private final int INTERVAL = 20;

    public HoriaciEfekt(Jednotka jednotka, int dobaTrvania, int dmg) {
        super(jednotka, dobaTrvania);
        this.dmg = dmg;
        this.casovac = 0;
        super.setTexture(new Texture("Efekty/OHEN.png"));
    }

    @Override
    public void vykonaj() {
        this.casovac++;
        if (this.casovac == this.INTERVAL) {
            super.getJednotka().uberHp(dmg);
            this.casovac = 0;
        }
    }

    @Override
    public void odstranEfekt() {
    }


}
