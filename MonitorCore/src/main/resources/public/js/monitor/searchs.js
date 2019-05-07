var M = {}
$(document).ready(function(){

      getServiceBaseInfo();

      $(".field").click(function(){

             var field = $(this).html();

             if(field == "trace"){

                  M.dialog3 = jqueryAlert({
                      'content' : '<section class="panel"><header class="panel-heading" style="margin-left:5px">起止时间戳(默认展示最近10条)</header>'+
                                                  '   <div class="panel-body"><form class="form-inline" role="form"><div class="form-group"><label class="sr-only" for="start">start time</label>'+
                                                  '   <input type="text" class="form-control" id="start" placeholder="start time"></div>'+
                                                  '   <div class="form-group"><label class="sr-only" for="end">endtime</label><input type="text" class="form-control" id="end" placeholder="end time"></div>',
                      'modal'   : true,
                      'buttons' :{
                          '确定' : function(){
                                   var start = $("#start").val();
                                   var end = $("#end").val();
                                   var data = '{"field":"trace","start":"'+start+'","end":"'+end+'"}';
                                   $.ajax({
                                                type: "POST",
                                                url:"/collection/trace",
                                                data:data,
                                                contentType: "application/json",
                                                dataType: "json",
                                                success: function(data) {
                                                  $("#content").html(syntaxHighlight(data));
                                                  $("#title").html(field + " ( 基本的HTTP跟踪信息 ) ");
                                                  $("#monitorModal").modal("show");
                                                  M.dialog3.close();
                                                }
                                   });


                          }
                      }
                  })
              }else{

                 $("#monitorModal").modal("show");
                 getServiceActuatorInfo(field);
              }

      });

      $("#closeModal").click(function(){
              $("#content").html("");
              $("#monitorModal").modal("hide");
              location.reload() ;
      });
});

function getServiceBaseInfo(){
         $.post("/collection/getBaseInfo",null,function(data){
              var name = data.name;
              var host = data.host;
              var port = data.port;
              $("#serviceinfo").html(name +" [ "+host+":"+port+" ] ");
         });

}


function getServiceActuatorInfo(field){
      var data = '{"field":"'+field+'"}';
      $.ajax({
              type: "POST",
              url:"/collection/"+field,
              data:data,
              contentType: "application/json",
              dataType: "json",
              success: function(data) {

                  $("#content").html(syntaxHighlight(data));

                  if(field == "beans"){
                    $("#title").html(field + " ( 上下文中创建的所有Bean信息 ) ");
                    return;
                  }
                  if(field == "env"){
                    $("#title").html(field + " ( 可用的环境属性信息 ) ");
                    return;
                  }
                  if(field == "configprops"){
                    $("#title").html(field + " ( 应用中配置的属性信息 ) ");
                    return;
                  }
                  if(field == "mappings"){
                    $("#title").html(field + " ( MVC的控制器映射关系 ) ");
                    return;
                  }
                  if(field == "autoconfig"){
                    $("#title").html(field + " ( 应用的自动化配置信息 ) ");
                    return;
                  }
                  if(field == "info"){
                    $("#title").html(field + " ( 一些应用自定义的信息 ) ");
                    return;
                  }
                  if(field == "health"){
                    $("#title").html(field + " ( 应用健康状态信息 ) ");
                   return;
                  }
              }
      });

}