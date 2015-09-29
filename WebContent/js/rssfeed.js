function load() {

	var feed ="https://sg.finance.yahoo.com/news/category-stocks/?format=rss";
	var options = {
			numResults : 10,
		    displayTime : 5000,
		    linkTarget : google.feeds.LINK_TARGET_BLANK,
		    pauseOnHover : false,
		    horizontal : false,
		    stacked : false,
		    title : 'Hollywood'
		  }
	new GFdynamicFeedControl(feed, "feedControl", options);

}
google.load("feeds", "1");
google.setOnLoadCallback(load);