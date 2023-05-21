package sk.uniza.fri.hra.jednotky;

import com.badlogic.gdx.graphics.Texture;
import sk.uniza.fri.hra.Pozicia;

import java.util.ArrayList;

public class Jerry extends Jednotka {

    private static final int CENA = 30;
    private static final int HP =30 ;
    private static final int DF = 20;
    private static final double RYCHLLOST = 1;


    public Jerry(ArrayList<Pozicia> body) {

        super( body, HP, RYCHLLOST, DF, CENA);
        super.texture = new Texture("jednotky/1N.png");

    }


}
