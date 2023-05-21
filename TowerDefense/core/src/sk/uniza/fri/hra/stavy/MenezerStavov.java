package sk.uniza.fri.hra.stavy;
import sk.uniza.fri.hra.enumy.STAV_HRY;




public class MenezerStavov {
    private Stav aktualnyStav;
    public MenezerStavov() {
    }

    public void setAktualnyStav(STAV_HRY stav_hry) {

        switch (stav_hry) {
            case HRAJ:
                this.aktualnyStav = new HraciStav(this);
                break;
            case UKONCI:
                this.aktualnyStav = new UkoncenyStav(this);
                break;
            case V_MENU:
                this.aktualnyStav = new ZaciatocnyStav(this);
                break;
            case EDITOR:
                this.aktualnyStav = new EditorStav(this);
                break;
            default:
                break;
        }
    }

    public void render() {
        aktualnyStav.render();
    }
}
