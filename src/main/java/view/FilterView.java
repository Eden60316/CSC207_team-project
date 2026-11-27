package view;
import interface_adapter.filter_recipe.*;
import use_case.filter_recipe.DietaryPreference;

import javax.imageio.*;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.concurrent.*;
import java.util.function.*;

public class FilterView extends JPanel {
    private static final String[] CATEGORY_OPTIONS = {"Any Category", "Beef", "Breakfast",
    "Chicken", "Dessert", "Goat", "Lamb", "Miscellaneous", "Pasta", "Pork", "Seafood",
    "Side", "Starter", "Vegan", "Vegetarian"};

    private final FilterRecipeController controller;
    private final FilterRecipeViewModel viewModel;
    private final JComboBox<String> categoryBox = new JComboBox<>(CATEGORY_OPTIONS);
    private final JComboBox<DietaryPreference> preferenceBox = new JComboBox<>(DietaryPreference.values());
    private final JButton filterButton = new JButton("Apply Filters");
    private final JButton clearButton = new JButton("Clear");
    
}
