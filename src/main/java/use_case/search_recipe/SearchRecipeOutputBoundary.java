package use_case.search_recipe;

public interface SearchRecipeOutputBoundary {

    void prepareSuccessView(SearchRecipeOutputData outputData);

    void prepareFailView(String errorMessage);
}
