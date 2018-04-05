package GUI;

import java.util.ArrayList;

public abstract class AbstractModeleEcoutable {

    private ArrayList<ModelListener> listeners;

    public AbstractModeleEcoutable () {
        this.listeners = new ArrayList<> ();
    }

    public void addListener (ModelListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener (ModelListener listener) {
        this.listeners.remove(listener);
    }

    public void fireChange () {
        for (ModelListener listener : this.listeners) {
            listener.update(this);
        }
    }
}
