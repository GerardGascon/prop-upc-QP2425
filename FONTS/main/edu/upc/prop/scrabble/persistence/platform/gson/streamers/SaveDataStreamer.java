package edu.upc.prop.scrabble.persistence.platform.gson.streamers;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;

/**
 * Classe abstracta encarregada de gestionar l'obtenció del camí absolut
 * per guardar fitxers de dades de la partida.
 * <p>
 * Proporciona un mètode per obtenir la ruta absoluta a un fitxer dins del directori
 * de guardat de dades del projecte.
 * </p>
 *
 * @author Gerard Gascón
 */
abstract class SaveDataStreamer {
    /**
     * Crea una instància d'un gestor dels arxius de guardat
     */
    public SaveDataStreamer() {

    }

    /**
     * Obté el camí absolut d'un fitxer de guardat dins del directori del projecte.
     * <p>
     * Construeix la ruta relativa al directori 'edu/upc/prop/scrabble/save' i
     * la combina amb el nom del fitxer proporcionat.
     * </p>
     *
     * @param fileName Nom del fitxer del qual es vol obtenir la ruta absoluta.
     * @return Un objecte File amb la ruta absoluta al fitxer de guardat.
     * @throws RuntimeException si hi ha un error en obtenir l'URI del directori del programa.
     */
    protected File getAbsolutePath(String fileName) {
        try {
            File programRootPath = new File(SaveWriter.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            Path path = programRootPath.toPath()
                    .resolve("edu")
                    .resolve("upc")
                    .resolve("prop")
                    .resolve("scrabble")
                    .resolve("save");

            return new File(path.toFile(), fileName);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
