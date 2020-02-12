
package observer;

/**
 * classe qui représente un écouteur d'un model
 */
public interface ListenableModel {
    
    /**
     * Ajoute un listener
     * @param l le listener a ajouter
     */
    void addModelListener(ModelListener l);
    
    /**
     * Retire un listener
     * @param l le listener a retirer
     */
    void removeModelListener(ModelListener l);
}
