package edu.upc.prop.scrabble.data;

import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.domain.board.WordGetter;
import edu.upc.prop.scrabble.domain.board.WordPlacer;
import edu.upc.prop.scrabble.domain.dawg.WordValidator;
import edu.upc.prop.scrabble.domain.pieces.PieceDrawer;
import edu.upc.prop.scrabble.domain.pieces.PiecesInHandVerifier;

public class IA {
    private Player bot;//para la mano
    private Board board;
    private PieceDrawer drawer;
    private WordPlacer wordPlacer;
    private WordGetter wordGetter;
    private PiecesInHandVerifier piecesInHandVerifier;
    //private DAWG se le pasa el dawg o q
    private WordValidator wordValidator;



    //TRATAR DE PONER:

    //si no hay ninguna ficha en el centro (es el 1r turno)
    //hacer que tenga que haber 1 ficha en el medio o que comienze siempre
    //de ahi etc

    //caso normal:
    //map<numerojugada,puntos> puntosporjugada --numero jugada que referencie al vector de jugadas y puntos pues eso
    //vector<vector<piece,pair>> jugadas ---> para cada jugada piezas y posicion de cada pieza o algo asi

    // caso "HORIZONTAL"
    //precompute cross-checks(se crea palabra perpendicular valida al poner una pieza de la mano)
    // y guardarselos como vector de bits
    //esto de guardarselo como bits ns pq en el papr dice que en ingles solo hay 26
    //letras asi que cada una representa 1 bit, aunq podria guardarse en bits pero respecto
    //las piezas de la mano mientras no cambien de orden ns

    //iterar por la board y guardarse vector<pair> (o matriz ns) con las coordenadas
    //de casillas anchor (casillas vacias adyacentes a fichas ya colocadas)

    //para cada fila
        //para cada columna
            //Si es una casilla anchor generar todas las combinaciones
            //posibles de jugadas respecto esa casilla

            //la parte izquierda tendra o fichas de nuestra mano o que ya esten
            //en board pero no las 2 (si son de la mano mirar que no hagan crosscheck)

            // La parte derecha se extiende desde el ancla utilizando un algoritmo de busqueda
            //basado en los prefijos que hemo ehcho antesde la izquierda añadiendo letras de la mano para
            //formar palabras validas

            //obvio si una es valida nos guardamos

            //caso "VERTICAL" (mirar vericalmente o hacerse una nueva board rotada no se)

    // Una vez generadas todas las jugadas, ordenarlas por puntuacion y seleccionar la mejor
    // Podemos hacer que segun la difiucultad pille la de la mitad en vez de la mejor o la peor etc

    //si no: (no hay NI UNA jugada fisicamente posible que casualidad)
    //pasar siempre || cambiar fichas

    //cambiar que sea las que valen menos puntos ya que es mas
    //probable que ya se encuentren en el tablero o con que criterio??

}
