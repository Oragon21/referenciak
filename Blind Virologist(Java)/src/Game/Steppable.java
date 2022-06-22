package Game;

/**
 * A léptethetőségért felelős osztály.
 * <p>Jelentőssége az ágensek lejárásánál és egyéb ütemezési feladatoknál van,
 * amiket state machine-en ábrázoltunk, így nem kerültek a tesztesetkbe.</p>
 */
public interface Steppable {

     void Step();
}
