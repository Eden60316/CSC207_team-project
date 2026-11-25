package use_case.filter_recipe;

public interface FilterRecipeOutputBoundary {
    void prepareSuccessView(FilterRecipeOutputData outputData);
    void prepareFailView(String errorMessage);
}
