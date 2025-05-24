package edu.upc.prop.scrabble.presenter.localization;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Classe abstracta que s'encarrega de localitzar i llegir fitxers de localització.
 * <p>
 * La classe {@code LocaleReader} proporciona funcionalitats per trobar i llegir fitxers
 * de localització (per exemple, fitxers de diccionari). El mètode {@code readFileToString}
 * llegeix el contingut d'un fitxer especificat del sistema de fitxers local, utilitzant
 * una codificació concreta. Aquesta classe s'usa habitualment com a base per a altres
 * classes que necessiten carregar recursos localitzats.
 * </p>
 *
 * @author Gerard Gascón
 */
abstract class LocaleReader {
    /**
     * Obtén la ruta absoluta al fitxer especificat dins del directori de localització.
     * <p>
     * Aquest mètode construeix el camí absolut a partir de la ubicació del programa i
     * resol la ruta fins al directori {@code edu/upc/prop/scrabble/locales}, afegint el nom
     * del fitxer.
     * </p>
     *
     * @param fileName Nom del fitxer de localització.
     * @return Un objecte {@code File} amb la ruta absoluta al fitxer.
     * @throws RuntimeException Si no es pot resoldre l'URI de la ruta.
     */
    private File getAbsolutePath(String fileName) {
        try {
            File programRootPath = new File(LocaleReader.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            Path path = programRootPath.toPath()
                    .resolve("edu")
                    .resolve("upc")
                    .resolve("prop")
                    .resolve("scrabble")
                    .resolve("locales");

            return new File(path.toFile(), fileName);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Llegeix el contingut d'un fitxer i el retorna com una cadena de text.
     * <p>
     * Aquest mètode llegeix el fitxer indicat, utilitzant la codificació UTF-8, i retorna
     * tot el seu contingut com una cadena {@code String}. En cas d'error, retorna {@code null}
     * i imprimeix un missatge d'error a la sortida d'errors.
     * </p>
     *
     * @param filePath Nom del fitxer a llegir.
     * @return Contingut del fitxer en format cadena, o {@code null} si hi ha un error.
     */
    protected String readFileToString(String filePath) {
        File path = getAbsolutePath(filePath);
        try {
            return Files.readString(path.toPath(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("Error llegint el fitxer: " + path + " - " + e.getMessage());
            return null;
        }
    }
}
