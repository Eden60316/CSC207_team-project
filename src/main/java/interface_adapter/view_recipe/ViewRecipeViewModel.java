
package interface_adapter.view_recipe;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ViewRecipeViewModel {

    private ViewRecipeState state = new ViewRecipeState();

    private final PropertyChangeSupport support =
            new PropertyChangeSupport(this);

    public ViewRecipeState getState() {
        return state;
    }

    public void setState(ViewRecipeState state) {
        this.state = state;
    }

    public void firePropertyChanged() {
        support.firePropertyChange("state", null, state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}
