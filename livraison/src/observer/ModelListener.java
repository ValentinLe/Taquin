
package observer;

/**
 * listener indiquer aux listener que le model a changé
 */
public interface ModelListener {
    /**
     * Indique ce qui faut faire quand quelque chose a changé dans le model
     * @param source la source du changement, l'objet qui dit qu'il y a eu du
     * changement
     */
    void update(Object source);
}
