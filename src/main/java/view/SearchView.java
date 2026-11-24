package view;

import interface_adapter.search_recipe.SearchRecipeController;
import interface_adapter.search_recipe.SearchRecipeState;
import interface_adapter.search_recipe.SearchRecipeViewModel;
import use_case.search_recipe.SearchRecipeOutputData;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.net.URL;
import java.util.function.Consumer;

public class SearchView extends JPanel {

    private final SearchRecipeController controller;

    private final JTextField keywordField = new JTextField(24);
    private final JButton searchButton = new JButton("Search");
    private final JLabel messageLabel = new JLabel("Enter a keyword to search for recipes.");
    private final DefaultListModel<ResultRow> listModel = new DefaultListModel<>();
    private final JList<ResultRow> resultList = new JList<>(listModel);

    private final SearchRecipeViewModel viewModel;
    private Consumer<String> onRecipeSelected;
    private int searchGeneration;

    public SearchView(SearchRecipeController controller, SearchRecipeViewModel viewModel) {
        this.controller = controller;
        this.viewModel = viewModel;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JPanel searchPanel = new JPanel(new BorderLayout(8, 8));
        searchPanel.add(new JLabel("Keyword:"), BorderLayout.WEST);
        searchPanel.add(keywordField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        JPanel top = new JPanel(new BorderLayout(8, 8));
        top.add(searchPanel, BorderLayout.NORTH);
        top.add(messageLabel, BorderLayout.SOUTH);
        add(top, BorderLayout.NORTH);

        resultList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        resultList.setCellRenderer(new ResultRenderer());
        resultList.setFixedCellHeight(72);
        resultList.addListSelectionListener(event -> {
            if (event.getValueIsAdjusting()) {
                return;
            }
            ResultRow selected = resultList.getSelectedValue();
            if (selected != null && onRecipeSelected != null) {
                onRecipeSelected.accept(selected.recipeId);
            }
        });
        add(new JScrollPane(resultList), BorderLayout.CENTER);

        searchButton.addActionListener(event -> runSearch());
        keywordField.addActionListener(event -> runSearch());

        viewModel.addPropertyChangeListener(event -> {
            if ("state".equals(event.getPropertyName())) {
                SwingUtilities.invokeLater(this::updateView);
            }
        });
    }

    public void setOnRecipeSelected(Consumer<String> onRecipeSelected) {
        this.onRecipeSelected = onRecipeSelected;
    }

    private void runSearch() {
        String keyword = keywordField.getText();
        int generation = ++searchGeneration;
        searchButton.setEnabled(false);
        messageLabel.setText("Searching...");

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                controller.execute(keyword);
                return null;
            }

            @Override
            protected void done() {
                if (generation == searchGeneration) {
                    searchButton.setEnabled(true);
                }
            }
        };
        worker.execute();
    }

    private void updateView() {
        SearchRecipeState state = viewModel.getState();
        listModel.clear();
        resultList.clearSelection();

        if (state.getErrorMessage() != null) {
            messageLabel.setText(state.getErrorMessage());
            return;
        }

        SearchRecipeOutputData results = state.getResults();
        if (results == null || results.getMatches().isEmpty()) {
            messageLabel.setText("Enter a keyword to search for recipes.");
            return;
        }

        for (SearchRecipeOutputData.Match match : results.getMatches()) {
            ResultRow row = new ResultRow(
                    match.getRecipeId(), match.getName(), match.getImageUrl());
            listModel.addElement(row);
            loadImage(row);
        }
        messageLabel.setText(results.getMatches().size() + " recipes found.");
    }

    private void loadImage(ResultRow row) {
        if (row.imageUrl == null || row.imageUrl.isEmpty()) {
            return;
        }

        SwingWorker<ImageIcon, Void> worker = new SwingWorker<>() {
            @Override
            protected ImageIcon doInBackground() throws Exception {
                Image image = ImageIO.read(new URL(row.imageUrl));
                if (image == null) {
                    return null;
                }
                Image scaled = image.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
                return new ImageIcon(scaled);
            }

            @Override
            protected void done() {
                try {
                    ImageIcon icon = get();
                    int index = listModel.indexOf(row);
                    if (icon != null && index >= 0) {
                        row.icon = icon;
                        listModel.set(index, row);
                    }
                } catch (Exception ex) {
                    row.icon = null;
                }
            }
        };
        worker.execute();
    }

    private static final class ResultRow {
        private final String recipeId;
        private final String name;
        private final String imageUrl;
        private ImageIcon icon;

        private ResultRow(String recipeId, String name, String imageUrl) {
            this.recipeId = recipeId;
            this.name = name;
            this.imageUrl = imageUrl;
        }
    }

    private static final class ResultRenderer extends JPanel
            implements ListCellRenderer<ResultRow> {

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
                boolean cellHasFocus) {
            nameLabel.setText(value.name);
            if (value.icon == null) {
                imageLabel.setIcon(null);
                imageLabel.setText(" ");
            } else {
                imageLabel.setText("");
                imageLabel.setIcon(value.icon);
            }
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
}
