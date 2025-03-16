package edu.upc.prop.scrabble.data;
import java.lang.reflect.InaccessibleObjectException;
import java.util.*;

public class Bag {
    // atributs de la bossa Map<Peça, Quantitat>
    private Map<Piece, Integer> quantities;
    private List<Piece> bag;

    // constructora per defecte d'una bossa
    public Bag() {
        this.quantities = new HashMap<>();
        this.bag = new ArrayList<>();
    }

    //per carregar el .txt del llenguatge seleccionat
    //public loadLanguangeBag() {}

    public boolean isEmpty() {
        return bag.isEmpty();
    }

    // pre: s'ha eliminat la piece del taulell
    // post: s'afegeix de nou la fitxa a la bossa
    public void addPiece(Piece piece, int quanitity) {
        quantities.put(piece, quantities.getOrDefault(piece, 0) + quanitity);
        for (int i = 0; i < quanitity; i++) {
            bag.add(piece);
        }
    }

    // pre: es vol posar una fitxa al taulell
    // post: agafar una fitxa random de la bossa
    public Piece drawPiece() {
        if (bag.isEmpty()) {
            throw new InaccessibleObjectException("Bag is empty.");
        }
        Random rand = new Random();
        int i = rand.nextInt(bag.size());
        Piece piece = bag.get(i);
        bag.remove(i);
        quantities.remove(piece, quantities.get(piece) - 1);
        return piece;
    }


}
