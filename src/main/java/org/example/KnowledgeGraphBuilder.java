package org.example;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class KnowledgeGraphBuilder {

    private final Driver driver;

    public KnowledgeGraphBuilder(String uri, String user, String password) {
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
    }

    public void close() {
        driver.close();
    }

    // Adds a Movie node and its relationships.
    public void addMovie(Movie movie) {
        try (Session session = driver.session()) {
            session.writeTransaction(tx -> {
                // Create a map of movie properties using Map.ofEntries.
                Map<String, Object> movieProperties = Map.ofEntries(
                        Map.entry("id", movie.getId()),
                        Map.entry("title", movie.getTitle()),
                        Map.entry("score", movie.getScore()),
                        Map.entry("releaseDate", movie.getReleaseDate()),
                        Map.entry("runTime", movie.getRunTime()),
                        Map.entry("isAdult", movie.isAdult()),
                        Map.entry("imdbId", movie.getImdbId()),
                        Map.entry("language", movie.getLanguage()),
                        Map.entry("summary", movie.getSummary()),
                        Map.entry("posterPath", movie.getPosterPath()),
                        Map.entry("tagLine", movie.getTagline())
                );

                // Merge the Movie node.
                tx.run("MERGE (m:Movie {id: $id}) " +
                                "SET m.title = $title, m.score = $score, m.releaseDate = $releaseDate, " +
                                "m.runTime = $runTime, m.isAdult = $isAdult, m.imdbId = $imdbId, " +
                                "m.language = $language, m.summary = $summary, m.posterPath = $posterPath, " +
                                "m.tagLine = $tagLine",
                        movieProperties);

                // Merge genres and relationships.
                for (String genre : movie.getGenres()) {
                    tx.run("MERGE (g:Genre {name: $genre}) " +
                                    "MERGE (m:Movie {id: $id}) " +
                                    "MERGE (m)-[:BELONGS_TO]->(g)",
                            Map.ofEntries(
                                    Map.entry("id", movie.getId()),
                                    Map.entry("genre", genre.trim())
                            ));
                }

                return null;
            });
        }
    }



    // Parses the movies.csv file and adds movies to the Knowledge Graph.
    public void parseMovies(String filePath) throws IOException, CsvValidationException {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] tokens;

            int lineNumber = 0;
            // Read the header.
            if ((tokens = reader.readNext()) != null) {
                lineNumber++;
                // (Optional) Validate header names here.
            }
            while ((tokens = reader.readNext()) != null) {
                lineNumber++;
                // Expecting appropriate columns for parsing.
                if (tokens.length < 20) { // Adjust the column count as per the dataset structure.
                    System.err.println("Skipping malformed line " + lineNumber + ": not enough columns");
                    continue;
                }
                try {
                    String id = tokens[0].trim().replaceAll("^\"|\"$", "");
                    String title = tokens[1].trim().replaceAll("^\"|\"$", "");
                    float score = tokens[2].isEmpty() ? 0 : Float.parseFloat(tokens[2].trim().replaceAll("^\"|\"$", ""));
                    String releaseDate = tokens[5].trim().replaceAll("^\"|\"$", "");
                    int runTime = tokens[7].isEmpty() ? 0 : Integer.parseInt(tokens[7].trim().replaceAll("^\"|\"$", ""));
                    boolean isAdult = tokens[8].trim().replaceAll("^\"|\"$", "").equalsIgnoreCase("true");
                    String imdbId = tokens[12].trim().replaceAll("^\"|\"$", "");
                    String language = tokens[13].trim().replaceAll("^\"|\"$", "");
                    String summary = tokens[15].trim().replaceAll("^\"|\"$", "");
                    String posterPath = tokens[17].trim().replaceAll("^\"|\"$", "");
                    String tagLine = tokens[18].trim().replaceAll("^\"|\"$", "");
                    String genresField = tokens[19].trim().replaceAll("^\"|\"$", "");
                    String[] genres = genresField.split(",\\s*");

                    // Add the movie to the Knowledge Graph.
                    Movie movie = new Movie(id, title, score, releaseDate, runTime, isAdult,
                            imdbId, language, summary, posterPath, tagLine, List.of(genres));
                    addMovie(movie);
                } catch (NumberFormatException nfe) {
                    System.err.println("Skipping line " + lineNumber + " due to number format error: " + nfe.getMessage());
                } catch (Exception e) {
                    System.err.println("Error processing line " + lineNumber + ": " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        // Update connection parameters as needed.
        KnowledgeGraphBuilder graphBuilder = new KnowledgeGraphBuilder("bolt://localhost:7687", "neo4j", "password");
        try {
            graphBuilder.parseMovies("src/main/resources/TMDB_movie_dataset_v11.csv.crdownload");
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        } finally {
            graphBuilder.close();
        }
    }
}
