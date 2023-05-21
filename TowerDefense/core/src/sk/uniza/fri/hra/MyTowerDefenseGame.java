package sk.uniza.fri.hra;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.utils.ScreenUtils;
import sk.uniza.fri.hra.enumy.STAV_HRY;
import sk.uniza.fri.hra.stavy.MenezerStavov;


public class MyTowerDefenseGame extends ApplicationAdapter{
	private MenezerStavov menezerStavov;
	@Override
	public void create () {
		this.menezerStavov = new MenezerStavov();
		this.menezerStavov.setAktualnyStav(STAV_HRY.V_MENU);
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		this.menezerStavov.render();
	}
	
	@Override
	public void dispose () {
	}
}
