
var teams;

$(document).ready(function(){
   getTeams();

    $("body").delegate(".user-checkbox", "click", function() {
        if($(this).attr("checked") == "checked") {
            $(this).removeAttr("checked");
        } else {
            $(this).attr("checked", "checked");
        }
    });

   $("body").delegate("#saveUser", "click", function() {
        var teamId = $("#saveUser").attr("data");
        var userIds = "";
        $('.user-content input:checkbox[name=user-name]:checked').each(function(index,element){
            var userId = $(this).attr("id");
            if(index != 0) {
                userIds += "," + userId;
            } else {
                userIds += userId;
            }
        });
        $.ajax({
            type: "POST",
            url: "/rest/updateUsersOfTeam",
            dataType: "json",
            data: {
                "teamId": teamId,
                "userIds": userIds
            },
            success: function(data) {
                layer.msg("Update successfully!")
            },
            error: function(e) {
                layer.msg("Update users in team failed")
            }
        })
   })
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
                   teams = data.result;
                   buildTable();
               }  else {
                   var tbody = $("#teamTable").find("tbody");
                   tbody.empty();
                }
          },
          function(){
                alert("error!");
          }
    );
}

function buildTable(){
    var tbody = $("#teamTable").find("tbody");
    tbody.empty();
   if(teams!=null){
      for(var i=0;i<teams.length;i++){
           var index = i + 1;
           var tr =$("<tr></tr>");
           var codeTd =$('<td class="line-center">'+index+'</td>');
           var nameTd = $('<td class="line-center">'+teams[i].name+'</td>');
           var descTd = $('<td class="line-center">'+teams[i].description+'</td>');
           var operateTd =$('<td class="option-center"></td>');
           var usersBtn = $('<button id="users_'+teams[i].id+'" type="button" class="btn btn-info btn-xs operation-btn">Users</button>');
           var editBtn = $('<button id="routeEdit_'+teams[i].id+'"type="button" class="btn btn-primary btn-xs operation-btn" >Edit</button>');
           var dropBtn = $('<button id="routeDel_'+teams[i].id+'"type="button" class="btn btn-danger btn-xs operation-btn" >Delete</button>');
           operateTd.append(usersBtn);
           operateTd.append(editBtn);
           operateTd.append(dropBtn);
           tr.append(codeTd);
           tr.append(nameTd);
           tr.append(descTd);
           tr.append(operateTd);
           tbody.append(tr);
           usersBtn.click(function() {
               var btnId = $(this).eq(0).attr("id");
               var splits = btnId.split('_');
               var teamId = splits[1];
              $.ajax({
                    type: "POST",
                    url: "/rest/getUserList",
                    dataType: "json",
                    data: {
                        "teamId": teamId
                    },
                    success: function(data) {
                        var userList = data.result;
                        var userHtml = '';
                        if (userList != null && userList.length > 0) {
                            for(var i = 0, len = userList.length; i < len; i++) {
                                var user = userList[i];
                                if(user.inTeam == 1) {

                                    userHtml += '<div class="col-xs-6 user-item"><label><input type="checkbox" class="user-checkbox" name="user-name" id="' + user.id + '" checked/>'+ user.name +'</label></div>';
                                } else {
                                    userHtml += '<div class="col-xs-6 user-item"><label><input type="checkbox" class="user-checkbox" name="user-name" id="' + user.id + '"/>'+ user.name +'</label></div>';
                                }
                            }
                            var content = '<div><div class="user-container">' +
                                          '    <div class="row">' +
                                          '         <div class="user-content">' +userHtml + '</div>' +
                                          '     </div>'+
                                          '     <div class="row"><div class="col-lg-12 save-container"><button type="button" class="btn btn-xs btn-primary" id="saveUser" >Save</button></div></div>' +
                                          ' </div></div>';
                            layer.open({
                                type: 1,
                                title: "Team Name",
                                closeBtn: 1,
                                shadeClose: true,
                                skin: 'yourclass',
                                content: content
                            });
                            $("#saveUser").attr("data", teamId);
                        } else {
                            layer.msg("There is no user");
                        }
                    },
                    error: function(e) {
                        layer.msg("Get users in team error!");
                    }
              })
           });
           editBtn.click(function(){
                var btnId = $(this).eq(0).attr("id");
                var splits = btnId.split('_');
                var id = splits[1];
                $("#team_id").val(id);
                for(var c=0;c<teams.length;c++){
                    if(teams[c].id==id){
                       $("#new_name").val(teams[c].name);
                       $("#new_description").val(teams[c].description);
                    }
                }
                $(function () { $('#myModal').modal({
                    keyboard: true
                })});
           });
           dropBtn.click(function(){
               var btnId = $(this).eq(0).attr("id");
               var splits = btnId.split('_');
               var id = splits[1];
               layer.confirm('Confirm to delete this team?', {
                   title: 'Delete',
                   btn: ['Delete','Cancel'], //按钮
                   skin: 'danger'
               }, function(index){
                     params ={};
                     params.id = id;
                     $.njx(
                          basePath + "/rest/deleteTeam",
                           params,
                           true,
                          "GET",
                          "json",
                          "application/json",
                           function(data){
                                if(data.result!=null){
                                     getTeams();
                                }
                           },function(e){
                                 layer.msg("Delete team error!");
                           }
                     );
                    layer.close(index);
                    layer.msg("Delete successfully!")
               }, function(index){});
           });
      }
   }
}

function addFeedback(){
     $("#team_id").val("");
     $("#new_name").val("");
     $("#new_description").val("");
     $(function () { $('#myModal').modal({
         keyboard: true
     })});
};

function saveTeam(){
        requestParams ={};
        requestParams.id = $("#team_id").val();
        if($("#new_name").val()==null || $("#new_name").val()==""){
            alert("name is null");
            return;
        }
        requestParams.name =  $("#new_name").val();
        requestParams.description =  $("#new_description").val();
        var params = JSON.stringify(requestParams);
        $.njx(
             basePath + "/rest/saveTeam",
              params,
              true,
             "POST",
             "json",
             "application/json",
              function(data){
                   if(data.result!=null){
                       getTeams();
                       $("#closeEdit").click();
                   }
              },
              function(){
                    alert("error!");
              }
        );
}

