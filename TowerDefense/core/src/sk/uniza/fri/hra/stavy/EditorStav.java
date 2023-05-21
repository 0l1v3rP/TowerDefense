package sk.uniza.fri.hra.stavy;


import sk.uniza.fri.hra.editor.EditorMenu;
import sk.uniza.fri.hra.editor.MapaEditor;


public class EditorStav extends Stav{
    private MapaEditor mapaEditor;
    private EditorMenu editorMenu;
    public EditorStav(final MenezerStavov menezerStavov) {
        super(menezerStavov);
        this.mapaEditor = MapaEditor.getInstanceMapaEditor();
        this.editorMenu = new EditorMenu(menezerStavov);
    }

    @Override
    public void render() {
        this.mapaEditor.vykresli(super.getSr());
        this.mapaEditor.vykresli(super.getSb());
        this.editorMenu.render(super.getSb(), super.getBitmapFont());

    }
}
