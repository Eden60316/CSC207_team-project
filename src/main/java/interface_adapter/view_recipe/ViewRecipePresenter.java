
package interface_adapter.view_recipe;

import use_case.view_recipe.ViewRecipeOutputBoundary;
import use_case.view_recipe.ViewRecipeOutputData;

public class ViewRecipePresenter implements ViewRecipeOutputBoundary {

    private final ViewRecipeViewModel viewModel;

    public ViewRecipePresenter(ViewRecipeViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(ViewRecipeOutputData outputData) {
        ViewRecipeState state = viewModel.getState();

        state.setRecipe(outputData);
        state.setErrorMessage(null);

        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        ViewRecipeState state = viewModel.getState();

        state.setRecipe(null);
        state.setErrorMessage(errorMessage);

        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }
}
