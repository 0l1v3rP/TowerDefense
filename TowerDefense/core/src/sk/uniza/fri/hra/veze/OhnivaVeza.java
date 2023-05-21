package sk.uniza.fri.hra.veze;

import com.badlogic.gdx.graphics.Texture;
import sk.uniza.fri.hra.efekt.HoriaciEfekt;
import sk.uniza.fri.hra.jednotky.Jednotka;

public class OhnivaVeza extends Veza {

    private static final int ZAKLADNY_DOSAH = 65;
    private static final int ZAKLADNA_DLZKA_NABIJANIA = 120;
    private static final int ZAKLADNE_POSKODENIE = 2;
    private static final int ZAKLADNE_TRVANIE_EFEKTU = 120;
    private static final int CENA = 25;
    private final int ZAKLADNE_POSKODENIE_EFEKTU = 1;

    public OhnivaVeza( int xSuradnica, int ySuradnica) {
        super(xSuradnica, ySuradnica, ZAKLADNY_DOSAH, ZAKLADNE_POSKODENIE, ZAKLADNA_DLZKA_NABIJANIA, ZAKLADNE_TRVANIE_EFEKTU, CENA);
        super.setTexture(OhnivaVeza.texture());

    }

    public void setEfekt(Jednotka jednotka) {
        jednotka.pridajEfekt(new HoriaciEfekt(jednotka, super.getTrvanieEfektu(), ZAKLADNE_POSKODENIE_EFEKTU));
    }


    public static Texture texture() {
        return new Texture("veze/OhnivaVeza.png");
    }



}
