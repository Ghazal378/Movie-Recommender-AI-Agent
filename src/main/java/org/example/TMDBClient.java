package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public class TMDBClient {

    private static final String API_KEY = "c3995fc8a771f420773bb6d5ebe9ba44";
    private static Map<String, String> genreMap = null;

    public static List<String> getRecommendedMovieTitles(String genre, String actor, String director) throws Exception {
        String baseUrl = "https://api.themoviedb.org/3/discover/movie";
        String query = baseUrl + "?api_key=" + API_KEY + "&language=en-US";

        // Dynamically fetch genre ID
        if (genre != null && !genre.isEmpty()) {
            String genreId = getGenreId(genre);
            if (genreId != null) {
                query += "&with_genres=" + genreId;
            } else {
                throw new Exception("Invalid genre: " + genre);
            }
        }

        // Dynamically fetch actor ID
        if (actor != null && !actor.isEmpty()) {
            query += "&with_cast=" + getActorId(actor);
        }

        // Dynamically fetch director ID
        if (director != null && !director.isEmpty()) {
            query += "&with_crew=" + getDirectorId(director);
        }

        // Call the TMDB API
        URL url = new URL(query);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // Parse the API response to extract movie titles
        JSONObject jsonResponse = new JSONObject(response.toString());
        JSONArray results = jsonResponse.getJSONArray("results");

        List<String> movieTitles = new ArrayList<>();
        for (int i = 0; i < results.length(); i++) {
            JSONObject movie = results.getJSONObject(i);
            String title = movie.getString("title");
            movieTitles.add(title);
        }

        return movieTitles;
    }

    // Fetch and cache genre list from TMDB
    private static void fetchGenres() throws Exception {
        if (genreMap == null) {
            genreMap = new HashMap<>();
            String baseUrl = "https://api.themoviedb.org/3/genre/movie/list";
            String query = baseUrl + "?api_key=" + API_KEY + "&language=en-US";

            URL url = new URL(query);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Parse the API response to populate the genre map
            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONArray genres = jsonResponse.getJSONArray("genres");

            for (int i = 0; i < genres.length(); i++) {
                JSONObject genre = genres.getJSONObject(i);
                String name = genre.getString("name").toLowerCase();
                String id = String.valueOf(genre.getInt("id")); // Convert ID to String
                genreMap.put(name, id);
            }
        }
    }

    // Convert genre name to TMDB genre ID
    private static String getGenreId(String genreName) throws Exception {
        fetchGenres(); // Ensure genres are loaded
        return genreMap.get(genreName.toLowerCase());
    }

    // Convert actor name to TMDB actor ID
    public static String getActorId(String actorName) throws Exception {
        String encodedActorName = java.net.URLEncoder.encode(actorName, "UTF-8");
        String baseUrl = "https://api.themoviedb.org/3/search/person";
        String query = baseUrl + "?api_key=" + API_KEY + "&language=en-US&query=" + encodedActorName;

        URL url = new URL(query);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // Parse the API response to extract the actor's ID
        JSONObject jsonResponse = new JSONObject(response.toString());
        JSONArray results = jsonResponse.getJSONArray("results");

        if (results.length() > 0) {
            JSONObject actor = results.getJSONObject(0); // Get the first result
            return String.valueOf(actor.getInt("id")); // Return the actor's ID as a string
        } else {
            throw new Exception("Actor not found: " + actorName);
        }
    }

    // Convert director name to TMDB director ID
    public static String getDirectorId(String directorName) throws Exception {
        String encodedDirectorName = java.net.URLEncoder.encode(directorName, "UTF-8");
        String baseUrl = "https://api.themoviedb.org/3/search/person";
        String query = baseUrl + "?api_key=" + API_KEY + "&language=en-US&query=" + encodedDirectorName;

        URL url = new URL(query);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // Parse the API response to extract the director's ID
        JSONObject jsonResponse = new JSONObject(response.toString());
        JSONArray results = jsonResponse.getJSONArray("results");

        if (results.length() > 0) {
            JSONObject director = results.getJSONObject(0); // Get the first result
            return String.valueOf(director.getInt("id")); // Return the director's ID as a string
        } else {
            throw new Exception("Director not found: " + directorName);
        }
    }
}
