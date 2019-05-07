$(document).ready(function(){
    init();

    $("body").delegate(".app-name", "click", function() {
         var appName = $(this).attr("id");
         window.location.href = basePath +"/rest/pages/appManager?appName="+appName;
    })

    $("body").delegate(".news-title", "click", function(){
        var newsId = $(this).attr("id");
        $.ajax({
            type: "GET",
            url: "/rest/getNewsById",
            dataType: "json",
            data: {
                "newsId": newsId
            },
            success: function(data) {
                var news = data.result;
                if(news != null) {
                    layer.open({
                        type: 1,
                        title: news.title,
                        closeBtn: 1,
                        shadeClose: true,
                        skin: 'yourclass',
                        content: '<div class="news-container">' +
                                    '<div class="update-time">'+ news.updateTime + '</div>' +
                                    '<div class="news-content">' + news.content + '</div>' +
                                 '</div>'
                    });
                } else {
                    layer.msg("News not exists");
                }
                console.log(news);
            },
            error: function(e) {
                layer.msg("Get one news error!")
            }
        })
    });
});

function init(){

    $.njx(
         basePath + "/rest/homePage",
          null,
          true,
         "GET",
         "JSON",
         "application/json",
          function(data){
               JSON.stringify(data, null, 4);
               if(data.result != null) {
                    $("#apps").html(data.result.apps);
                    $("#teams").html(data.result.teams);
                    $("#instances").html(data.result.instances);
                    $("#news").html(data.result.news);
               }
          },
          function(){
                layer.msg("error!");
          }
    );

     $.njx(
             basePath + "/rest/getActivity",
              null,
              true,
             "GET",
             "JSON",
             "application/json",
              function(data){
               JSON.stringify(data, null, 4);
               buildActivity(data.result);
              },
              function(){
                    layer.msg("error!");
              }
        );

     $.ajax({
        type: "POST",
        url: "/rest/getAppCollectedList",
        dataType: "json",
        success: function(result) {
            JSON.stringify(result, null, 4)
            var appList = result.result;
            buildCollectedAppList(appList);

        },
        error: function(e) {
            layer.msg("collected app list error!");
        }
     });

    $.ajax({
        type: "POST",
        url: "/rest/getRecentNewsList",
        dataType: "json",
        success: function(result) {
            JSON.stringify(result, null, 4)
            var newsList = result.result;
            buildRecentNewsList(newsList);
        },
        error: function(e) {
            layer.msg("collected app list error!");
        }
    })
};

function buildCollectedAppList(collectedAppList) {
    if(collectedAppList != null && collectedAppList.length > 0) {
        var html = "";
        for(var i = 0, len = collectedAppList.length; i < len; i++) {
            var app = collectedAppList[i];
            html += '<div class="space-between collection-item">' +
                        '<div class="app-name icon-green" id="'+app.name+'">'+ app.name +'</div>' +
                        '<div class="space-between">' +
                            '<div class="node">Nodes : ' + app.availableNodes + '</div>' +
                            '<div class="team-name">'+ app.teamName +'</div>' +
                        '</div>' +
                    '</div>';
        }
        $("#collections").html(html);
    }
}

function buildActivity(activityData){
    if(activityData != null && activityData.length > 0) {
        for(var i=0;i<activityData.length;i++){
              var html = ' <div class="activity-item"> ' +
                         ' <div class="font-bold">'+activityData[i].username+'</div> ' +
                         ' <div class="space-between"> ' +
                         '      <div>'+activityData[i].description+'</div> ' +
                         '      <div>'+activityData[i].time+'</div> ' +
                         ' </div> ' +
                         ' </div> ';

             $("#activity").append(html);
         }
    }
}

function buildRecentNewsList(newsList){
    if(newsList != null && newsList.length > 0) {
        var html = "";
        for(var i=0;i<newsList.length;i++){
              html += ' <div class="news-item space-between"> ' +
                         '      <div class="news-title" id="'+newsList[i].newsId+'">'+newsList[i].title+'</div> ' +
                         '      <div>'+newsList[i].updateTime+'</div> ' +
                         ' </div> ';
         }
         $("#newsList").html(html);
    }
}