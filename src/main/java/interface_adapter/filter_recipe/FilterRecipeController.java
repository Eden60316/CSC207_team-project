package interface_adapter.filter_recipe;
import use_case.filter_recipe.*;

public class FilterRecipeController {
    private final FilterRecipeInputBoundary interactor;
    public FilterRecipeController(FilterRecipeInputBoundary interactor){
        this.interactor = interactor;
    }

    public void execute(String category, DietaryPreference preference){
        FilterRecipeInputData inputData = new FilterRecipeInputData(category, preference);
        interactor.execute(inputData);
    }
}
