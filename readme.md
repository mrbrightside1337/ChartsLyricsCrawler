# ChartsLyricsCrawler

University project to crawl the year-end charts of the billboard website, request the corresponding lyrics from the apiseeds lyrics api and export all the information as a json file.

The project has two components: ChartCrawler and LyricsCrawler.

## ChartCrawler

The ChartCrawler crawls all the billboard top 100 end-year charts of the years within the specified time frame. Therefore the needed information (artist and song) are extracted of the DOM of the HTML. The ChartCrawler constructs song objects with the crawled data.

## LyricsCrawler

The LyricsCrawler is not really a crawler. The LyricsCrawler populates the existing song objects with the lyrics data that are requested from the apiseeds lyrics api.

# TODO
* Improve crawling performance by
	* use multiple threads for the requests
	* use another api