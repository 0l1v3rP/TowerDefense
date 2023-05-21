package sk.uniza.fri.hra.stavy;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import sk.uniza.fri.hra.enumy.STAV_HRY;

public class ZaciatocnyStav extends Stav {



        private Stage stage;
        private Skin skin;

        public ZaciatocnyStav(final MenezerStavov menezerStavov) {
            super(menezerStavov);
            skin = new Skin(Gdx.files.internal("terra-mother/skin/terra-mother-ui.json"));
            stage = new Stage();
            Table mainTable = new Table();
            mainTable.setFillParent(true);
            mainTable.center();
            final TextButton button = new TextButton("HRAJ", skin, "default");
            final TextButton button2 = new TextButton("UKONCI", skin, "default");
            final TextButton button3 = new TextButton("EDITOR", skin, "default");
            final Label label = new Label("TOWER DEFENSE", skin, "giygas");

            button.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y){
                    menezerStavov.setAktualnyStav(STAV_HRY.HRAJ);

                }
            });

            button3.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y){
                    menezerStavov.setAktualnyStav(STAV_HRY.EDITOR);

                }
            });

            button2.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y){
                    Gdx.app.exit();

                }
            });

            mainTable.add(label);
            mainTable.row();
            mainTable.add(button);
            mainTable.row();
            mainTable.add(button3);
            mainTable.row();
            mainTable.add(button2);
            mainTable.row();

            stage.addActor(mainTable);
        }

        @Override
        public void render() {
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.input.setInputProcessor(stage);
            super.getSb().begin();
            stage.draw();
            super.getSb().end();
        }
    }
