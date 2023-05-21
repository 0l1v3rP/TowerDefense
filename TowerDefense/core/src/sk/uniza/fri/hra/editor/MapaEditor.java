package sk.uniza.fri.hra.editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import sk.uniza.fri.hra.layout.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class MapaEditor {
    private int[][] mapaVCislach;
    private Blok[][] mapaVBlokoch;

    private final int VYSKA_V_BLOKOCH = 16;
    private final int SIRKA_V_BLOKOCH = 20;
    private boolean mamStart;
    private boolean mamEnd;
    private int[] start;
    private int[] end;

    private static MapaEditor instance = null;
    private ArrayList<int[]> cesta;


    public MapaEditor() {
        this.mapaVBlokoch = new Blok[VYSKA_V_BLOKOCH][SIRKA_V_BLOKOCH];
        this.mapaVCislach = new int[VYSKA_V_BLOKOCH][SIRKA_V_BLOKOCH];

        this.polozMenu();

        this.start = new int[2];
        this.end = new int[2];


    }

    public static MapaEditor getInstanceMapaEditor()
    {
        if (instance == null)
            instance = new MapaEditor();
        return instance;
    }

    /**
     *spodne dva riaky blokov nastavy na menu
     */


    public void polozMenu() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < this.mapaVBlokoch[0].length; j++) {
                int x = j * (Gdx.graphics.getWidth() / this.mapaVBlokoch[0].length);
                int y = i * (Gdx.graphics.getHeight() / this.mapaVBlokoch.length);
                System.out.println(y +  " " + x);
                this.mapaVBlokoch[i][j] = new BlokMenu(x,y);
                this.mapaVCislach[this.VYSKA_V_BLOKOCH -1- i][j] = 4;
            }
        }
    }

    /**
     *skontroluje ci mozem polozit blok(ak blok je zaciatocny alelbo koncovy tak smie byt vytvorena len jedna instancia bloku,  ak
     *  blok neni rovny null(na tom mieste sa nachadza uz iny blok)) ak nie tak exitnem, inak polozim blok v blokovom poli a
     *  zaktualizujem ciselnu reprezentaciu mapy
     */


    public void pridajBlok (double x, double y, Blok blok){
        if (mozemPolozitAkZaciatocny(x, y, blok)) return;
        if (mozemPolozitAkKoncovy(x, y, blok)) return;
        if (this.nachadzamSa(x,  y ) != null) {
            return;
        }

            int pomX = this.vypocitajIndexXPodlaSuradnice(x) * (Gdx.graphics.getWidth() / this.mapaVBlokoch[0].length);
            int pomY = this.vypocitajIndexYPodlaSuradnice(y) * (Gdx.graphics.getHeight() / this.mapaVBlokoch.length);

            this.mapaVBlokoch[this.vypocitajIndexYPodlaSuradnice(y)][this.vypocitajIndexXPodlaSuradnice(x)] =
                    ManazerBlokov.vytvorBlok(ManazerBlokov.getCiselnuReprezentaciuBloku(blok), pomX, pomY);

            this.mapaVCislach[this.VYSKA_V_BLOKOCH - 1 - this.vypocitajIndexYPodlaSuradnice(y)]
                    [this.vypocitajIndexXPodlaSuradnice(x)] = ManazerBlokov.getCiselnuReprezentaciuBloku(blok);


    }

    private boolean mozemPolozitAkKoncovy(double x, double y, Blok blok) {
        if (blok instanceof BlokKoncovy) {
            if (this.mamEnd) {
                return true;
            } else {
                this.end[0] = this.VYSKA_V_BLOKOCH - 1 - this.vypocitajIndexYPodlaSuradnice(y);
                this.end[1] = this.vypocitajIndexXPodlaSuradnice(x);
                this.mamEnd = true;
            }
        }
        return false;
    }

    private boolean mozemPolozitAkZaciatocny(double x, double y, Blok blok) {
        if (blok instanceof BlokZaciatocny) {
            if (this.mamStart) {
                return true;
            } else {
                this.start[0] = this.VYSKA_V_BLOKOCH - 1 - this.vypocitajIndexYPodlaSuradnice(y);
                this.start[1] = this.vypocitajIndexXPodlaSuradnice(x);
                this.mamStart = true;
            }
        }
        return false;
    }

    /**
     *odstra blok pokial blok nieje menu
     */


    public void odstran(double x, double y) {

        if (this.nachadzamSa(x,  y )  instanceof BlokZaciatocny) {
                this.mamStart = false;
        }

        if (this.nachadzamSa(x,  y )  instanceof BlokKoncovy) {
                this.mamEnd = false;
        }

        if (this.nachadzamSa(x,  y ) != null && !(this.nachadzamSa(x,  y ) instanceof BlokMenu)) {
            this.mapaVBlokoch[this.vypocitajIndexYPodlaSuradnice(y)][this.vypocitajIndexXPodlaSuradnice(x)] = null;
            this.mapaVCislach[this.VYSKA_V_BLOKOCH - 1 - this.vypocitajIndexYPodlaSuradnice(y)]
                    [this.vypocitajIndexXPodlaSuradnice(x)] = 0;
        }
    }

    /**
     *vykresli mriezku
     */



    public void vykresli(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin();
        shapeRenderer.set(ShapeRenderer.ShapeType.Line);
        for (int i = 2; i < this.VYSKA_V_BLOKOCH; i++) {
            shapeRenderer.line(0,i * ((float)Gdx.graphics.getHeight() / this.VYSKA_V_BLOKOCH),Gdx.graphics.getWidth(),
                    i * ((float)Gdx.graphics.getHeight() / this.VYSKA_V_BLOKOCH));
        }

        for (int i = 0; i < this.SIRKA_V_BLOKOCH; i++) {
            shapeRenderer.line(i * ((float)Gdx.graphics.getWidth() / this.SIRKA_V_BLOKOCH),
                    2 * ((float)Gdx.graphics.getHeight() / this.VYSKA_V_BLOKOCH),i * ((float)Gdx.graphics.getWidth() / this.SIRKA_V_BLOKOCH),
                    Gdx.graphics.getHeight());
        }

    shapeRenderer.end();
    }

    /**
     *vykresli bloky
     */


    public void vykresli(SpriteBatch spriteBatch) {

        spriteBatch.begin();
        for (Blok[] vBlokoch : this.mapaVBlokoch) {
            for (int j = 0; j < this.mapaVBlokoch[0].length; j++) {
                if (vBlokoch[j] != null) {
                    vBlokoch[j].vykresli(spriteBatch);
                }
            }
        }
        spriteBatch.end();
    }

    /**
     *vypocitaj j v int[i][j] podla suradnic
     */


    private int vypocitajIndexXPodlaSuradnice(double x) {
        return (int)(x/(Gdx.graphics.getWidth() / this.mapaVBlokoch[0].length));
    }

    /**
     *vypocitaj i v int[i][j] podla suradnic
     */

    private int  vypocitajIndexYPodlaSuradnice(double y) {
        return (int)(y/(Gdx.graphics.getHeight() / this.mapaVBlokoch.length)) ;
    }


    private Blok nachadzamSa(double x, double y) {
        return this.mapaVBlokoch[this.vypocitajIndexYPodlaSuradnice(y)][this.vypocitajIndexXPodlaSuradnice(x)];
    }

    /**
     *ak je cesta v pohode nacita ciselnu reprezentaciu mapy do predpridpraveneho suboru
     */

    public boolean nacitajDoSuboru() throws IOException {
        if (!cestaJeOk()) {
            return false;
        }
        File file = new File("TowerDefense/assets/mapy/mapa.txt");
        PrintWriter printWriter = new PrintWriter(file);
        for (int[] vCislach : this.mapaVCislach) {
            for (int j = 0; j < this.mapaVCislach[0].length; j++) {
                printWriter.print(vCislach[j]);

                printWriter.print(" ");
            }
            printWriter.println();
        }
        printWriter.close();
        return true;
    }

    /**
     *skontroluje ci je vysledna cesta dobra, t.j cesta je suvisla, ma start a koniec, nema viac alternatyvnych ciest
     */

    private boolean cestaJeOk() {
        this.cesta = new ArrayList<>();
        if (!this.mamStart || !this.mamEnd) {
            return false;
        }
        int[] aktualny = this.start;
        do {
            this.cesta.add(aktualny);
            aktualny = Mapa.najdiDalsiIntVIntovomPoli(aktualny,this.cesta,this.mapaVCislach);
            if (aktualny == null) {
                return false;

            }
        }while((aktualny[0] != this.end[0] || aktualny[1] != this.end[1]));
            return true;
    }
}


