package sk.uniza.fri.hra.stavy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import sk.uniza.fri.hra.*;
import sk.uniza.fri.hra.enumy.STAV_HRY;
import sk.uniza.fri.hra.enumy.STAV_JEDNOTKY;
import sk.uniza.fri.hra.layout.Mapa;
import sk.uniza.fri.hra.veze.Veza;

import java.util.ArrayList;

public class HraciStav extends Stav {
    private Vlna vlna;
    private ArrayList<Veza> veze ;
    private Mapa mapa;
    private int zivoty;
    private MenezerStavov menezerStavov;
    private HracieMenu hracieMenu;
    private ZoznamVezi zoznamVezi;
    private GoldSystem goldSystem;

    private final int POCET_ZIVOTOV = 3;
    private final int ZAKLADNE_GOLDY = 100;

    public HraciStav(MenezerStavov menezerStavov) {
        super(menezerStavov);


        this.veze = new ArrayList<>();
        this.menezerStavov = menezerStavov;

        this.zoznamVezi = new ZoznamVezi(super.getSb());

        this.mapa = new Mapa();
        this.vlna = new Vlna(this.mapa.getBody());
        this.zivoty = POCET_ZIVOTOV;

        super.getBitmapFont().setColor(Color.GOLD);

        this.hracieMenu = new HracieMenu(this.mapa, super.getSr(),super.getSb(), this.zoznamVezi, super.getMenezerStavov());

        this.goldSystem = GoldSystem.getInstance();
        this.goldSystem.pridajGoldy(ZAKLADNE_GOLDY);
        this.goldSystem.setBitmapFont(super.getBitmapFont());

    }

    @Override
    public void render() {
        ScreenUtils.clear(Color.FIREBRICK, true);
        super.getSb().begin();


        this.mapa.vykresli(super.getSb());
        this.zivoty = this.zivoty - this.vlna.vykonaj();

        vezeVykonaj();

        this.vlna.vykonaj(super.getSb());
        super.getBitmapFont().draw(super.getSb(), "ZIVOTY: " + this.zivoty, 10, Gdx.graphics.getHeight() - 10  );
        this.goldSystem.vykresli(super.getSb());

        super.getSb().end();

        vykresliVeze();
        this.hracieMenu.render();
        prehralSom();
    }

    private void vezeVykonaj() {
        for (Veza v:
                this.zoznamVezi.getZoznamVezi()) {

            v.vykresli(super.getSb());
            v.zameraj(this.vlna.getVlna());

            if (v.vystrel()) {
                if (v.getZameriavanie().getStav_jednotky() == STAV_JEDNOTKY.RIP) {
                    v.setZameriavanie(null);
                }
            }
        }
    }

    private void vykresliVeze() {
        super.getSr().begin();
        for (Veza v:
                this.zoznamVezi.getZoznamVezi()) {
            v.vykresli(super.getSr());
        }
        super.getSr().end();
    }

    private void prehralSom() {
        if (this.zivoty == 0) {
            GoldSystem.getInstance().setPeniaze(0);
            this.menezerStavov.setAktualnyStav(STAV_HRY.UKONCI);
        }
    }
}
