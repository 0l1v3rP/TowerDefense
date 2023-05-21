package sk.uniza.fri.hra.editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import sk.uniza.fri.hra.enumy.STAV_HRY;
import sk.uniza.fri.hra.layout.Blok;
import sk.uniza.fri.hra.layout.BlokCesta;
import sk.uniza.fri.hra.layout.BlokKoncovy;
import sk.uniza.fri.hra.layout.BlokZaciatocny;
import sk.uniza.fri.hra.stavy.MenezerStavov;
import java.io.IOException;

public class EditorMenu {
    private Stage stage;
    private Blok ghost;
    private boolean odstran;
    private boolean nepodariloSaNacitat;

    public EditorMenu(final MenezerStavov menezerStavov) {


        this.stage = new Stage();
        this.ghost = null;

        Table table = new Table();
        table.setFillParent(true);
        table.bottom();

        Skin skin = new Skin(Gdx.files.internal("terra-mother/skin/terra-mother-ui.json"));

        TextureRegionDrawable start = new TextureRegionDrawable(new TextureRegion(BlokZaciatocny.getTexture()));

        TextureRegionDrawable cesta = new TextureRegionDrawable(BlokCesta.getTextureRegion());

        TextureRegionDrawable koniec = new TextureRegionDrawable(new TextureRegion(BlokKoncovy.getTexture()));

        TextureRegionDrawable delete = new TextureRegionDrawable(new TextureRegion(new Texture("DELETE.png")));

        ImageButton vytvorStartBlok = new ImageButton(start);
        table.add(vytvorStartBlok);
        table.add();

        ImageButton vytvorCestaBlok = new ImageButton(cesta);
        table.add(vytvorCestaBlok);

        ImageButton vytvorKoniecBlok = new ImageButton(koniec);
        table.add(vytvorKoniecBlok);

        final ImageButton deleteButton = new ImageButton(delete);
        table.add(deleteButton);

        vytvorKoniecBlok.addListener(new ClickListener(){


            @Override
            public void clicked(InputEvent event, float x, float y){
                    EditorMenu.this.ghost = new BlokKoncovy((int )x, (int )y);


            }
        });

        vytvorCestaBlok.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){

                    EditorMenu.this.ghost = new BlokCesta( (int )x, (int )y);

            }
        });

        vytvorStartBlok.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){

                EditorMenu.this.ghost = new BlokZaciatocny( (int )x, (int )y);

            }
        });

        deleteButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                EditorMenu.this.ghost = null;
                EditorMenu.this.odstran = !EditorMenu.this.odstran;
                deleteButton.setColor(Color.GOLD);


            }
        });


        final TextButton button = new TextButton("SAVE", skin, "default");
        button.setSize(40,20);
        button.setPosition(5,0);
        button.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y){
                try {
                    EditorMenu.this.nepodariloSaNacitat = !MapaEditor.getInstanceMapaEditor().nacitajDoSuboru();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        final TextButton button2 = new TextButton("MENU", skin, "default");
        button2.setSize(40,20);
        button2.setPosition(5,25);
        button2.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y){
                menezerStavov.setAktualnyStav(STAV_HRY.V_MENU);
            }
        });


        this.stage.addActor(button);
        this.stage.addActor(button2);

        this.stage.addActor(table);
    }
    public void render(SpriteBatch spriteBatch, BitmapFont bitmapFont) {
        Gdx.input.setInputProcessor(this.stage);
        spriteBatch.begin();
        if (Gdx.input.isTouched() && this.odstran) {
            MapaEditor.getInstanceMapaEditor().odstran(Gdx.input.getX() ,Gdx.graphics.getHeight() - Gdx.input.getY());
        }

        if (this.ghost != null) {
            this.ghost.setxSuradnica(Gdx.input.getX());
            this.ghost.setySuradnica(Gdx.graphics.getHeight() - Gdx.input.getY());
            this.ghost.vykresli(spriteBatch);
        }

        if (Gdx.input.isTouched() &&  this.ghost != null) {
            MapaEditor.getInstanceMapaEditor().pridajBlok(Gdx.input.getX() ,Gdx.graphics.getHeight() - Gdx.input.getY() ,this.ghost);
        }

        if (this.nepodariloSaNacitat) {
            bitmapFont.draw(spriteBatch, "ZLA MAPA !!", 500, 35);
        }

        spriteBatch.end();

        this.stage.draw();

    }
}
