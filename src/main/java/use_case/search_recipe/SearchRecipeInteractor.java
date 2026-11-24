package use_case.search_recipe;

import entity.Recipe;
import use_case.recipe.RecipeDataAccessInterface;

import java.util.ArrayList;
import java.util.List;

public class SearchRecipeInteractor implements SearchRecipeInputBoundary {

    private final RecipeDataAccessInterface recipeDAO;
    private final SearchRecipeOutputBoundary outputBoundary;

    public SearchRecipeInteractor(
            RecipeDataAccessInterface recipeDAO,
            SearchRecipeOutputBoundary outputBoundary) {
        this.recipeDAO = recipeDAO;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(SearchRecipeInputData inputData) {
        String keyword = inputData.getKeyword();
        if (keyword == null || keyword.trim().isEmpty()) {
            outputBoundary.prepareFailView("Please enter a keyword.");
            return;
        }

        try {
            List<Recipe> recipes = recipeDAO.searchByName(keyword.trim());
            if (recipes == null || recipes.isEmpty()) {
                outputBoundary.prepareFailView("No matching recipes were found.");
                return;
            }

            List<SearchRecipeOutputData.Match> matches = new ArrayList<>();
            for (Recipe recipe : recipes) {
                matches.add(new SearchRecipeOutputData.Match(
                        recipe.getID(),
                        recipe.getName(),
                        recipe.getImageUrl()));
            }
            outputBoundary.prepareSuccessView(new SearchRecipeOutputData(matches));
        } catch (RuntimeException ex) {
            outputBoundary.prepareFailView(
                    "Search results could not be loaded. Please try again.");
        }
    }
}
