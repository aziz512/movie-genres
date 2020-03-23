# Movie Genres

The program analyzes `movies.csv` in the `/data` directory and parses movie titles, release years, and genres. Then it organizes all the genres into a hash table. It prints out genres in descending order by the total number of movies in the genre.  
A JSON file is then created with parsed genre data and a web page is launched that graphs the data using Google Charts.

The CSV file must be from https://grouplens.org/datasets/movielens/latest/ or have the same structure.
## Dependencies

* [Java 8](https://docs.oracle.com/javase/8/docs/api/index.html)
* GSON (jar in lib folder)
* Google Charts (using CDN)
* Javascript (ES6+ compatible browser)

## Setup
1) `git clone https://github.com/aziz512/movie-genres.git`
2) `cd movie-genres`
3) `chmod +x run.sh` (for *nix systems)
4) `./run.sh` (*nix) OR `./win_run.sh` (Windows)
4) Enjoy outputs. A webpage will open with a chart. In the console there will be text output.

## Folder Structure
* Code is saved into the `src` folder.
* Data is parsed from the csv file in the `data` folder.
* Output is printed in the console and displayed in a web page
