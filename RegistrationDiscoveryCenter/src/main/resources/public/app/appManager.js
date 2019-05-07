var appName;
var instances;
var instanceStatusArray =[];
var zones = [];

$(document).ready(function(){
    appName = getUrlParam('appName');
    init();
    $("#navigation").html('<font id="'+appName+'">'+appName+'</font>');
    var collection = $('<div class="collection"><i class="icon-star-empty"></i></div>');
    $.ajax({
        type: "GET",
        url: "/rest/getApp",
        dataType: "json",
        data: {
            "appName": appName
        },
        success: function(result) {
            var app = result.result;
            if(app.collected == "1") {
                collection = $('<div class="collection collected" collected="1" id="'+app.collectionId+'"><i class="icon-star"></i></div>');
            }
            $("#navigation").append(collection);
        },
        error: function(e) {
            layer.msg("Get app error!");
        }
    });

    $("body").delegate(".collection", "click", function() {
        var collectionObj = $(this);
        collectOperation(collectionObj);
    })
});

function dropApp(){
    layer.confirm('Confirm to delete this App?', {
            title: 'Delete',
            btn: ['Delete','Cancel'], //按钮
            skin: 'danger'
        }, function(index){
        params ={};
        params.appName = appName;
        $.njx(
             basePath + "/rest/dropApp",
              params,
              true,
             "GET",
             "json",
             "application/json",
              function(data){
                   location.href="/rest/pages/apps";
              },
              function(){
                    layer.msg("Delete app error!");
              }
        );
        layer.close(index);
        layer.msg("Delete successfully!");
    }, function(){});
}

function init(){
    var params={};
    params.appName=appName;
    $.ajax({
        type: "GET",
        url:  basePath + "/rest/getAppsByName",
        dataType: "json",
        data: params,
        contentType: "application/json",
        success: function(data) {
            if(data.status == "1") {
              instances = data.result.instance;
              buildInstances(instances);
              $("#instancesTable").DataTable( {
                  "paging":   false,
                  "info":     false
              } );
          } else {
            //$("#instancesTable").empty();
          }
        },
        error: function(e) {
             layer.msg("Init instance list error!");
        }
    })
};

function buildInstances(instances){
    instanceStatusArray = [];
    $("#data-container").empty();
    for(var i=0;i<instances.length;i++){
       var instanceTmp = {};
       instanceTmp.instanceId = instances[i].instanceId;
       instanceTmp.statusUrl = instances[i].statusPageUrl;
       instanceTmp.command = instances[i].status;
       instanceStatusArray[i] = instanceTmp;

       var item = $('<tr></tr>');
       var checkBox;
       if(instances[i].status == "UP"){
           checkBox = $('<td><div id="checkBox_' + instances[i].instanceId + '" class="status-icon icon-green"><span class="icon-ok-circle"></span></div></td>');
       }else{
           checkBox = $('<td><div id="checkBox_"' + instances[i].instanceId + '" class="status-icon icon-red"></span></div></td>');
       }
       var app = $('<td><div>' + instances[i].app + '</div></td>');
       var host = $('<td><a target="_blank" href="'+ instances[i].homePageUrl + '">'+ instances[i].instanceId+ '</a></td>');
       var local = $('<td><div>' + instances[i].metadata.zone + '</div></td>');
       var down = $('<td><div id="down_' + instances[i].instanceId + '" target="_blank" class="icon-red cursor" title="switch off"><i class="icon-circle-arrow-down"></i></div></td>');
       var up = $('<td><div id="up_' + instances[i].instanceId + '" target="_blank" class="icon-green cursor" title="switch on"><i class="icon-circle-arrow-up"></i></div></td>')
       var drop = $('<td><div id="drop_' + instances[i].instanceId + '" class="instance-term icon-red cursor" title="drop instance"><i class="icon-remove"></i></div></td>');
       var updateTime = $('<td><div class="instance-term">' + formatDateTime(instances[i].lastDirtyTimestamp) + '</div></td');
       item.append(checkBox);
       item.append(app);
       item.append(host);
       item.append(local);
       if(instances[i].status =="UP"){
           item.append(down);
       }else{
           item.append(up);
       }
        item.append(drop);
        item.append(updateTime);
       up.click(function(){
           var thisId = $(this).find("div").attr("id");
           var parts =thisId.split("_");
           var requestUrl = "/eureka/apps/"+appName+"/"+parts[1]+"/status?value=UP";
           layer.confirm('Switch on line?', {
               title: 'Switch',
               btn: ['Confirm','Cancel'], //按钮
               skin: 'success'
           }, function(index){
              $.njx(
                   requestUrl,
                    null,
                    true,
                   "DELETE",
                   "JSON",
                   "application/json",
                    function(data){
                          init();
                    },
                    function(){
                          init();
                    }
              );
              layer.close(index);
           }, function(){});
       });
       down.click(function(){
            var thisId = $(this).find("div").attr("id");
            var parts =thisId.split("_");
            var requestUrl = "/eureka/apps/"+appName+"/"+parts[1]+"/status?value=OUT_OF_SERVICE";
           layer.confirm('Switch off line?', {
                     title: 'Switch',
                     btn: ['Confirm','Cancel'], //按钮
                     skin: 'danger'
           }, function(index){
                $.njx(
                     requestUrl,
                      null,
                      true,
                     "PUT",
                     "JSON",
                     "application/json",
                      function(data){
                            init();
                      },
                      function(){
                            init();
                      }
                );
               layer.close(index);
           }, function(){});
       });
       drop.click(function(){
           var thisId = $(this).find("div").attr("id");
           var parts =thisId.split("_");
           var theUrl = "http://"+parts[1]+"/shutdown"
           var params ={};
           params.name = appName+"("+parts[1]+")";
           params.theUrl =theUrl;
           var requestParams = JSON.stringify(params);
           layer.confirm('Drop instance?', {
               title: 'Drop',
               btn: ['Drop','Cancel'], //按钮
               skin: 'danger'
           }, function(index){
                  $.njx(
                       basePath + "/rest/appShutDown",
                       requestParams,
                        true,
                       "POST",
                       "JSON",
                       "application/json",
                        function(data){
                              init();
                        },
                        function(){
                              layer.msg("Delete instance error!");
                        }
                  );
                  layer.close(index);
        }, function(){});
       });

       $("#data-container").append(item);
    }
}

