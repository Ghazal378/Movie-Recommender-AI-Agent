package org.example;

import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MovieRecommendationApp {
    public static KnowledgeGraphQuerier querier;
    public static void main(String[] args) {
        // Update Neo4j connection parameters.
        String uri = "bolt://localhost:7687";
        String user = "neo4j";
        String password = "password";

        // Initialize Neo4j driver and clients.
        Driver driver = GraphDatabase.driver(uri, org.neo4j.driver.AuthTokens.basic(user, password));
        querier = new KnowledgeGraphQuerier(driver);


        new StartFrame(querier);


    }
    public static void callAPI(String genre, String actor,String director, KnowledgeGraphQuerier querier) {
        try {
            // Step 1: Fetch recommended movie titles from TMDB API.
            List<String> movieTitles = TMDBClient.getRecommendedMovieTitles(genre, actor,director);

            // Step 2: Query the knowledge graph for detailed movie information.
            List<Movie> movieList = new ArrayList<>();
            for (String title : movieTitles) {
                Movie movie = querier.getMovieByTitle(title);
                if (movie != null) {
                    movieList.add(movie);
                }
            }

            // Sort movies by score in descending order
            movieList.sort((m1, m2) -> Double.compare(m2.getScore(), m1.getScore()));

            // Step 3: Create a scrollable pane to display movie details
            MovieMenuFrame movieMenuFrame = new MovieMenuFrame(movieList);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            querier.close();
        }
    }

    public static void getQuerier(KnowledgeGraphQuerier querier) {
        MovieRecommendationApp.querier = querier;
    }
}
