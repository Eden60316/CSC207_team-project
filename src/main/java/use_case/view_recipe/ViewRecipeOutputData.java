
package use_case.view_recipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ViewRecipeOutputData {

    private final String recipeId;
    private final String name;
    private final String imageUrl;
    private final String category;
    private final List<String> ingredients;
    private final List<String> measurements;
    private final String instructions;

    public ViewRecipeOutputData(
            String recipeId,
            String name,
            String imageUrl,
            String category,
            List<String> ingredients,
            List<String> measurements,
            String instructions) {

        this.recipeId = recipeId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.category = category;
        this.ingredients = Collections.unmodifiableList(
                new ArrayList<>(ingredients));
        this.measurements = Collections.unmodifiableList(
                new ArrayList<>(measurements));
        this.instructions = instructions;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public List<String> getMeasurements() {
        return measurements;
    }

    public String getInstructions() {
        return instructions;
    }
}
