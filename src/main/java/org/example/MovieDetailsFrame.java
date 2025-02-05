package org.example;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.List;

public class MovieDetailsFrame extends JFrame {

    public MovieDetailsFrame(Movie movie) {
        setTitle("Movie Details");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with a vertical layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title Section
        JLabel titleLabel = new JLabel(movie.getTitle(), SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);

        if (movie.getTagline() != null && !movie.getTagline().isEmpty()) {
            JLabel taglineLabel = new JLabel("<html><i>" + movie.getTagline() + "</i></html>", SwingConstants.CENTER);
            taglineLabel.setFont(new Font("Arial", Font.ITALIC, 16));
            taglineLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            taglineLabel.setForeground(new Color(100, 100, 100));
            mainPanel.add(taglineLabel);
        }

        mainPanel.add(Box.createVerticalStrut(20)); // Add spacing

        // Poster Section
        try {
            if (movie.getPosterPath() != null && !movie.getPosterPath().isEmpty()) {
                String posterURL = "https://image.tmdb.org/t/p/w500" + movie.getPosterPath();
                ImageIcon originalImageIcon = new ImageIcon(new URL(posterURL));
                Image scaledImage = originalImageIcon.getImage().getScaledInstance(200, 300, Image.SCALE_SMOOTH);
                JLabel posterLabel = new JLabel(new ImageIcon(scaledImage));
                posterLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                mainPanel.add(posterLabel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        mainPanel.add(Box.createVerticalStrut(20)); // Add spacing

        // Details Section
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridLayout(0, 1, 10, 10));
        detailsPanel.setBackground(Color.WHITE);

        detailsPanel.add(createDetailLabel("Release Date:", movie.getReleaseDate()));
        detailsPanel.add(createDetailLabel("Runtime:", movie.getRunTime() + " minutes"));
        detailsPanel.add(createDetailLabel("Score:", String.format("%.2f", movie.getScore())));
        detailsPanel.add(createDetailLabel("Genres:", String.join(", ", movie.getGenres())));
        detailsPanel.add(createDetailLabel("Language:", movie.getLanguage()));
        detailsPanel.add(createDetailLabel("Adult Content:", movie.isAdult() ? "Yes" : "No"));
        detailsPanel.add(createDetailLabel("IMDB ID:", movie.getImdbId()));

        // Summary Section
        JLabel summaryLabel = new JLabel("<html><b>Summary:</b></html>");
        summaryLabel.setFont(new Font("Arial", Font.BOLD, 16));
        summaryLabel.setForeground(new Color(34, 45, 65));

        JTextArea summaryText = new JTextArea(movie.getSummary());
        summaryText.setFont(new Font("Arial", Font.PLAIN, 14));
        summaryText.setLineWrap(true);
        summaryText.setWrapStyleWord(true);
        summaryText.setEditable(false);
        summaryText.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        summaryText.setBackground(new Color(245, 245, 245));

        JScrollPane summaryScrollPane = new JScrollPane(summaryText);
        summaryScrollPane.setPreferredSize(new Dimension(200, 400));
        summaryScrollPane.setBorder(BorderFactory.createTitledBorder("Summary"));

        mainPanel.add(detailsPanel);
        mainPanel.add(Box.createVerticalStrut(20)); // Add spacing
        mainPanel.add(summaryLabel);
        mainPanel.add(summaryScrollPane);

        // Add the main panel to a scroll pane
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane);
    }

    private JLabel createDetailLabel(String field, String value) {
        JLabel label = new JLabel("<html><b>" + field + "</b> " + value + "</html>");
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(new Color(60, 60, 60));
        return label;
    }
}
