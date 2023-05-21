package sk.uniza.fri.hra.veze;
import com.badlogic.gdx.graphics.Texture;

public class Kanon extends Veza {

    private static final int ZAKLADNY_DOSAH = 80;
    private static final int ZAKLADNA_DLZKA_NABIJANIA = 80;
    private static final int ZAKLADNE_POSKODENIE = 7;
    private static final int ZAKLADNE_TRVANIE_EFEKTU = 0;
    private static final int CENA = 25;

    public Kanon( int xSuradnica, int ySuradnica) {
        super( xSuradnica, ySuradnica,ZAKLADNY_DOSAH , ZAKLADNE_POSKODENIE, ZAKLADNA_DLZKA_NABIJANIA,  ZAKLADNE_TRVANIE_EFEKTU, CENA);
        super.setTexture( Kanon.texture());

    }
    public static Texture texture() {
        return new Texture("veze/kanon.png");
    }



}
