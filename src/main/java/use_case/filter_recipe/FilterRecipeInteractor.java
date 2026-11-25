package use_case.filter_recipe;
import entity.*;
import use_case.recipe.RecipeDataAccessInterface;

import java.sql.Array;
import java.util.*;

public class FilterRecipeInteractor implements FilterRecipeInputBoundary {
    private static final String[] NON_VEGETARIAN_INGREDIENTS = {"beef", "pork", "chicken", "turkey", "duck", "lamb", "veal, " +
            "meat", "bacon", "ham", "sausage", "salami", "pepperoni", "prosciutto", "pancetta", "lard", "gelatin",
            "fish", "salmon", "tuna", "cod", "trout", "anchovy", "anchovies", "shrimp", "prawn",
    "prawns", "crab", "lobster", "oyster", "oysters", "clam", "clams", "mussel", "mussels",
    "squid", "seafood", "bone"};

    private static final String[] NON_VEGAN_INGREDIENTS = {"egg", "eggs", "milk", "cream", "butter",
    "cheese", "yogurt", "yoghurt", "ghee", "honey", "whey", "casein", "mayonnaise", "custard"};

    private final RecipeDataAccessInterface recipeDAO;
    private final FilterRecipeOutputBoundary outputBoundary;
    public FilterRecipeInteractor(RecipeDataAccessInterface recipeDAO, FilterRecipeOutputBoundary outputBoundary){
        this.recipeDAO = recipeDAO;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(FilterRecipeInputData inputData) {
        if (inputData == null) {
            outputBoundary.prepareFailView("No filter information provided");
            return;
        }
        String category = inputData.getCategory();
        if (category == null) {
            category = "";
        }
        category = category.trim();

        DietaryPreference preference =
                inputData.getDietaryPreference();

        if (category.isEmpty() && preference == DietaryPreference.ANY) {
            outputBoundary.prepareFailView("Please select a category or dietary preference");
            return;
        }

        try {
            List<Recipe> candidates = findCandidateRecipes(category, preference);
            List<FilterRecipeOutputData.Match> matches = new ArrayList<>();
            Set<String> seenIDs = new HashSet<>();

            for (Recipe candidate : candidates) {
                if (candidate == null || candidate.getID() == null || candidate.getID().trim().isEmpty()) {
                    continue;
                }
                String id = candidate.getID();
                if (!seenIDs.add(id)) {
                    continue;
                }
                Recipe recipe = candidate;
                if (preference != DietaryPreference.ANY) {
                    recipe = recipeDAO.getRecipeByID(id);
                    if (recipe == null || !matchesDiet(recipe, preference)) {
                        continue;
                    }
                }
                matches.add(new FilterRecipeOutputData.Match(recipe.getID(), recipe.getName(), recipe.getImageUrl(), recipe.getCategory()));
            }
            if (matches.isEmpty()) {
                outputBoundary.prepareFailView("No recipes match the selected filters");
                return;
            }
            outputBoundary.prepareSuccessView(new FilterRecipeOutputData(matches));
        } catch (RuntimeException ex) {
            outputBoundary.prepareFailView("Filtered recipes could not be loaded, please try again:(");
        }
    }
    private List<Recipe> findCandidateRecipes(String category, DietaryPreference preference){
        if (!category.isEmpty()){
            return nonNullList(recipeDAO.filterByCategory(category));
        }
        List<Recipe> candidates = new ArrayList<>();
        if (preference == DietaryPreference.VEGETARIAN){
            candidates.addAll(nonNullList(recipeDAO.filterByCategory("Vegetarian")));
        }
        candidates.addAll(nonNullList(recipeDAO.filterByCategory("Vegan")));
        return candidates;
    }
    private List<Recipe> nonNullList(List<Recipe> recipes){
        if (recipes == null){
            return new ArrayList<>();
        }
        return recipes;
    }
    private boolean matchesDiet(Recipe recipe, DietaryPreference preference){
        List<Ingredient> ingredients = recipe.getIngredients();
        if (ingredients == null || ingredients.isEmpty()){
            return false;
        }
        for (Ingredient ingredient: ingredients) {
            if (ingredient == null || ingredient.getName() == null) {
                return false;
            }
            String name = normalize(ingredient.getName());
            if (name.isEmpty()) {
                return false;
            }
        }
        return true;
    }
    private String normalize(String name){
        return name.toLowerCase(Locale.ROOT).replaceAll("[^a-z]+", "").trim();
    }

}
