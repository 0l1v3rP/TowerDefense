package sk.uniza.fri.hra.layout;

/**
 *mapovacia struktura pre bloky
 */


public class ManazerBlokov {

    private static final int CESTA = 1;
    private static final int MOZNA_VEZA = 0;
    private static final int ZACIATOK = 2;
    private static final int KONIEC = 3;
    private static final int MENU = 4;

    /**
     *mapuje cisla na bloky
     */

    public static Blok vytvorBlok(int i, int x, int y) {
        switch (i) {
            case CESTA:
                return new BlokCesta(x, y);
            case MOZNA_VEZA:
                return new BlokVeza(x, y);
            case ZACIATOK:
                return new BlokZaciatocny(x, y);
            case KONIEC:
                return new BlokKoncovy(x, y);
            case MENU:
                return new BlokMenu(x, y);
            default:
                return null;
        }
    }

    /**
     *mapuje bloky na cisla
     */


    public static int getCiselnuReprezentaciuBloku(Blok blok) {
        if (blok instanceof BlokCesta) {
            return CESTA;
        } else if (blok instanceof BlokVeza) {
            return MOZNA_VEZA;
        } else if (blok instanceof BlokZaciatocny) {
            return ZACIATOK;
        } else if (blok instanceof BlokKoncovy) {
            return KONIEC;
        } else if (blok instanceof BlokMenu) {
            return MENU;
        }
        return -1;
    }
}
