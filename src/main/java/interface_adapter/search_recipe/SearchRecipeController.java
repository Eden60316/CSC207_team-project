package interface_adapter.search_recipe;

import use_case.search_recipe.SearchRecipeInputBoundary;
import use_case.search_recipe.SearchRecipeInputData;

public class SearchRecipeController {

    private final SearchRecipeInputBoundary interactor;

    public SearchRecipeController(SearchRecipeInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String keyword) {
        interactor.execute(new SearchRecipeInputData(keyword));
    }
}
