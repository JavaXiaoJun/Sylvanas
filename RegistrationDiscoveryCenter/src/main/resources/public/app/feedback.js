var totalCount;
var totalPage;
var pageQuery={};
pageQuery.pageNum=1;
pageQuery.pageSize=10;

function goto(){
    var num =parseInt($("#pageNum").val());
    if(num>totalPage){
       $("#pageNum").val(totalPage)
    }
    if(0<pageQuery.pageNum && pageQuery.pageNum <= totalPage){
        pageQuery.pageNum = parseInt($("#pageNum").val());
        getFeedBacks();
    }
}

function previous(){
    if(pageQuery.pageNum>1){
        pageQuery.pageNum =pageQuery.pageNum -1;
        $("#pageNum").val(pageQuery.pageNum);
        getFeedBacks();
    }
}

function next(){
    if(pageQuery.pageNum<totalPage){
        pageQuery.pageNum =pageQuery.pageNum + 1;
        $("#pageNum").val(pageQuery.pageNum);
        getFeedBacks();
    }
}


var feedbacks;

$(document).ready(function(){
   $("#pageNum").val("1");
   getFeedBacks();
});

function getFeedBacks(){
   query={};
   var num =pageQuery.pageNum;
   var size = pageQuery.pageSize;
   var limit = (num-1) * size;
   query.limit = limit;
   query.size =  pageQuery.pageSize;
   var params = JSON.stringify(query);
   $.njx(
         basePath + "/rest/getFeedBackList",
          params,
          true,
         "POST",
         "json",
         "application/json",
          function(data){
               if(data.result!=null){
                   feedbacks = data.result.pageData;
                   totalCount = data.result.totalCount;
                   totalPage = Math.ceil(totalCount/pageQuery.pageSize);
                   buildTable();
                   $("#closeEdit").click();
               } else {
                   var tbody = $("#feedbackTable").find("tbody");
                   tbody.empty();
               }
          },
          function(){
                layer.msg("Get feedback list error!");
          }
    );
}

function buildTable(){
   $("#totalPage").html(totalPage);
   var tbody = $("#feedbackTable").find("tbody");
   tbody.empty();
   if(feedbacks!=null){

      for(var i=0;i<feedbacks.length;i++){
           var index = i + 1;
           var tr =$('<tr></tr>');
           var codeTd =$('<td class="line-center">'+index+'</td>');
           var nameTd = $('<td class="line-center">'+feedbacks[i].name+'</td>');
           var contentTd = $('<td class="line-center">'+feedbacks[i].content+'</td>');
           var timeTd =  $('<td class="line-center">'+feedbacks[i].updateTime+'</td>');
           var operateTd =$('<td class="option-center"></td>');
           var editBtn = $('<button id="routeEdit_'+feedbacks[i].id+'" type="button" class="btn btn-primary btn-xs operation-btn">Edit</button>');
           var dropBtn = $('<button id="routeDel_'+feedbacks[i].id+'" type="button" class="btn btn-danger btn-xs operation-btn" >Delete</button>');
           operateTd.append(editBtn);
           operateTd.append(dropBtn);
           tr.append(codeTd);
           tr.append(nameTd);
           tr.append(contentTd);
           tr.append(timeTd);
           tr.append(operateTd);
           tbody.append(tr);
           editBtn.click(function(){
                var btnId = $(this).eq(0).attr("id");
                var splits = btnId.split('_');
                var id = splits[1];
                $("#feedback_id").val(id);
                for(var c=0;c<feedbacks.length;c++){
                    if(feedbacks[c].id==id){
                       $("#new_name").val(feedbacks[c].name);
                       $("#new_content").val(feedbacks[c].content);
                       $("#saveBtn").attr("data", id);
                    }
                }
                $(function () { $('#myModal').modal({
                    keyboard: true
                })});
           });
           dropBtn.click(function(){
                //询问框
                var btnId = $(this).eq(0).attr("id");
                var splits = btnId.split('_');
                var id = splits[1];
                layer.confirm('Confirm to delete this feedback?', {
                    title: 'Delete',
                    btn: ['Delete','Cancel'], //按钮
                    skin: 'danger'
                }, function(index){
                   params ={};
                   params.id = id;
                   $.njx(
                        basePath + "/rest/deleteFeedBack",
                         params,
                         true,
                        "GET",
                        "json",
                        "application/json",
                         function(data){
                              if(data.result!=null){
                                   getFeedBacks();
                              }
                         },
                         function(){
                               layer.msg("Delete feedback error!");
                         }
                   );
                   layer.close(index);
                   layer.msg("Delete successfully!");
                }, function(){
                });
           });
      }
   }
}

function addFeedback(){
     $("#feedback_id").val("");
     $("#new_name").val("");
     $("#new_content").val("");
     $("#saveBtn").removeAttr("data");
     $(function () { $('#myModal').modal({
         keyboard: true
     })});
};

function saveFeedBack(){
        requestParams ={};
        requestParams.id = $("#feedback_id").val();
        if($("#new_name").val()==null || $("#new_name").val()==""){
            layer.msg("Name is null");
            return ;
        }
        requestParams.name =  $("#new_name").val();
        requestParams.content =  $("#new_content").val();
        var feedbackId = $("#saveBtn").attr("data");
        if(feedbackId != null && feedbackId != "") {
            requestParams.id = feedbackId;
            var params = JSON.stringify(requestParams);
               $.njx(
                    basePath + "/rest/updateFeedBack",
                     params,
                     true,
                    "POST",
                    "json",
                    "application/json",
                     function(data){
                         if(data.status == "1"){
                             getFeedBacks();
                             $("#closeEdit").click();
                         }
                     },
                     function(){
                           layer.msg("Update feedback error!");
                     }
               );

        } else {
            var params = JSON.stringify(requestParams);
            $.njx(
                 basePath + "/rest/addFeedBack",
                  params,
                  true,
                 "POST",
                 "json",
                 "application/json",
                  function(data){
                       if(data.status == "1"){
                           getFeedBacks();
                           $("#closeEdit").click();
                       }
                  },
                  function(){
                        layer.msg("Save feedback error!");
                  }
            );
        }
}
