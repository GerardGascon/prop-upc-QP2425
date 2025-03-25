package edu.upc.prop.scrabble.domain.pieces;

import edu.upc.prop.scrabble.data.pieces.Piece;

import java.util.ArrayList;

// CASOS CATALÀ: L·L, NY
// CASOS CASTELLÀ: LL, RR, CH
// CASOS ANGLÈS: cap
public class PiecesConverter {
    public Piece[] run(String word) {
        ArrayList<Piece> pieces = new ArrayList<>();
        int i = 0;
        while (i < word.length()) {
            char c = word.charAt(i);
            if (c == 'L' && i < (word.length() - 2) && word.charAt(i + 1) == '·') {   // CATALÀ -> L.L
                pieces.add(new Piece(word.substring(i, i+3), 0));
                i += 3;
            }
            else if (c == 'N' && i < (word.length() - 1) && word.charAt(i + 1) == 'Y') { // CATALÀ -> NY
                pieces.add(new Piece(word.substring(i, i+2), 0));
                i += 2;
            }
            else if (c == 'R' && i < (word.length() - 1) && word.charAt(i + 1) == 'R') {  //CASTELLÀ -> RR
                pieces.add(new Piece(word.substring(i, i+2), 0));
                i += 2;
            }
            else if (c == 'L' && i < (word.length() - 1) && word.charAt(i + 1) == 'L') {  //CASTELLÀ -> LL
                pieces.add(new Piece(word.substring(i, i+2), 0));
                i += 2;
            }
            else if (c == 'C' && i < (word.length() - 1) && word.charAt(i + 1) == 'H') {  //CASTELLÀ -> CH
                pieces.add(new Piece(word.substring(i, i+2), 0));
                i += 2;
            }
            else{
                pieces.add(new Piece(Character.toString(c), 0));
                i += 1;
            }
        }
        return pieces.toArray(new Piece[0]);
    }
}
