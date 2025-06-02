package edu.upc.prop.scrabble.persistence.platform.gson.streamers;

import edu.upc.prop.scrabble.persistence.runtime.interfaces.ISaveWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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
     * Crea una instància del lector d'arxius de guardat
     */
    public SaveWriter() {
        super();
    }

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
        Path path = getAbsolutePath(fileName).toPath();

        try {
            Files.createDirectories(path.getParent());
            Files.writeString(path, data);
        } catch (IOException e) {
            System.err.println("Error writing file: " + path + " - " + e.getMessage());
        }
    }
}
