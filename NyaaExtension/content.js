// IF the user clicks on the extension icon execute CreateMagnetLink
chrome.runtime.onMessage.addListener(
    function(request, sender, sendResponse) {
        if( request.message === "clicked_browser_action" ) {
            CreateMagnetLink();
        }
    }
);

/**
 * Creates magnet url to add to clipboard
 */
function CreateMagnetLink(){
    let children = $("body > div > div.table-responsive > table > tbody > tr > td > a:nth-child(2)");
    let magnetUrl = "";
    children.each(function(key, value ) {
        // Check if value is a magnet url
        if(value.href.includes("magnet")){
            let href = value.href;
            // combine magnet url's
            magnetUrl += href + "\n";

            // Check if this is the last element in the each loop
            if(key+1 === children.length){
                addToClipboard(magnetUrl);
                console.log("\nOutput: \n" + magnetUrl);
            }
        }
    });
}

/**
 * Add's date to clipboard
 * @param {String} clipboardData data that gets inserten into clipboard 
 */
function addToClipboard(clipboardData) {
    // Check if the plugin has permission to edit the clipboard
    navigator.permissions.query({name: "clipboard-write"}).then(result => {
        if (result.state == "granted" || result.state == "prompt") {
            // Try to write the clipboardDate to users clipboard
            navigator.clipboard.writeText(clipboardData).then(function(a, b, c) {
                console.log(a, b, c)
                alert("URL's succesfully added to clipboard");
            }, function(y,u,i) {
                console.log(y,u,i)

                alert("can't write to clipboard, try again or see dev console");
            });
        } else {
            alert("Error writing to clipboard, try again or see dev console");
        }
    });
}