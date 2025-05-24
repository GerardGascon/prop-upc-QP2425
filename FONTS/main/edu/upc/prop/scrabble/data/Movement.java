package edu.upc.prop.scrabble.data;

import edu.upc.prop.scrabble.utils.Direction;

/**
 * Representa un moviment d'una paraula sobre el tauler de Scrabble,
 * incloent-hi les coordenades d'inici (x, y), la direcció del moviment,
 * i la paraula en si.
 *
 * <p><b>Nota:</b> La paraula ha d'estar en majúscules. Qualsevol lletra en minúscula
 * es considerarà com una fitxa en blanc al tauler.</p>
 *
 * @param word      La paraula que es col·loca al tauler. Ha d'estar en majúscules.
 * @param x         La coordenada x (posició horitzontal) on comença la paraula.
 * @param y         La coordenada y (posició vertical) on comença la paraula.
 * @param direction La direcció en què es col·loca la paraula (Vertical o Horitzontal).
 *
 * @author Gerard Gascón
 */
public record Movement(String word, int x, int y, Direction direction) {
}
