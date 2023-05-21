package sk.uniza.fri.hra.jednotky;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import sk.uniza.fri.hra.HpBar;
import sk.uniza.fri.hra.Pozicia;
import sk.uniza.fri.hra.efekt.Efekt;
import sk.uniza.fri.hra.enumy.STAV_JEDNOTKY;

import java.util.ArrayList;

public class Jednotka extends Pozicia {
    private int hP;
    private int aktualneHp;
    private double aktualnaRychlost;
    private int dF;
    private HpBar hpBar;
    protected Texture texture;
    private STAV_JEDNOTKY stav_jednotky;
    private double pozicia;
    private ArrayList<Efekt> efekty;
    private final int cena;
    private final double baseRychlost;
    private final ArrayList<Pozicia> body;

    public Jednotka(ArrayList<Pozicia> body, int hP, double baseRychlost, int dF, int cena) {
        super( body.get(0).getxSuradnica(),  body.get(0).getySuradnica());
        this.baseRychlost = baseRychlost;
        this.body = body;
        this.hP = hP;
        this.aktualnaRychlost = baseRychlost;
        this.dF = dF;
        this.stav_jednotky = STAV_JEDNOTKY.OK;
        this.cena = cena;
        this.pozicia = 0;
        this.hpBar = new HpBar( hP);
        this.efekty = new ArrayList<>();

    }

    public double getBaseRychlost() {
        return this.baseRychlost;
    }

    public int getCena() {
        return cena;
    }

    public STAV_JEDNOTKY getStav_jednotky() {
        return stav_jednotky;
    }


    public void vykresli(SpriteBatch batch) {
        this.hpBar.vykresli(batch, super.getxSuradnica() - (64 - this.texture.getWidth())/2, super.getySuradnica()
                + this.texture.getHeight() + 5);

        batch.draw(this.texture, super.getxSuradnica(), super.getySuradnica());

        for (Efekt e:
             this.efekty) {
            e.vykresli(batch);
        }

    }

    public boolean posun() {
        if (this.pozicia >= this.body.size()){
            return  true;
        }
        super.setxSuradnica(this.body.get((int)this.pozicia ).getxSuradnica());
        super.setySuradnica(this.body.get((int)this.pozicia ).getySuradnica());
        this.pozicia += this.aktualnaRychlost;

        return false;
    }

    public void uberHp(int poskodenie) {

        if (this.hpBar.getAktualnehP() - poskodenie <= 0) {
            this.stav_jednotky = STAV_JEDNOTKY.RIP;
        } else {
            this.hpBar.setAktualnehP(this.hpBar.getAktualnehP() - poskodenie);
        }
    }

    public void pridajEfekt(Efekt efekt) {
        for (int i = 0; i < this.efekty.size(); i++) {
            if (this.efekty.get(i).getClass().equals(efekt.getClass())) {
                this.efekty.remove(this.efekty.get(i));
            }
        }
            this.efekty.add(efekt);

    }

    public void uplatniEfekty() {
        for (int i = 0; i < this.efekty.size(); i++) {
            if (this.efekty.get(i).getDobaTrvania() != 0) {
                this.efekty.get(i).vykonaj();
                this.efekty.get(i).uberDobaTrvania();
            } else {
                this.efekty.get(i).odstranEfekt();
                this.efekty.remove(i);
            }
        }
    }

    public int gethP() {
        return hP;
    }

    public void sethP(int hP) {
        this.hP = hP;
    }

    public int getAktualneHp() {
        return aktualneHp;
    }


    public double getRychlost() {
        return aktualnaRychlost;
    }

    public void setAktualnaRychlost(double rychlost) {
        this.aktualnaRychlost = rychlost;
    }

    public int getdF() {
        return dF;
    }

    public void setdF(int dF) {
        this.dF = dF;
    }

    public HpBar getHpBar() {
        return hpBar;
    }

    public void setHpBar(HpBar hpBar) {
        this.hpBar = hpBar;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}
