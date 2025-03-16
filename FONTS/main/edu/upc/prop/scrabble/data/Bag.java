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

    // Booleà per comprovar si la bossa está buida
    public boolean isEmpty() {
        return bag.isEmpty();
    }

    // pre: s'ha eliminat la piece del taulell
    // post: s'afegeix de nou la fitxa a la bossa
    public void addPiece(Piece piece) {
        quantities.put(piece, quantities.getOrDefault(piece, 0) + 1);
        bag.add(piece);
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

    // pre: es vol guardar l'estat de la bossa
    // post: es retornen les peces que queden i les seves quantitats
    public Map<Piece, Integer> getAllPieces() {
        return this.quantities;
    }

    // pre: es vol carregar una bossa
    // post: es carrega la llista de les peces segons les seves quantitats
    public void fillTheBag(Map<Piece, Integer> quantities) {
        this.quantities = quantities;
        this.bag.clear();
        for (Map.Entry<Piece, Integer> entry : quantities.entrySet()) {
            Piece piece = entry.getKey();
            int n = entry.getValue();
            for (int i = 0; i < n; ++i) {
                this.bag.add(piece);
            }
        }
    }
}
