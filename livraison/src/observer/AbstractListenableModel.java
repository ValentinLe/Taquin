
package observer;

import java.util.*;

/**
 * Classe de gestion des listeners
 */
public abstract class AbstractListenableModel implements ListenableModel {

    private List<ModelListener> listeners;

    /**
     * Construit une liste de listeners
     */
    public AbstractListenableModel() {
        listeners = new ArrayList<>();
    }

    /**
     * Ajoute un listener
     * @param l le listener à ajouter
     */
    @Override
    public void addModelListener(ModelListener l) {
        listeners.add(l);
    }

    /**
     * Retire un listener
     * @param l le listener à retirer
     */
    @Override
    public void removeModelListener(ModelListener l) {
        listeners.remove(l);
    }

    /**
     * Actualiste tous les listeners
     */
    protected void fireChange() {
	for (ModelListener l : listeners) {
	    l.update(this);
	}
    }

}
