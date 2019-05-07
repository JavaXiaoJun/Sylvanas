function collectOperation(collectionObj) {
    var collected = collectionObj.attr("collected");
    if(collected == "1") {
        var collectionId = collectionObj.attr("id");
        $.ajax({
            type: "POST",
            url: "/rest/deleteCollection",
            dataType: "json",
            data: {
                "collectionId": collectionId
            },
            success: function(result) {
                var status = result.status;
                if(status == "1") {
                    collectionObj.removeAttr("collected");
                    collectionObj.find("i").removeClass("icon-star").addClass("icon-star-empty");
                    collectionObj.removeClass("collected");
                    collectionObj.removeAttr("id");
                }
            },
            error: function(e) {
                layer.msg("Delete collection error!");
            }
        })
    } else {
        var appName = collectionObj.prev().attr("id");
        $.ajax({
            type: "POST",
            url: "/rest/addCollection",
            dataType: "json",
            data: {
                "appName": appName
            },
            success: function(result) {
                var status = result.status;
                var collectionId = result.info;
                if(status == "1") {
                    collectionObj.attr("collected", "1");
                    collectionObj.find("i").removeClass("icon-star-empty").addClass("icon-star");
                    collectionObj.addClass("collected");
                    collectionObj.attr("id", collectionId);
                }
            },
            error: function(e) {
                layer.msg("Add collection error!");
            }
        })
    }
}