function load() {
var feed ="http://www.sgx.com/wps/wcm/connect/design_new/site/sa/rss/news_release_rss.xml";
new GFdynamicFeedControl(feed, "feedControl");

}
google.load("feeds", "1");
google.setOnLoadCallback(load);