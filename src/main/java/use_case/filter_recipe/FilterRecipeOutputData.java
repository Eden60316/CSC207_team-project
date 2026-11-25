package use_case.filter_recipe;
import entity.Recipe;
import java.util.*;

public class FilterRecipeOutputData {
    public static class Match {
        private final String recipeID;
        private final String name;
        private final String imageUrl;
        private final String category;

        public Match(String recipeID, String name, String imageUrl, String category) {
            this.recipeID = recipeID;
            this.name = name;
            this.imageUrl = imageUrl;
            this.category = category;
        }

        public String getRecipeID() {
            return recipeID;
        }

        public String getName(){return name;}
        
        public String getImageUrl(){return imageUrl;}

        public String getCategory(){return category;}
    }
    private final List<Match> matches;
    public FilterRecipeOutputData(List<Match> matches){
        this.matches = Collections.unmodifiableList(new ArrayList<>(matches));
    }
    public List<Match> getMatches(){return matches;}
}
