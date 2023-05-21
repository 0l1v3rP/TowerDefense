package sk.uniza.fri.hra;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import sk.uniza.fri.hra.enumy.STAV_HRY;
import sk.uniza.fri.hra.layout.Mapa;
import sk.uniza.fri.hra.stavy.MenezerStavov;
import sk.uniza.fri.hra.veze.Kanon;
import sk.uniza.fri.hra.veze.LadovaVeza;
import sk.uniza.fri.hra.veze.OhnivaVeza;
import sk.uniza.fri.hra.veze.Veza;

/**
 *tlacidla na pridavanie vezi
 */


public class HracieMenu {
    private ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;
    private Stage stage;
    private Veza ghost;
    private ZoznamVezi zoznamVezi;
    private boolean mozemDelete;
    private MenezerStavov mS;
    private Mapa mapa;
    public HracieMenu(Mapa mapa, ShapeRenderer shapeRenderer, SpriteBatch spriteBatch, ZoznamVezi zoznamVezi, MenezerStavov menezerStavov) {
        this.shapeRenderer = shapeRenderer;
        this.spriteBatch = spriteBatch;
        this.stage = new Stage();
        this.zoznamVezi = zoznamVezi;
        this.ghost = null;
        this.mozemDelete = false;
        this.mapa = mapa;
        this.mS = menezerStavov;

        Table table = new Table();
        table.setFillParent(true);
        table.bottom();

        Skin skin = new Skin(Gdx.files.internal("terra-mother/skin/terra-mother-ui.json"));

        TextureRegionDrawable kanon = new TextureRegionDrawable(new TextureRegion(Kanon.texture()));

        TextureRegionDrawable ohnivaVeza = new TextureRegionDrawable(new TextureRegion(OhnivaVeza.texture()));

        TextureRegionDrawable ladovaVeza = new TextureRegionDrawable(new TextureRegion(sk.uniza.fri.hra.veze.LadovaVeza.texture()));

        TextureRegionDrawable delete = new TextureRegionDrawable(new TextureRegion(new Texture("DELETE.png")));

        ImageButton vytvorOhnivuVezu = new ImageButton(ohnivaVeza);
        table.add(vytvorOhnivuVezu);

        ImageButton vytvorLadovuVezu = new ImageButton(ladovaVeza);
        table.add(vytvorLadovuVezu);

        ImageButton vytvorKanon = new ImageButton(kanon);
        table.add(vytvorKanon);

        ImageButton deleteButton = new ImageButton(delete);
        deleteButton.setPosition(400, 0);


        vytvorKanon.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                HracieMenu.this.ghost = new Kanon((int) x, (int) y);
            }
        });

        vytvorLadovuVezu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                HracieMenu.this.ghost = new LadovaVeza((int) x, (int) y);

            }
        });

        vytvorOhnivuVezu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                HracieMenu.this.ghost = new OhnivaVeza((int) x, (int) y);
            }
        });

        deleteButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                HracieMenu.this.ghost = null;
                HracieMenu.this.mozemDelete = true;
            }
        });

        final TextButton menuButton = new TextButton("MENU", skin, "default");
        menuButton.setSize(40, 20);
        menuButton.setPosition(5, 10);

        menuButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                HracieMenu.this.mS.setAktualnyStav(STAV_HRY.V_MENU);
            }
        });
        this.stage.addActor(deleteButton);
        this.stage.addActor(menuButton);
        this.stage.addActor(table);
    }

    /**
     * stavia veze
     */


    public void render() {
        Gdx.input.setInputProcessor(this.stage);
        this.spriteBatch.begin();

        pohniVezuAkSaTiDa();

        polozVezu();

        this.stage.draw();
        this.spriteBatch.end();
        this.shapeRenderer.begin();
        if (this.ghost != null) {
            this.ghost.vykresli(this.shapeRenderer);
        }
        this.shapeRenderer.end();

        if (this.mozemDelete) {
            this.delete();
        }

    }

    private void pohniVezuAkSaTiDa() {
        if (this.ghost != null) {
            this.ghost.setxSuradnica(Gdx.input.getX());
            this.ghost.setySuradnica(Gdx.graphics.getHeight() - Gdx.input.getY());
            this.ghost.vykresli(spriteBatch);
        }
    }

    /**
     * poloz vezu na ktoru kliknes za predpokladu ze si predtym klikol na delete ikonku a
     */


    private void polozVezu() {
        if (Gdx.input.isTouched() && this.ghost != null
                && this.ghost.mozemPolozit(this.mapa, Gdx.input.getX() - (float)this.ghost.getTexture().getWidth() / 2,
                Gdx.graphics.getHeight() - Gdx.input.getY() - (float)this.ghost.getTexture().getHeight() / 2)) {
            this.zoznamVezi.pridajVezu(this.ghost);
            GoldSystem.getInstance().pridajGoldy(-this.ghost.getCena());
            this.ghost = null;
        }
    }

    /**
     * odstran vezu na ktoru kliknes za predpokladu ze si predtym klikol na delete ikonku
     */

    private void delete() {
        Veza odstranVezu = null;
        for (Veza v :
                this.zoznamVezi.getZoznamVezi()) {
            if (v.klikolSomMySomNaVezu()) {
                odstranVezu = v;
                break;
            }
        }
            if (odstranVezu != null) {
                GoldSystem.getInstance().pridajGoldy(odstranVezu.getCena());
                this.zoznamVezi.odstranVezu(odstranVezu);
                this.mozemDelete = false;
            }
    }
}

