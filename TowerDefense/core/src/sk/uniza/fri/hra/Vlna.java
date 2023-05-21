package sk.uniza.fri.hra;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import sk.uniza.fri.hra.enumy.STAV_JEDNOTKY;
import sk.uniza.fri.hra.jednotky.Jednotka;
import sk.uniza.fri.hra.jednotky.Jerry;
import sk.uniza.fri.hra.jednotky.RychlyJerry;

import java.util.ArrayList;

public class Vlna {
    private ArrayList<Jednotka> inicializacnaVlna;
    private ArrayList<Jednotka> vlna;
    private int interval;
    private final int VELKOST_INTERVALU = 60;

    public Vlna(ArrayList<Pozicia> body) {
        this.vlna = new ArrayList<>();
        this.inicializacnaVlna = new ArrayList<>();
        this.inicializacnaVlna.add(new Jerry(body));
        this.inicializacnaVlna.add(new Jerry(body));
        this.inicializacnaVlna.add(new Jerry(body));
        this.inicializacnaVlna.add(new Jerry(body));
        this.inicializacnaVlna.add(new Jerry(body));
        this.inicializacnaVlna.add(new Jerry(body));
        this.inicializacnaVlna.add(new Jerry(body));
        this.inicializacnaVlna.add(new Jerry(body));
        this.inicializacnaVlna.add(new RychlyJerry( body));
        this.inicializacnaVlna.add(new RychlyJerry( body));
        this.inicializacnaVlna.add(new RychlyJerry( body));
    }

    /**
     *posunie jednoky a vrati pocet jednotiek ktore sa dostali do ciela, jednotky v ciely vymaze zo zoznamu
     */

    public int vykonaj() {
        int zivoty;
        ArrayList<Jednotka> odoberJednotky = new ArrayList<>();
        for (Jednotka jednotka : this.vlna) {
            if (jednotka.posun()) {
                odoberJednotky.add(jednotka);
            }
        }
        zivoty = odoberJednotky.size();
        for (Jednotka j:
             odoberJednotky) {
            this.vlna.remove(j);
        }
        return zivoty;
    }

    public ArrayList<Jednotka> getVlna() {
        return this.vlna;
    }

    public void vykonaj(SpriteBatch batch) {
        for (Jednotka jednotka : this.vlna) {
            jednotka.vykresli(batch);
            jednotka.uplatniEfekty();
        }
        this.mozeIstDalsi();
        this.odoberJednotky();
    }

    /**
     *odoberie jednotku ktora zomrela, prida goldy za danu jednotku
     */


    private void odoberJednotky() {
        Jednotka odoberJednotku = null;
        for (Jednotka jednotka : this.vlna) {
            if (jednotka.getStav_jednotky() == STAV_JEDNOTKY.RIP) {
                odoberJednotku = jednotka;
                break;
            }
        }
        if (odoberJednotku != null) {
            GoldSystem.getInstance().pridajGoldy(odoberJednotku.getCena());
            this.vlna.remove(odoberJednotku);
            this.odoberJednotky();
        }
    }

    /**
     *vysle dalsiu jednotku do pohybu
     */


    private void mozeIstDalsi() {
        if (this.inicializacnaVlna.size() != 0) {
            if (this.interval == this.VELKOST_INTERVALU) {
                this.interval = 0;
                this.vlna.add(this.inicializacnaVlna.remove(0)) ;
            }
            this.interval++;
        }
    }
}
