var M = {}
$(document).ready(function(){

      getServiceBaseInfo();

      $(".field").click(function(){

             var field = $(this).html();

             if(field == "httptrace"){

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
                                   var data = '{"field":"httptrace","start":"'+start+'","end":"'+end+'"}';
                                   $.ajax({
                                                type: "POST",
                                                url:"/collection/httptrace",
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
                  if(field == "conditions"){
                    $("#title").html(field + " ( 应用的自动化配置条件和原因 ) ");
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
                  if(field == "scheduledtasks"){
                      $("#title").html(field + " ( 应用中配置的定时任务 ) ");
                      return;
                  }
                  if(field == "loggers"){
                      $("#title").html(field + " ( 应用中修饰和配置的日志 ) ");
                      return;
                  }
                  if(field == "threaddump"){
                      $("#title").html(field + " ( 应用内存中线程使用信息 ) ");
                      return;
                  }

              }
      });

}