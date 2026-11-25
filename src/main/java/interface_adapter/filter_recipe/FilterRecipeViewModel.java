package interface_adapter.filter_recipe;
import java.beans.*;

public class FilterRecipeViewModel {
    private FilterRecipeState state = new FilterRecipeState();
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public FilterRecipeState getState(){
        return state;
    }
    public void setState(FilterRecipeState state){
        this.state = state;
    }
    public void firePropertyChanged(){
        support.firePropertyChange("state", null, state);
    }
    public void addPropertyChangeListener(PropertyChangeListener listener){
        support.addPropertyChangeListener(listener);
    }
    public void removePropertyChangeListener(PropertyChangeListener listener){
        support.removePropertyChangeListener(listener);
    }
}
