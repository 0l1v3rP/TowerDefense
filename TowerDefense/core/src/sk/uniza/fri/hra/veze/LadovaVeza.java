package sk.uniza.fri.hra.veze;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import sk.uniza.fri.hra.efekt.SpomalovaciEfekt;
import sk.uniza.fri.hra.jednotky.Jednotka;

public class LadovaVeza extends Veza {

    private static final int ZAKLADNY_DOSAH = 65;
    private static final int ZAKLADNA_DLZKA_NABIJANIA = 120;
    private static final int ZAKLADNE_POSKODENIE = 3;
    private static final int CENA = 25;
    private static final int ZAKLADNE_TRVANIE_EFEKTU = 80;
    private final double SPOMAL_O = 0.5;

    public LadovaVeza( int xSuradnica, int ySuradnica) {
        super(  xSuradnica, ySuradnica,ZAKLADNY_DOSAH , ZAKLADNE_POSKODENIE,ZAKLADNA_DLZKA_NABIJANIA, ZAKLADNE_TRVANIE_EFEKTU, CENA);
        super.setTexture(LadovaVeza.texture());
    }

    /**
     *nastavi efekt jednotke
     */

    public void setEfekt(Jednotka jednotka) {
        jednotka.pridajEfekt(new SpomalovaciEfekt(jednotka, super.getTrvanieEfektu(), this.SPOMAL_O));
    }

    public static Texture texture() {
        return new Texture("veze/LadovaVeza.png");
    }

}
