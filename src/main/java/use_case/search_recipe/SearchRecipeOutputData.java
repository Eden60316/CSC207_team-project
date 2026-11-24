package use_case.search_recipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchRecipeOutputData {

    private final List<Match> matches;

    public SearchRecipeOutputData(List<Match> matches) {
        this.matches = Collections.unmodifiableList(new ArrayList<>(matches));
    }

    public List<Match> getMatches() {
        return matches;
    }

    public static class Match {

        private final String recipeId;
        private final String name;
        private final String imageUrl;

        public Match(String recipeId, String name, String imageUrl) {
            this.recipeId = recipeId;
            this.name = name;
            this.imageUrl = imageUrl;
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
    }
}
