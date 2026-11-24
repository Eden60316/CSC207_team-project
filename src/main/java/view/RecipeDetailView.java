
package view;

import interface_adapter.view_recipe.ViewRecipeController;
import interface_adapter.view_recipe.ViewRecipeState;
import interface_adapter.view_recipe.ViewRecipeViewModel;
import use_case.view_recipe.ViewRecipeOutputData;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import javax.imageio.ImageIO;
import java.net.URL;

public class RecipeDetailView extends JPanel {

    private final ViewRecipeController controller;
    private final ViewRecipeViewModel viewModel;

    private final JLabel nameLabel = new JLabel("Recipe Details");
    private final JLabel categoryLabel = new JLabel("Category: ");
    private final JLabel imageLabel = new JLabel("Image: ");
    private String currentImageUrl;
    private final JButton backButton = new JButton("Back");
    private Runnable onBackRequested;

    private final JTextArea ingredientsArea = new JTextArea();
    private final JTextArea instructionsArea = new JTextArea();

    public RecipeDetailView(ViewRecipeController controller,
                            ViewRecipeViewModel viewModel) {

        this.controller = controller;
        this.viewModel = viewModel;

        setLayout(new BorderLayout(10, 10));

        JPanel headerPanel = new JPanel(new GridLayout(4, 1));

        backButton.addActionListener(e -> {
            if (onBackRequested != null) {
                onBackRequested.run();
            }
        });

        headerPanel.add(backButton);
        headerPanel.add(nameLabel);
        headerPanel.add(categoryLabel);
        headerPanel.add(imageLabel);

        add(headerPanel, BorderLayout.NORTH);

        ingredientsArea.setEditable(false);
        ingredientsArea.setLineWrap(true);
        ingredientsArea.setWrapStyleWord(true);

        instructionsArea.setEditable(false);
        instructionsArea.setLineWrap(true);
        instructionsArea.setWrapStyleWord(true);

        JPanel contentPanel = new JPanel(new GridLayout(2, 1, 10, 10));

        JPanel ingredientsPanel = new JPanel(new BorderLayout());
        ingredientsPanel.add(new JLabel("Ingredients:"), BorderLayout.NORTH);
        ingredientsPanel.add(new JScrollPane(ingredientsArea), BorderLayout.CENTER);

        JPanel instructionsPanel = new JPanel(new BorderLayout());
        instructionsPanel.add(new JLabel("Instructions:"), BorderLayout.NORTH);
        instructionsPanel.add(new JScrollPane(instructionsArea), BorderLayout.CENTER);

        contentPanel.add(ingredientsPanel);
        contentPanel.add(instructionsPanel);

        add(contentPanel, BorderLayout.CENTER);

        viewModel.addPropertyChangeListener(evt -> {
            if ("state".equals(evt.getPropertyName())) {
                updateView();
            }
        });

        updateView();
    }

    public void showRecipe(String recipeId) {
        controller.execute(recipeId);
    }

    private void updateView() {
        currentImageUrl = null;
        imageLabel.setIcon(null);

        ViewRecipeState state = viewModel.getState();

        if (state.getErrorMessage() != null) {
            nameLabel.setText("Error: " + state.getErrorMessage());
            categoryLabel.setText("");
            imageLabel.setText("");
            ingredientsArea.setText("");
            instructionsArea.setText("");
            return;
        }

        ViewRecipeOutputData recipe = state.getRecipe();

        if (recipe == null) {
            nameLabel.setText("Select a recipe to view details");
            categoryLabel.setText("");
            imageLabel.setText("");
            ingredientsArea.setText("");
            instructionsArea.setText("");
            return;
        }

        nameLabel.setText(recipe.getName());
        categoryLabel.setText("Category: " + recipe.getCategory());
        displayImage(recipe.getImageUrl());

        List<String> ingredients = recipe.getIngredients();
        List<String> measurements = recipe.getMeasurements();

        StringBuilder ingredientText = new StringBuilder();

        for (int i = 0; i < ingredients.size(); i++) {
            String measurement = i < measurements.size()
                    ? measurements.get(i) : "";

            ingredientText.append(ingredients.get(i))
                    .append(" - ")
                    .append(measurement)
                    .append("\n");
        }

        ingredientsArea.setText(ingredientText.toString());
        instructionsArea.setText(recipe.getInstructions());
    }

    private void displayImage(String imageUrl) {
        currentImageUrl = imageUrl;

        if (imageUrl == null || imageUrl.isEmpty()) {
            imageLabel.setText("No image available");
            return;
        }

        imageLabel.setText("Loading image...");

        SwingWorker<ImageIcon, Void> worker =
                new SwingWorker<ImageIcon, Void>() {

                    @Override
                    protected ImageIcon doInBackground() throws Exception {
                        Image image = ImageIO.read(new URL(imageUrl));

                        if (image == null) {
                            return null;
                        }

                        Image scaledImage = image.getScaledInstance(
                                180, 120, Image.SCALE_SMOOTH);

                        return new ImageIcon(scaledImage);
                    }

                    @Override
                    protected void done() {
                        if (!imageUrl.equals(currentImageUrl)) {
                            return;
                        }

                        try {
                            ImageIcon icon = get();

                            imageLabel.setIcon(icon);
                            imageLabel.setText(
                                    icon == null ? "Image unavailable" : "");

                        } catch (Exception e) {
                            imageLabel.setText("Image unavailable");
                        }
                    }
                };

        worker.execute();
    }
    public void setOnBackRequested(Runnable onBackRequested) {
        this.onBackRequested = onBackRequested;
    }
}
