package interface_adapter.search_recipe;

import use_case.search_recipe.SearchRecipeOutputData;

public class SearchRecipeState {

    private SearchRecipeOutputData results;
    private String errorMessage;

    public SearchRecipeOutputData getResults() {
        return results;
    }

    public void setResults(SearchRecipeOutputData results) {
        this.results = results;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
