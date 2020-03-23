// Aziz Yokubjonov - aziz.yokubjonov@gmail.com
// GitHub: @aziz512
// azizwrites.xyz

console.log('trying to load Google Charts...');
google.charts.load('current', { 'packages': ['corechart'] });
google.charts.setOnLoadCallback(init);

function init() {
    const genreData = JSON.parse(jsonData);
    const chartData = new google.visualization.DataTable();
    chartData.addColumn('string', 'Year');

    const genreNames = Object.keys(genreData).filter(genreName => !genreName.includes('no genre'));
    const [firstYearEver, lastYearEver] = genreNames.map(genre => {
        // create a column for every genre
        chartData.addColumn('number', genre);

        const genreDates = Object.keys(genreData[genre].yearMovieCount).sort();
        // [first year in genre, last year in genre]
        return [+genreDates[0], +genreDates[genreDates.length - 1]];
    }).reduce((accumulator, current) => { // figuring out the first and last year in the data
        let newSmallest = accumulator[0], newBiggest = accumulator[1];
        if (current[0] < newSmallest) newSmallest = current[0];
        if (current[1] > newBiggest) newBiggest = current[1];
        return [newSmallest, newBiggest]
    }, [Number.MAX_VALUE, Number.MIN_VALUE]);


    let currentYear = firstYearEver;
    while (currentYear <= lastYearEver) {
        const yearTotal = genreNames.map(genre => genreMoviesOnYear(currentYear, genre, genreData))
            .reduce((acc, current) => acc + current, 0);
        if (yearTotal === 0) { // no data for the year
            currentYear++; // but hopefully we have data for the next year
        } else {
            const row = [currentYear.toString(),
                ...genreNames.map(genre => genreMoviesOnYear(currentYear, genre, genreData))];
            chartData.addRow(row);
            currentYear += 5; // show data for every 5 years
        }
    }

    const chartOptions = {
        title: 'How did the proportion of genres change over time?',
        vAxis: { title: 'Proportion of movies on a given year' },
        isStacked: 'relative',
        chartArea: {
            top: 60,
            left: 100
        }
    };
    const chart = new google.visualization.SteppedAreaChart(document.getElementById('chart_div'));
    chart.draw(chartData, chartOptions);
}

function genreMoviesOnYear(year, genre, genreData) {
    return genreData[genre].yearMovieCount[year] || 0;
}
