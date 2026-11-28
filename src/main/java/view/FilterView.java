package view;
import interface_adapter.filter_recipe.*;
import use_case.filter_recipe.DietaryPreference;
import use_case.filter_recipe.FilterRecipeOutputData;

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
    private final JLabel messageLabel = new JLabel("Choose a category or dietary preference");
    private final DefaultListModel<ResultRow> listModel = new DefaultListModel<>();
    private final JList<ResultRow> resultList = new JList<>(listModel);
    private Consumer<String> onRecipeSelected;
    private boolean isBusy;

    public FilterView(FilterRecipeController controller, FilterRecipeViewModel viewModel){
        this.controller = controller;
        this.viewModel = viewModel;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        categoryBox.setEditable(true);
        preferenceBox.setSelectedItem(DietaryPreference.ANY);
        JPanel controls = new JPanel(new GridLayout(2, 3, 8, 8));
        controls.add(new JLabel("Category"));
        controls.add(new JLabel("Dietary Preference"));
        controls.add(new JLabel(""));
        controls.add(categoryBox);
        controls.add(preferenceBox);

        JPanel buttons = new JPanel(new GridLayout(1, 2, 4, 0));
        buttons.add(filterButton);
        buttons.add(clearButton);
        controls.add(buttons);

        JPanel top = new JPanel(new BorderLayout(8, 8));
        top.add(controls, BorderLayout.NORTH);
        top.add(messageLabel, BorderLayout.SOUTH);
        add(top, BorderLayout.NORTH);

        resultList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        resultList.setCellRenderer(new ResultRenderer());
        resultList.setFixedCellHeight(76);
        resultList.addListSelectionListener(event -> {
            if (event.getValueIsAdjusting()){
                return;
            }
            ResultRow selected = resultList.getSelectedValue();
            if (selected != null && onRecipeSelected != null){
                onRecipeSelected.accept(selected.recipeID);
            }
        });
        add (new JScrollPane(resultList), BorderLayout.CENTER);

        filterButton.addActionListener(event -> applyFilters());
        clearButton.addActionListener(event -> cleraFilters());
        viewModel.addPropertyChangeListener(event -> {
            if ("state".equals(event.getPropertyName())){
                SwingUtilities.invokeLater(this::updateView);
            }
        });
    }

    private static final class ResultRow{
        private final String recipeID;
        private final String name;
        private final String imageUrl;
        private final String category;
        private ImageIcon icon;

        private ResultRow(String recipeID, String name, String imageUrl, String category){
            this.recipeID = recipeID;
            this.name = name;
            this.imageUrl = imageUrl;
            this.category = category;
        }
    }
    private static final class ResultRenderer extends  JPanel implements ListCellRenderer<ResultRow> {
        private final JLabel imageLabel = new JLabel();
        private final JLabel nameLabel = new JLabel();

        private ResultRenderer() {
            setLayout(new BorderLayout(8, 0));
            setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
            imageLabel.setPreferredSize(new Dimension(64, 64));
            add(imageLabel, BorderLayout.WEST);
            add(nameLabel, BorderLayout.CENTER);
        }

        @Override
        public Component getListCellRendererComponent(
            JList<? extends ResultRow> list,
            ResultRow value,
            int index,
            boolean isSelected,
            boolean cellHasFocus){
            nameLabel.setText(value.name + " [" + value.category + "]");
            imageLabel.setText("");
            imageLabel.setIcon(value.icon);
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                nameLabel.setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                nameLabel.setForeground(list.getForeground());
            }
            setOpaque(true);
            return this;
        }

    }

    private void applyFilters(){
        if (isBusy){
            return;
        }
        Object selection = categoryBox.getSelectedItem();
        String category;
        if (selection == null){
            category = "";
        }
        else{
            category = selection.toString().trim();
        }
        if ("Any Category".equalsIgnoreCase(category)){
            category = "";
        }
        DietaryPreference preference = (DietaryPreference) preferenceBox.getSelectedItem();
        final String chosenCategory = category;
        final DietaryPreference chosenPreference = preference;
        isBusy = true;
        filterButton.setEnabled(false);
        clearButton.setEnabled(false);
        messageLabel.setText("Filtering recipes, this could take some time:)");

        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground(){
                controller.execute(chosenCategory, chosenPreference);
                return null;
            }
            @Override
            protected void done(){
                isBusy = false;
                filterButton.setEnabled(true);
                clearButton.setEnabled(true);
                try{
                    get();
                }
                catch(InterruptedException ex){
                    Thread.currentThread().interrupt();
                    messageLabel.setText("Filtering was interrupted.");
                }
                catch(ExecutionException ex){
                    messageLabel.setText("Filtering failed");
                }
            }
        };
        worker.execute();
    }

    private void cleraFilters(){
        if (isBusy){
            return;
        }
        categoryBox.setSelectedItem("Any Category");
        preferenceBox.setSelectedItem(DietaryPreference.ANY);
        viewModel.setState(new FilterRecipeState());
        viewModel.firePropertyChanged();
    }

    private void updateView(){
        FilterRecipeState state = viewModel.getState();
        listModel.clear();
        resultList.clearSelection();
        if (state.getErrorMessage() != null){
            messageLabel.setText(state.getErrorMessage());
            return;
        }
        FilterRecipeOutputData results = state.getResults();
        if (results == null || results.getMatches().isEmpty()){
            messageLabel.setText("Choose a category or dietary preference.");
            return;
        }
        for (FilterRecipeOutputData.Match match: results.getMatches()){
            ResultRow row = new ResultRow(match.getRecipeID(), match.getName(),
                    match.getImageUrl(), match.getCategory());
            listModel.addElement(row);
            loadImage(row);
        }
        messageLabel.setText(results.getMatches().size() + "recipes found");
    }
    private void loadImage(ResultRow row){
        if (row.imageUrl == null || row.imageUrl.isEmpty()){
            return;
        }
        SwingWorker<ImageIcon, Void> worker = new SwingWorker<ImageIcon, Void>() {
            @Override
            protected ImageIcon doInBackground() throws Exception{
                Image image = ImageIO.read(new URL(row.imageUrl));
                if (image == null){
                    return null;
                }
                Image scaled = image.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
                return new ImageIcon(scaled);
            }
            @Override
            protected void done(){
                try{
                    ImageIcon icon = get();
                    int index = listModel.indexOf(row);
                    if (icon != null && index >= 0){
                        row.icon = icon;
                        listModel.set(index, row);
                    }
                }
                catch(Exception ex){
                    row.icon = null;
                }
            }
        };
        worker.execute();
    }

}
