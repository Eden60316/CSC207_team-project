package interface_adapter.search_recipe;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class SearchRecipeViewModel {

    private SearchRecipeState state = new SearchRecipeState();

    private final PropertyChangeSupport support =
            new PropertyChangeSupport(this);

    public SearchRecipeState getState() {
        return state;
    }

    public void setState(SearchRecipeState state) {
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
