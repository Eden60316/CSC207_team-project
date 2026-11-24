package use_case.search_recipe;

import entity.Ingredient;
import entity.Recipe;
import org.junit.jupiter.api.Test;
import use_case.recipe.RecipeDataAccessInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SearchRecipeInteractorTest {

    @Test
    public void blankKeywordAsksTheUserToEnterOne() {
        FakeRecipeDataAccess dataAccess = new FakeRecipeDataAccess();
        RecordingPresenter presenter = new RecordingPresenter();

        new SearchRecipeInteractor(dataAccess, presenter)
                .execute(new SearchRecipeInputData("   "));
        assertEquals("Please enter a keyword.", presenter.errorMessage);
        assertNull(dataAccess.lastKeyword);

        new SearchRecipeInteractor(dataAccess, presenter)
                .execute(new SearchRecipeInputData(null));
        assertEquals("Please enter a keyword.", presenter.errorMessage);
        assertNull(dataAccess.lastKeyword);
    }

    @Test
    public void noMatchesShowsNotFoundMessage() {
        FakeRecipeDataAccess dataAccess = new FakeRecipeDataAccess();
        RecordingPresenter presenter = new RecordingPresenter();

        new SearchRecipeInteractor(dataAccess, presenter)
                .execute(new SearchRecipeInputData("zzzz-no-such-meal"));

        assertEquals("No matching recipes were found.", presenter.errorMessage);
        assertNull(presenter.results);
        assertEquals("zzzz-no-such-meal", dataAccess.lastKeyword);
    }

    @Test
    public void matchingRecipesIncludeIdNameAndImage() {
        Recipe recipe = new Recipe(
                "52772",
                "Teriyaki Chicken Casserole",
                "Chicken",
                "Japanese",
                "Cook it.",
                "https://example.com/chicken.jpg",
                Collections.singletonList(new Ingredient("chicken", "1 kg")));
        FakeRecipeDataAccess dataAccess = new FakeRecipeDataAccess();
        dataAccess.results = Collections.singletonList(recipe);
        RecordingPresenter presenter = new RecordingPresenter();

        new SearchRecipeInteractor(dataAccess, presenter)
                .execute(new SearchRecipeInputData(" chicken "));

        assertNull(presenter.errorMessage);
        assertEquals("chicken", dataAccess.lastKeyword);
        assertEquals(1, presenter.results.getMatches().size());
        SearchRecipeOutputData.Match match = presenter.results.getMatches().get(0);
        assertEquals("52772", match.getRecipeId());
        assertEquals("Teriyaki Chicken Casserole", match.getName());
        assertEquals("https://example.com/chicken.jpg", match.getImageUrl());
    }

    @Test
    public void apiFailureLetsTheUserTryAgain() {
        FakeRecipeDataAccess dataAccess = new FakeRecipeDataAccess();
        dataAccess.failure = new IllegalStateException("TheMealDB request failed");
        RecordingPresenter presenter = new RecordingPresenter();

        new SearchRecipeInteractor(dataAccess, presenter)
                .execute(new SearchRecipeInputData("chicken"));

        assertEquals(
                "Search results could not be loaded. Please try again.",
                presenter.errorMessage);
        assertNull(presenter.results);
    }

    private static class RecordingPresenter implements SearchRecipeOutputBoundary {
        private SearchRecipeOutputData results;
        private String errorMessage;

        @Override
        public void prepareSuccessView(SearchRecipeOutputData outputData) {
            this.results = outputData;
            this.errorMessage = null;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            this.errorMessage = errorMessage;
            this.results = null;
        }
    }

    private static class FakeRecipeDataAccess implements RecipeDataAccessInterface {
        private List<Recipe> results = new ArrayList<>();
        private RuntimeException failure;
        private String lastKeyword;

        @Override
        public List<Recipe> searchByName(String keyword) {
            lastKeyword = keyword;
            if (failure != null) {
                throw failure;
            }
            return results;
        }

        @Override
        public List<Recipe> filterByCategory(String category) {
            return new ArrayList<>();
        }

        @Override
        public List<String> getCategories() {
            return new ArrayList<>();
        }

        @Override
        public Recipe getRecipeByID(String id) {
            return null;
        }
    }
}
