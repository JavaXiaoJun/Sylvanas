$(document).ready(function(){
    $('#new_teams').multiselect({buttonWidth : '400px',maxHeight: 400});
    $('#new_type').multiselect({buttonWidth : '400px',maxHeight: 400});
    getTeams();
    init();
});

function getTeams(){
   $.njx(
         basePath + "/rest/getTeamList",
          null,
          true,
         "GET",
         "json",
         "application/json",
          function(data){
               if(data.result!=null){
                   initTeams(data.result);
               }
          },
          function(){
                layer.msg("Get team list error!");
          }
    );
}

function initTeams(teams){
   $('#new_teams').empty();
      for(var i=0;i<teams.length;i++){
          $('#new_teams').append(
               $('<option value='+teams[i].id+'>'+teams[i].name+'</option>')
           );
    }
    $('#new_teams').multiselect('rebuild');
}

function addApp(){
     $("#route_name").val("");
     $('#new_type').multiselect('deselectAll', false);
     $('#new_type').multiselect('updateButtonText');
     $('#new_teams').multiselect('deselectAll', false);
     $('#new_teams').multiselect('updateButtonText');
     $(function () { $('#myModal').modal({
         keyboard: true
     })});
};

function saveApp(){
    requestParams ={};
    if($("#new_name").val()==null || $("#new_name").val()==""){
        layer.msg("Name is null");
        return ;
    }
    requestParams.name =  $("#new_name").val();
    if($("#new_type").val()==null || $("#new_type").val()==""){
        layer.msg("Type is null");
        return ;
    }
    requestParams.type =  $("#new_type").val();
    if($("#new_teams").val()==null || $("#new_teams").val()==""){
        layer.msg("Team is null");
        return ;
    }
    requestParams.teamId =  $("#new_teams").val();
    var params = JSON.stringify(requestParams);
    $.njx(
         basePath + "/rest/addApp",
          params,
          true,
         "POST",
         "json",
         "application/json",
          function(data){
               if(data.result!=null){
                   init();
                   $("#closeEdit").click();
               }
          },
          function(){
                layer.msg("Save app error!");
          }
    );
}

function init(){
    $.njx(
         basePath + "/rest/getApps",
          null,
          true,
         "GET",
         "JSON",
         "application/json",
          function(data){
               /*console.log(JSON.stringify(data, null, 4));*/
               buildApps(data.result);
          },
          function(){
                layer.msg("Init app list error!");
          }
    );
};

function buildApps(appsData){
    $("#serviceCnt").empty();
    $("#baseCnt").empty();
    $("#appCnt").empty();
    if(appsData != null && appsData.length > 0) {
        for(var i=0;i<appsData.length;i++){
            var block = $('<div class="col-lg-2"></div>');
            var section = $('<section class="panel"></section>');
            var body = $('<div class="panel-body"></div>');
            var appItemHead = $('<div class="app-item-head"></div>');
            var appName = $('<div id="'+appsData[i].name + '" class="app-name">'+appsData[i].name+'</div>');
            var collection = $('<div class="collection"><i class="icon-star-empty"></i></div>');
            var node = $( '<div class="app-item-term"><i class="icon-sun"></i>Nodes : '+appsData[i].availableNodes+' </div>');
            var team = $('<div><i class="icon-group"></i>Team : '+appsData[i].teamName+'</div>');
            appItemHead.append(appName);
            if(appsData[i].collected == "1") {
                collection = $('<div class="collection collected" collected="1" id="'+appsData[i].collectionId+'"><i class="icon-star"></i></div>');
            }
            appItemHead.append(collection);
            body.append(appItemHead);
            body.append(node);
            body.append(team);
            section.append(body);
            block.append(section);

            appName.click(function(){
                var appName = $(this).attr("id");
                window.location.href = basePath +"/rest/pages/appManager?appName="+appName;
            });

            collection.click(function() {
                var collectionObj = $(this);
                collectOperation(collectionObj);
            });
            var type = appsData[i].type;
            if(type.toLowerCase()=="service"){
                $("#serviceCnt").append(block);
                $("#serviceTitle").show();
            }else if(type.toLowerCase()=="base"){
                $("#baseCnt").append(block);
                $("#baseTitle").show();
            }else{
                $("#appCnt").append(block);
                $("#appTitle").show();
            }
        }
    }

}
