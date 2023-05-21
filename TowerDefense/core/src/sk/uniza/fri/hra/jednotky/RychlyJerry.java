package sk.uniza.fri.hra.jednotky;

import com.badlogic.gdx.graphics.Texture;
import sk.uniza.fri.hra.Pozicia;

import java.util.ArrayList;

public class RychlyJerry extends  Jednotka{
    private static final int CENA = 20;
    private static final int HP = 15 ;
    private static final int DF = 10;
    private static final double RYCHLLOST = 2;


    public RychlyJerry(ArrayList<Pozicia> body) {
        super( body, HP, RYCHLLOST, DF, CENA);
        super.texture = new Texture("jednotky/1NZAMRZNUTY.png");
    }

}
