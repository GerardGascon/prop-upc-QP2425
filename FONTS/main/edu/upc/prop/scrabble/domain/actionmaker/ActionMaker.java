package edu.upc.prop.scrabble.domain.actionmaker;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.domain.pieces.PiecesConverter;

public abstract class ActionMaker {
    //si quiere poner pieza-------------------------------------------------
    //board??
    protected Player player;

    public ActionMaker(Player player) {
            this.player = player;
    }
    //MovementReader reader = new MovementReader();

    //movement verifier cuando lo haga la gina

    PiecesConverter pc = new PiecesConverter();

    //movement cleaner cuando lo haga la gina

    //piecesInHandGetter

    //evidentemente excepciones si algo no esta bien

    //WordPlacer =

    //si quiere robar------------------------------------

    //DrawReader cuando lo haga la gina
    
    //PieceDrawer drawer = new PieceDrawer(bolsa);
}
