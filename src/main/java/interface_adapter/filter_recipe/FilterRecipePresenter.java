package interface_adapter.filter_recipe;
import use_case.filter_recipe.*;

public class FilterRecipePresenter implements FilterRecipeOutputBoundary {
    private final FilterRecipeViewModel viewModel;
    public FilterRecipePresenter(FilterRecipeViewModel viewModel){
        this.viewModel = viewModel;
    }
    @Override
    public void prepareSuccessView(FilterRecipeOutputData outputData){
        FilterRecipeState state = new FilterRecipeState();
        state.setResults(outputData);
        state.setErrorMessage(null);
        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage){
        FilterRecipeState state = new FilterRecipeState();
        state.setResults(null);
        state.setErrorMessage(errorMessage);
        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }
}
