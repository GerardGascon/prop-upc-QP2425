package edu.upc.prop.scrabble.presenter.terminal.players;

import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.turns.TurnResult;
import edu.upc.prop.scrabble.presenter.terminal.movements.DrawParser;
import edu.upc.prop.scrabble.presenter.terminal.movements.MovementParser;
import edu.upc.prop.scrabble.presenter.terminal.utils.Reader;

public class HumanPlayerObject extends PlayerObject {
    private TurnResult movementDesired;
    private boolean movementSelected = false;

    @Override
    public void startTurn() {
        super.startTurn();
        printInstructions();
        movementSelected = false;
    }

    private void printInstructions() {
        System.out.println("Type 'put' to place a word in the board.");
        System.out.println("Type 'draw' to draw some letters from the bag.");
        System.out.println("Type 'pass' to pass your turn without doing anything.");
    }

    @Override
    public void onProcess(float delta) {
        if (!isActive())
            return;

        String movementRaw = Reader.getInstance().readLine();
        if (movementRaw == null)
            return;

        if (!movementSelected) {
            selectMove(movementRaw);
            return;
        }

        if (movementDesired == TurnResult.Place){
            int previousScore = player.getScore();
            Movement move = MovementParser.parse(movementRaw);
            placePiece(move);
            System.out.println("Movement: " + movementRaw + " " + (player.getScore() - previousScore));
        }

        if (movementDesired == TurnResult.Draw){
            Piece[] piecesToDraw = DrawParser.parse(movementRaw);
            drawPieces(piecesToDraw);
        }
    }

    private void selectMove(String movementRaw) {
        switch (movementRaw) {
            case "put" -> {
                movementDesired = TurnResult.Place;
                movementSelected = true;
                System.out.println("Write your movement in this format: WORD AB");
                System.out.println("If a letter of a word is lowercase, it is interpreted as a blank tile.\n");
                System.out.println("A and B represent the coordinates where the word starts.");
                System.out.println("    If A is a number, the direction of the movement is vertical");
                System.out.println("    If A is a letter, the direction of the movement is horizontal.\n");
                printPieces();
            }
            case "draw" -> {
                movementDesired = TurnResult.Draw;
                movementSelected = true;
                System.out.println("Write the pieces you want to replace with some random ones.");
                System.out.println("    Separate them by commas.");
                printPieces();
            }
            case "pass" -> {
                movementDesired = TurnResult.Skip;
                movementSelected = true;
            }
            default -> printInstructions();
        }
    }
}
