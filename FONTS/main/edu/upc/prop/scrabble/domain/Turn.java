package edu.upc.prop.scrabble.domain;

import edu.upc.prop.scrabble.data.Bag;
import edu.upc.prop.scrabble.data.Piece;
import edu.upc.prop.scrabble.data.Player;

import java.util.List;

public class Turn {
    private final List<Player> players;
    private int turnNumber;
    private final PieceDrawer drawer;
    private final WordPlacer wordPlacer;
    //Constructora
    public Turn(List<Player> players, PieceDrawer drawer, WordPlacer wordPlacer) {
        this.players = players;
        this.turnNumber = 0;
        this.drawer = drawer;
        this.wordPlacer = wordPlacer;
    }
    public void run(){

        ++turnNumber;
        //Li toca al jugador
        int PlayerTurn = turnNumber%players.size();
        Player currentPlayer = players.get(PlayerTurn);

        //Donem peça
        if (currentPlayer.getHand().size() < 7){
            Piece actualPiece = drawer.run();
            currentPlayer.getHand().add(actualPiece);
        }

        //El jugador si pot posa peça
        // DESPRES : if (existeix alguna paraula que puguis posar)



    }
}
