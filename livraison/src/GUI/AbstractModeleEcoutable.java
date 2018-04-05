package GUI;

import java.util.ArrayList;

/**
  * Classe de gestion des listeners
  */
public abstract class AbstractModeleEcoutable {

    private ArrayList<ModelListener> listeners;

    /**
      * Constructeur de la classe
      */
    public AbstractModeleEcoutable () {
        this.listeners = new ArrayList<> ();
    }

    /**
      * Ajoute un listener
      * @param listener le listener à ajouter
      */
    public void addListener (ModelListener listener) {
        this.listeners.add(listener);
    }

    /**
      * retire un listener
      * @param listener le listener à retirer
      */
    public void removeListener (ModelListener listener) {
        this.listeners.remove(listener);
    }

    /**
      * Actualise tous les listeners qui sont dans la liste
      */
    public void fireChange () {
        for (ModelListener listener : this.listeners) {
            listener.update(this);
        }
    }
}
