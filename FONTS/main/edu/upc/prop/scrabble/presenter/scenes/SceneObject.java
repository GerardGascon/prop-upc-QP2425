package edu.upc.prop.scrabble.presenter.scenes;

/**
 * Classe base abstracta que representa un objecte dins d'una escena.
 * <p>
 * Aquesta classe proporciona funcionalitats bàsiques per habilitar, deshabilitar i processar objectes
 * dins l'escena. També gestiona la integració amb el sistema de gestió d'escenes, cosa que permet la instanciació
 * i destrucció d'objectes segons sigui necessari.
 * </p>
 *
 * @author Gerard Gascón
 */
public abstract class SceneObject {
    /**
     * Indica si l'objecte està habilitat (actiu) o no.
     */
    private boolean enabled;

    /**
     * Escena a la qual pertany aquest objecte.
     */
    private Scene scene;

    /**
     * Crea una instància d'objecte d'escena
     */
    public SceneObject() {

    }

    /**
     * Assigna l'escena a la qual pertany aquest objecte.
     *
     * @param scene L'escena a associar amb aquest objecte
     */
    void setScene(Scene scene) {
        this.scene = scene;
    }

    /**
     * Deshabilita l'objecte i executa la lògica associada a la deshabilitació.
     * Aquest mètode es crida quan l'objecte és deshabilitat des de l'escena.
     *
     * @see #onDisable()
     */
    public final void disable() {
        enabled = false;
        onDisable();
    }

    /**
     * Habilita l'objecte i executa la lògica associada a l'habilitació.
     * Aquest mètode es crida quan l'objecte és habilitat dins l'escena.
     *
     * @see #onEnable()
     */
    public final void enable() {
        enabled = true;
        onEnable();
    }

    /**
     * Comprova si l'objecte està habilitat.
     *
     * @return <b>true</b> si l'objecte està habilitat, <b>false</b> en cas contrari
     */
    public final boolean isEnabled() {
        return enabled;
    }

    /**
     * Mètode que es crida per processar l'objecte durant l'actualització de l'escena.
     * Pot ser sobreescrit per les subclasses per implementar comportaments específics.
     *
     * @param delta Diferència de temps respecte a la darrera crida (en segons)
     */
    public void onProcess(float delta) {
    }

    /**
     * Mètode cridat quan l'objecte és desenganxat de l'escena.
     * Les subclasses poden sobreescriure aquest mètode per gestionar el procés de desenganxament.
     */
    public void onDetach() {
    }

    /**
     * Mètode cridat quan l'objecte és habilitat. Pot ser sobreescrit per implementar
     * comportaments personalitzats quan l'objecte s'habilita dins l'escena.
     */
    protected void onEnable() {
    }

    /**
     * Mètode cridat quan l'objecte és deshabilitat. Pot ser sobreescrit per implementar
     * comportaments personalitzats quan l'objecte es deshabilita dins l'escena.
     */
    protected void onDisable() {
    }

    /**
     * Instància un nou objecte d'escena dins de l'escena actual.
     *
     * @param <T> Tipus de l'objecte d'escena a instanciar
     * @param o   La classe de l'objecte d'escena a instanciar
     * @return L'objecte d'escena acabat d'instanciar
     * @see Scene#instantiate(Class)
     */
    protected <T extends SceneObject> T instantiate(Class<T> o) {
        return scene.instantiate(o);
    }

    /**
     * Destrueix un objecte d'escena i el elimina de l'escena actual.
     *
     * @param o L'objecte d'escena a destruir
     * @see Scene#destroy(SceneObject)
     */
    protected final void destroy(SceneObject o) {
        scene.destroy(o);
    }
}
