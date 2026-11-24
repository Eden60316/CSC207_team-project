package interface_adapter.search_recipe;

import use_case.search_recipe.SearchRecipeOutputBoundary;
import use_case.search_recipe.SearchRecipeOutputData;

public class SearchRecipePresenter implements SearchRecipeOutputBoundary {

    private final SearchRecipeViewModel viewModel;

    public SearchRecipePresenter(SearchRecipeViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(SearchRecipeOutputData outputData) {
        SearchRecipeState state = viewModel.getState();

        state.setResults(outputData);
        state.setErrorMessage(null);

        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        SearchRecipeState state = viewModel.getState();

        state.setResults(null);
        state.setErrorMessage(errorMessage);

        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }
}
