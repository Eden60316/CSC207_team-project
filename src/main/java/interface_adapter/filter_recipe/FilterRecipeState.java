package interface_adapter.filter_recipe;
import use_case.filter_recipe.FilterRecipeOutputData;

public class FilterRecipeState {
    private FilterRecipeOutputData results;
    private String errorMessage;
    public FilterRecipeOutputData getResults(){return results;}
    public void setResults(FilterRecipeOutputData results){
        this.results = results;
    }
    public String getErrorMessage(){
        return errorMessage;
    }
    public void setErrorMessage(String message){
        this.errorMessage = message;
    }
}
