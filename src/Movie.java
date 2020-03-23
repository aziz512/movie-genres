import java.util.ArrayList;

// Aziz Yokubjonov - aziz.yokubjonov@gmail.com
// GitHub: @aziz512
// azizwrites.xyz

public class Movie {
    public String title;
    public Integer releaseYear;
    public String[] genres;

    public Movie(String title, Integer releaseYear, String[] genres) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.genres = genres;
    }

    public static Movie parseMovieLine(String currentLine) {
        // [movieId,title,genres]
        ArrayList<String> data = Utils.parseCSVLine(currentLine);
        // clean up quotation marks in some entries
        String title = data.get(1).replaceFirst("^(\")*([^\"]+)(\")*$", "$2").trim();
        String titleFormatRegex = "(.*)\\(([0-9]+)\\)$";
        Integer year;
        try {
            year = Integer.parseInt(title.replaceFirst(titleFormatRegex, "$2"));
        } catch (NumberFormatException e) {
            // some entries don't have release years
            year = null;
        }
        // remove release date from title
        title = data.get(1).replaceFirst(titleFormatRegex, "$1").trim();
        String[] genres = data.get(2).split("\\|");
        return new Movie(title, year, genres);
    }
}