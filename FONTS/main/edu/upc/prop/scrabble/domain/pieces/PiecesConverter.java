package edu.upc.prop.scrabble.domain.pieces;

import edu.upc.prop.scrabble.data.pieces.Piece;

// CASOS CATALÀ: L·L, NY
// CASOS CASTELLÀ: LL, RR, CH
// CASOS ANGLÈS: cap
public class PiecesConverter {
    public Piece[] run(String word) {
        Piece[] pieces = new Piece[word.length()];
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (c == 'L' && i < word.length() - 1 && word.charAt(i + 1) == '·') {   // CATALÀ -> L.L
                pieces[i] = new Piece(word.substring(i, i+3), 0);
                i += 3;
            }
            else if (c == 'N' && i < word.length() - 1 && word.charAt(i + 1) == 'Y') { // CATALÀ -> NY
                pieces[i] = new Piece(word.substring(i, i+2), 0);
                i += 2;
            }
            else if (c == 'R' && i < word.length() - 1 && word.charAt(i + 1) == 'R') {  //CASTELLÀ -> RR
                pieces[i] = new Piece(word.substring(i, i+2), 0);
                i += 2;
            }
            else if (c == 'L' && i < word.length() - 1 && word.charAt(i + 1) == 'L') {  //CASTELLÀ -> RR
                pieces[i] = new Piece(word.substring(i, i+2), 0);
                i += 2;
            }
            else if (c == 'C' && i < word.length() - 1 && word.charAt(i + 1) == 'H') {  //CASTELLÀ -> RR
                pieces[i] = new Piece(word.substring(i, i+2), 0);
                i += 2;
            }
            else{
                pieces[i] = new Piece(Character.toString(c), 0);
            }
        }
        return pieces;
    }
}
