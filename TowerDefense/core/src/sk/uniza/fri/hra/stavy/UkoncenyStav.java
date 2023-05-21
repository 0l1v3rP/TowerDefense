package sk.uniza.fri.hra.stavy;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import sk.uniza.fri.hra.enumy.STAV_HRY;


public class UkoncenyStav extends Stav  {


    private Stage stage;
    private Skin skin;

    public UkoncenyStav(final MenezerStavov menezerStavov) {
        super(menezerStavov);
        skin = new Skin(Gdx.files.internal("terra-mother/skin/terra-mother-ui.json"));
        stage = new Stage();

        //Create Table
        Table mainTable = new Table();
        //Set table to fill stage
        mainTable.setFillParent(true);
        //Set alignment of contents in the table.
        mainTable.center();

        final TextButton button = new TextButton("RST", skin, "default");
        final TextButton button2 = new TextButton("END", skin, "default");
        final Label label = new Label("GAME OVER", skin, "giygas");



        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                menezerStavov.setAktualnyStav(STAV_HRY.HRAJ);

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
        mainTable.add(button2);

        stage.addActor(mainTable);

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.input.setInputProcessor(stage);
        stage.draw();
    }
}
