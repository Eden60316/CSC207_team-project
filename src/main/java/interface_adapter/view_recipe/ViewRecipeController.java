
package interface_adapter.view_recipe;

import use_case.view_recipe.ViewRecipeInputBoundary;
import use_case.view_recipe.ViewRecipeInputData;

public class ViewRecipeController {

    private final ViewRecipeInputBoundary interactor;

    public ViewRecipeController(ViewRecipeInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String recipeId) {
        ViewRecipeInputData inputData =
                new ViewRecipeInputData(recipeId);

        interactor.execute(inputData);
    }
}
