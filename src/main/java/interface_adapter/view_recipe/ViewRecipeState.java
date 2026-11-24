
package interface_adapter.view_recipe;

import use_case.view_recipe.ViewRecipeOutputData;

public class ViewRecipeState {

    private ViewRecipeOutputData recipe;
    private String errorMessage;

    public ViewRecipeOutputData getRecipe() {
        return recipe;
    }

    public void setRecipe(ViewRecipeOutputData recipe) {
        this.recipe = recipe;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
