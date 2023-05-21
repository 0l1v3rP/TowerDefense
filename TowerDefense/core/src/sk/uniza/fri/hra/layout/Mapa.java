package sk.uniza.fri.hra.layout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import sk.uniza.fri.hra.Pozicia;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *reprezentuje hernu mapu
 */

public class Mapa {

    private Blok[][] mapa;
    private ArrayList<Blok> cesta;
    private ArrayList<Pozicia> body;
    private int[][] mapaVCislach;
    private ArrayList<int[]> cestaInt;

    private int[] prvy;
    private int[] posledny;

    private final int VYSKA_V_BLOKOCH = 16;
    private final int SIRKA_V_BLOKOCH = 20;

    public Mapa() {
        this.body = new ArrayList<>();
        this.cesta = new ArrayList<>();
        this.prvy = new int[2];
        this.posledny = new int[2];
        this.mapa = this.nacitajZoSuboru("TowerDefense/assets/mapy/mapa.txt");
        this.cestaInt = new ArrayList<>();
        this.nastavCestu();
        this.nacitajBody();
    }




    public void vykresli(SpriteBatch spriteBatch) {
        for (int i = this.mapa.length - 1; i >= 0; i--) {
            for (int j = 0; j < this.mapa[0].length; j++) {
                this.mapa[i][j].vykresli(spriteBatch);
            }
        }
    }

    /**
     *vrati blok na ktorom sa nachadzam
     */

    public Blok nachadzamSa(double x, double y) {
        double pomX = x/((double)Gdx.graphics.getWidth() / this.mapa[0].length);
        double pomY = y/((double)Gdx.graphics.getHeight() / this.mapa.length);
        return this.mapa[(int)pomY][(int)pomX];
    }

    /**
     *nastavy body pohybu pre jednotky, od startu k cielu
     */

    private void nacitajBody() {
        int x = this.cesta.get(0).getxSuradnica() ;
        int y = this.cesta.get(0).getySuradnica();
        this.body.add(new Pozicia(x,y));


        for (int i = 0; i < this.cesta.size() - 1; i++) {
            if (this.cesta.get(i).getySuradnica() == this.cesta.get(i + 1).getySuradnica()) {
                System.out.println(i+": " + this.cesta.get(i).getySuradnica());
                for (int j = 0; j < Blok.getSirka(); j++) {
                    if (this.cesta.get(i).getxSuradnica() < this.cesta.get(i + 1).getxSuradnica()) {
                        x++;
                    } else {
                        x--;
                    }
                    this.body.add(new Pozicia(x,y));
                }
            } else {
                for (int j = 0; j < Blok.getVyska(); j++) {
                    if (this.cesta.get(i).getySuradnica() < this.cesta.get(i + 1).getySuradnica()) {
                        y++;
                    } else {
                        y--;
                    }
                    this.body.add(new Pozicia(x,y));
                }
            }
        }
    }

    /**
     *nacita maou zo suboru
     */

    private Blok[][] nacitajZoSuboru(String cestaKSuboru) {
        File subor = new File(cestaKSuboru);
        Blok[][] docMriezka = null;

        try {
            Scanner citac = new Scanner(subor);
            docMriezka = new Blok[VYSKA_V_BLOKOCH][SIRKA_V_BLOKOCH];
            this.mapaVCislach = new int[VYSKA_V_BLOKOCH][SIRKA_V_BLOKOCH];
            nastavovanieBlokov(docMriezka,citac);

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return docMriezka;
    }

    /**
     *zapise blok do pola blokov podla cisla zo suboru
     */

    private void nastavovanieBlokov(Blok[][] docMriezka, Scanner citac) {
        final int START = 2;
        final int KONIEC = 3;
        for (int i = docMriezka.length - 1; i >= 0; i--) {
            for (int j = 0; j < docMriezka[0].length; j++) {
                int x = j * (Gdx.graphics.getWidth() / docMriezka[0].length);
                int y = i * (Gdx.graphics.getHeight() / docMriezka.length);
                int blok = 0;
                    if (citac.hasNext()) {
                        blok = citac.nextInt();

                        if (blok == START) {
                            this.prvy[0] = i;
                            this.prvy[1] = j;
                        }
                        if (blok == KONIEC) {
                            this.posledny[0] = i;
                            this.posledny[1] = j;
                        }
                    this.mapaVCislach[i][j] = blok;
                }
                docMriezka[i][j] = ManazerBlokov.vytvorBlok(blok, x, y);
            }
        }
    }

    /**
     *zapise cestu do intoveho pola
     */

    private void nastavCestu() {

        this.cestaInt.add(this.prvy);
        this.mapaVCislach[this.prvy[0]][this.prvy[1]] = 1;
        while ( (cestaInt.get(cestaInt.size() - 1 )[1] != this.posledny[1]) ||
                (this.cestaInt.get(this.cestaInt.size() - 1 )[0] != this.posledny[0])) {
            this.cesta.add(this.mapa[cestaInt.get(cestaInt.size() - 1)[0]][cestaInt.get(cestaInt.size() - 1)[1]]);
            cestaInt.add(najdiDalsiIntVIntovomPoli(cestaInt.get(cestaInt.size() - 1), this.cestaInt, this.mapaVCislach));
        }
    }
    public ArrayList<Pozicia> getBody() {
        return body;
    }

    /**
     *hlada dalsiu susednu cestu v poliIntov ktora este nieje zapisana v ceste, pripadne ak sa najde viac susedov tak metoda vrati null
     */

    public static int[] najdiDalsiIntVIntovomPoli(int[] aktualny, ArrayList<int[]> cestaVInt, int[][] mapaVCislach) {
        int[] doc = new int[2];
        int suseda = 0;
        if (aktualny[0] +1 >= 0  && aktualny[0] +1 < mapaVCislach.length && (mapaVCislach[aktualny[0] +1][aktualny[1] ]== 1
                ||  mapaVCislach[aktualny[0] +1][aktualny[1] ]== 3) && !Mapa.nachadzasaSaVIntCeste(aktualny[0] +1, aktualny[1],cestaVInt)) {
            doc[0] = aktualny[0] +1;
            doc[1] = aktualny[1];
            suseda++;
        }
        if (aktualny[0] -1 >= 0  && aktualny[0] -1 < mapaVCislach.length && (mapaVCislach[aktualny[0] - 1][aktualny[1] ]== 1
                || mapaVCislach[aktualny[0] - 1][aktualny[1] ]== 3 )&& !Mapa.nachadzasaSaVIntCeste(aktualny[0] -1, aktualny[1],cestaVInt)) {
            doc[0] = aktualny[0] - 1;
            doc[1] = aktualny[1];
            suseda++;
        }
        if (aktualny[1] +1 >= 0  && aktualny[1] +1 < mapaVCislach[0].length && (mapaVCislach[aktualny[0] ][aktualny[1] +1]== 1
                || mapaVCislach[aktualny[0] ][aktualny[1] +1]== 3)  && !Mapa.nachadzasaSaVIntCeste(aktualny[0] , aktualny[1] +1,cestaVInt)) {
            doc[0] = aktualny[0];
            doc[1] = aktualny[1] + 1;
            suseda++;
        }
        if (aktualny[1] - 1 >= 0  && aktualny[1] - 1 < mapaVCislach[0].length && (mapaVCislach[aktualny[0] ][aktualny[1]- 1 ]== 1
                || mapaVCislach[aktualny[0] ][aktualny[1]- 1 ]== 3)  && !Mapa.nachadzasaSaVIntCeste(aktualny[0] , aktualny[1] - 1,cestaVInt)) {
            doc[0] = aktualny[0];
            doc[1] = aktualny[1] - 1;
            suseda++;
        }
        if (suseda != 1) {
            return null;
        }
        return doc;
    }

    /**
     *zisti ci sa int uz nachadza v ceste
     */

    public static boolean nachadzasaSaVIntCeste(int i, int j,ArrayList<int[]> cestaInt){
        for (int[] ints :
                cestaInt) {
            if (ints[0] == i && ints[1] == j) {
                return true;
            }
        }
        return false;
    }

}




