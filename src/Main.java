// Aziz Yokubjonov - aziz.yokubjonov@gmail.com
// GitHub: @aziz512
// azizwrites.xyz

import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import com.google.gson.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner reader = new Scanner(new File("data/movies.csv"));
        reader.nextLine();

        int totalMovies = 0;
        HashMap<String, GenreDatum> genreData = new HashMap<>();
        while (reader.hasNextLine()) {
            String currentLine = reader.nextLine();
            Movie movie = Movie.parseMovieLine(currentLine);
            Integer releaseYear = movie.releaseYear;
            totalMovies++;

            for (String movieGenre : movie.genres) {
                genreData.putIfAbsent(movieGenre, new GenreDatum(movieGenre));

                GenreDatum genre = genreData.get(movieGenre);
                genre.totalMovieCount++;
                if (releaseYear != null) {
                    var yearCount = genre.yearMovieCount;
                    yearCount.putIfAbsent(releaseYear, 0);
                    yearCount.replace(releaseYear, yearCount.get(releaseYear) + 1);
                }
            }
        }

        // sort genres based on the number of movies each contains (desc. order)
        GenreDatum[] genresSortedDesc = (GenreDatum[]) genreData.values().toArray(new GenreDatum[0]);
        Arrays.sort(genresSortedDesc);

        double avgMoviesPerGenre = (double) totalMovies / genreData.keySet().size();
        System.out.println();
        System.out.printf("Average movies per genre: %s\n\n", avgMoviesPerGenre);
        System.out.println("ABOVE average # of movies:");
        boolean listingBelowAvg = false;
        for (GenreDatum genreDatum : genresSortedDesc) {
            if (!listingBelowAvg && genreDatum.totalMovieCount < avgMoviesPerGenre) {
                System.out.println("\nBELOW average # of movies:");
                listingBelowAvg = true;
            }
            System.out.printf("%s: %s\n", genreDatum.genreName, genreDatum.totalMovieCount);
        }

        // Saving data into a JSON file. The file is later used by JS for visualization
        Gson gson = new Gson();
        String jsonString = gson.toJson(genreData);
        String JS_DATA_PATH = "dist/genre_data.js";
        PrintWriter writer = new PrintWriter(new File(JS_DATA_PATH));
        writer.write(String.format("const jsonData = `%s`;", jsonString));
        writer.close();
        System.out.println();
        System.out.printf("Saved JSON output in '%s'\n", JS_DATA_PATH);

        reader.close();
    }
}

class GenreDatum implements Comparable<GenreDatum> {
    public final String genreName;
    public int totalMovieCount;
    // key: year, value: # of movies that year
    public HashMap<Integer, Integer> yearMovieCount = new HashMap<>();

    public GenreDatum(String name) {
        this.genreName = name;
    }

    public int compareTo(GenreDatum another) {
        return another.totalMovieCount - this.totalMovieCount;
    }
}
