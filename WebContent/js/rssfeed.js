function load() {

	var feed ="http://www.investopedia.com/feedbuilder/feed/getFeed?feedName=rss_stock_analysis";
	var options = {
			numResults : 20,
		    displayTime : 5000,
		    linkTarget : google.feeds.LINK_TARGET_BLANK,
		    pauseOnHover : false,
		    horizontal : false,
		    stacked : false,
		    title : 'Stock News'
		  }
	new GFdynamicFeedControl(feed, "feedControl", options);

}
google.load("feeds", "1");
google.setOnLoadCallback(load);