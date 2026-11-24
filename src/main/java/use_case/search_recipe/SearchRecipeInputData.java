package use_case.search_recipe;

public class SearchRecipeInputData {

    private final String keyword;

    public SearchRecipeInputData(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }
}
