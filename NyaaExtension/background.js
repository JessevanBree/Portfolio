// Add a onclick listener to the chrome extension
chrome.browserAction.onClicked.addListener(function(tab) {
  // Get the current tabs that are open
  chrome.tabs.query({active: true, currentWindow: true}, function(tabs) {
      var activeTab = tabs[0];
      // Sent a message to the active tab that the user clicked on the extension icon
      chrome.tabs.sendMessage(activeTab.id, {"message": "clicked_browser_action"});
    });
});