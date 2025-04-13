package edu.upc.prop.scrabble.presenter.terminal;

import edu.upc.prop.scrabble.data.properties.GameProperties;
import edu.upc.prop.scrabble.presenter.scenes.SceneManager;
import edu.upc.prop.scrabble.presenter.scenes.SceneObject;
import edu.upc.prop.scrabble.presenter.terminal.scenes.GameScene;
import edu.upc.prop.scrabble.presenter.terminal.utils.Reader;



public class GameCreator extends SceneObject {
    private final LanguageSetter languageSetter = new LanguageSetter();
    private final SizeSetter sizeSetter = new SizeSetter();
    private final PlayerSetter playerSetter = new PlayerSetter();


    public GameCreator() {
        //EN UN MUY FUTURO PONER EL HELP insturccion palabras
        languageSetter.print_interface();
    }

    enum State {
        Lang,
        Size,
        Player
    }
    State state = State.Lang;

    @Override
    public void onProcess(float delta) {

        String action = Reader.getInstance().readLine();
        if (action == null)
            return;
        if (action.equals("next")) {
            next();
        }
        if (action.equals("previous")) {
            previous();
        }
        if (action.equals("submit")) {
            submit();
        }
    }

    private void previous(){
        switch (state) {
            case Lang:
                languageSetter.previous();
                break;
            case Size:
                sizeSetter.previous();
                break;
            case Player:
                playerSetter.Decrease();
                break;
        }
    }

    private void next(){
        switch (state) {
            case Lang:
                languageSetter.next();
                break;
            case Size:
                sizeSetter.next();
                break;
            case Player:
                playerSetter.Increase();
                break;
        }
    }

    private void submit(){
        switch (state) {
            case Lang:
                sizeSetter.print_interface();
                state = State.Size;
                break;
            case Size:
                playerSetter.print_interface();
                state = State.Player;
                break;
            case Player:

                if (playerSetter.hasEnded())
                    loadGame();
                else {
                    playerSetter.switchMode();
                }
                break;
        }
    }

    private void loadGame(){
        GameProperties gameProperties = new GameProperties(languageSetter.getLanguage(), sizeSetter.getBoardSize(),
                                                            playerSetter.getRealPlayers(),playerSetter.getCpuPlayers());
        SceneManager.getInstance().load(GameScene.class,gameProperties);
    }
}
