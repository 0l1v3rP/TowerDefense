package sk.uniza.fri.hra;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import sk.uniza.fri.hra.veze.Veza;

import java.util.ArrayList;

public class ZoznamVezi {


    private SpriteBatch spriteBatch;
    private ArrayList<Veza> zoznamVezi;

    public ZoznamVezi(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
        this.zoznamVezi = new ArrayList<>();
    }

    public void vykresli() {
        for (Veza veza:
             this.zoznamVezi) {
            veza.vykresli(this.spriteBatch);
        }
    }

    public void pridajVezu(Veza veza) {
        this.zoznamVezi.add(veza);
    }

    public ArrayList<Veza> getZoznamVezi() {
        return zoznamVezi;
    }

    public void odstranVezu(Veza veza) {
        this.zoznamVezi.remove(veza);
    }
}
