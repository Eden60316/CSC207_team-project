
package use_case.view_recipe;

import use_case.recipe.RecipeDataAccessInterface;

public class ViewRecipeInteractor implements ViewRecipeInputBoundary {

    private final RecipeDataAccessInterface recipeDAO;
    private final ViewRecipeOutputBoundary outputBoundary;

    public ViewRecipeInteractor(
            RecipeDataAccessInterface recipeDAO,
            ViewRecipeOutputBoundary outputBoundary) {
        this.recipeDAO = recipeDAO;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(ViewRecipeInputData inputData) {
        String recipeId = inputData.getRecipeId();

        // TODO: Retrieve recipe using recipeDAO.
        // TODO: Send result through outputBoundary.
    }
}
