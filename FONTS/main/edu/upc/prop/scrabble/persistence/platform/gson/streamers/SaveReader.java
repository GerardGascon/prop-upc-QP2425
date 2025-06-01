package edu.upc.prop.scrabble.persistence.platform.gson.streamers;

import edu.upc.prop.scrabble.persistence.runtime.interfaces.ISaveReader;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * Classe encarregada de llegir dades des de fitxers de guardat de la partida.
 * <p>
 * Estén la classe abstracta SaveDataStreamer per obtenir la ruta absoluta
 * i implementa la interfície ISaveReader per a la lectura de fitxers.
 * </p>
 *
 * @author Gerard Gascón
 */
public class SaveReader extends SaveDataStreamer implements ISaveReader {
    /**
     * Crea una instància del lector d'arxius de guardat
     */
    public SaveReader() {
        super();
    }

    /**
     * Llegeix el contingut d'un fitxer de guardat en format text.
     * <p>
     * Utilitza la codificació UTF-8 per llegir el fitxer. Si el fitxer no existeix,
     * retorna null. En cas d'error durant la lectura, imprimeix un missatge d'error
     * i retorna null.
     * </p>
     *
     * @param fileName Nom del fitxer que es vol llegir.
     * @return El contingut del fitxer com a cadena, o null si no existeix o hi ha un error.
     */
    @Override
    public String read(String fileName) {
        File path = getAbsolutePath(fileName);
        if (!path.exists())
            return null;

        try {
            return Files.readString(path.toPath(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("Error llegint el fitxer: " + path + " - " + e.getMessage());
            return null;
        }
    }

    /**
     * Comprova que el fitxer de guardat existeixi.

     * @param fileName Nom del fitxer de guardat.
     * @return true si el fitxer existeix.
     */
    @Override
    public boolean exists(String fileName) {
        File path = getAbsolutePath(fileName);
        return path.exists();
    }

    /**
     * Elimina l'arxiu de guardat indicat.
     *
     * @param fileName nom de l'arxiu de guardat a eliminar.
     * @return <b>true</b> si l'arxiu existeix i s'ha eliminat amb èxit.
     */
    @Override
    public boolean delete(String fileName) {
        File path = getAbsolutePath(fileName);
        if (!path.exists())
            return false;

        try {
            return path.delete();
        } catch (Exception e) {
            System.err.println("Error borrant el fitxer: " + path + " - " + e.getMessage());
            return false;
        }
    }
}
