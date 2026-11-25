package use_case.filter_recipe;

public class FilterRecipeInputData {
    private final String category;
    private final DietaryPreference dietaryPreference;

    public FilterRecipeInputData(String category, DietaryPreference dietaryPreference){
        this.category = category;
        this.dietaryPreference = dietaryPreference;
    }

    public String getCategory(){return category;}

    public DietaryPreference getDietaryPreference(){return dietaryPreference;}
}
