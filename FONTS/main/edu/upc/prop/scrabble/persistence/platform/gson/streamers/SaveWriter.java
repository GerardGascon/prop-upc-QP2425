package edu.upc.prop.scrabble.persistence.platform.gson.streamers;

import edu.upc.prop.scrabble.persistence.runtime.interfaces.ISaveWriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Classe encarregada d'escriure dades en fitxers de guardat de la partida.
 * <p>
 * Estén la classe abstracta SaveDataStreamer per obtenir la ruta absoluta
 * i implementa la interfície ISaveWriter per a l'escriptura de fitxers.
 * </p>
 *
 * @author Gerard Gascón
 */
public class SaveWriter extends SaveDataStreamer implements ISaveWriter {
    /**
     * Escriu el contingut proporcionat en un fitxer.
     * <p>
     * Si hi ha algun error durant l'escriptura, imprimeix un missatge d'error.
     * </p>
     *
     * @param data     Dades a escriure en format cadena.
     * @param fileName Nom del fitxer on s'escriuran les dades.
     */
    @Override
    public void write(String data, String fileName) {
        File path = getAbsolutePath(fileName);

        try {
            Files.writeString(path.toPath(), data);
        } catch (IOException e) {
            System.err.println("Error writing file: " + path + " - " + e.getMessage());
        }
    }
}
