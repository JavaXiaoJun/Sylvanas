var totalCount;
var totalPage;
var pageQuery={};
pageQuery.pageNum=1;
pageQuery.pageSize=10;

function goto(){
    var num =parseInt($("#pageNum").val());
    if(num>totalPage){
       $("#pageNum").val(totalPage);
    }
    if(0<pageQuery.pageNum && pageQuery.pageNum <= totalPage){
        pageQuery.pageNum = parseInt($("#pageNum").val());
        getNewsList();
    }
}

function previous(){
    if(pageQuery.pageNum>1){
        pageQuery.pageNum =pageQuery.pageNum -1;
        $("#pageNum").val(pageQuery.pageNum);
        getNewsList();
    }
}

function next(){
    if(pageQuery.pageNum<totalPage){
        pageQuery.pageNum =pageQuery.pageNum + 1;
        $("#pageNum").val(pageQuery.pageNum);
        getNewsList();
    }
}


var newsList;

$(document).ready(function(){
   $("#pageNum").val("1");
   getNewsList();
});

function getNewsList(){
    var query={};
    var num = pageQuery.pageNum;
    var size = pageQuery.pageSize;
    var limit = (num-1) * size;
    query.limit = limit;
    query.size =  pageQuery.pageSize;
    var params = JSON.stringify(query);
    $.ajax({
        type: "POST",
        url: "/rest/getNewsList",
        dataType: "json",
        data: params,
        contentType: "application/json",
        success: function(data) {
            if(data.result != null){
                newsList = data.result.pageData;
                totalCount = data.result.totalCount;
                totalPage = Math.ceil(totalCount/pageQuery.pageSize);
                console.log("totalCount:" +totalCount+ " totalPage:" + totalPage)
                console.log(newsList)
                buildTable();
                $("#closeEdit").click();
            } else {
                var tbody = $("#newsTable").find("tbody");
                tbody.empty();
            }
        },
        error: function(e) {
            layer.msg("Get news list error!");
        }
    })
}

function buildTable(){
   $("#totalPage").html(totalPage);
   var tbody = $("#newsTable").find("tbody");
   tbody.empty();
   if(newsList != null && newsList.length > 0){

      for(var i=0;i<newsList.length;i++){
           var index = i + 1;
           var tr =$('<tr></tr>');
           var codeTd =$('<td>'+index+'</td>');
           var titleTd = $('<td>'+newsList[i].title+'</td>');
           var contentTd = $('<td>'+newsList[i].content+'</td>');
           var timeTd =  $('<td>'+newsList[i].updateTime+'</td>');
           var operateTd =$('<td></td>');
           var editBtn = $('<button id="routeEdit_'+newsList[i].newsId+'" type="button" class="btn btn-primary btn-xs operation-btn">Edit</button>');
           var deleteBtn = $('<button id="routeDel_'+newsList[i].newsId+'" type="button" class="btn btn-danger btn-xs operation-btn" >Delete</button>');
           operateTd.append(editBtn);
           operateTd.append(deleteBtn);
           tr.append(codeTd);
           tr.append(titleTd);
           tr.append(contentTd);
           tr.append(timeTd);
           tr.append(operateTd);
           tbody.append(tr);
           editBtn.click(function(){
                var btnId = $(this).attr("id");
                var splits = btnId.split('_');
                var newsId = splits[1];
                for(var c = 0; c < newsList.length; c++){
                    if(newsList[c].newsId == newsId){
                       $("#newsTitle").val(newsList[c].title);
                       $("#newsContent").val(newsList[c].content);
                       $("#newsId").attr("data", newsId);
                    }
                }
                $(function () { $('#myModal').modal({
                    keyboard: true
                })});
           });
           deleteBtn.click(function(){
                //询问框
                var btnId = $(this).attr("id");
                var splits = btnId.split('_');
                var newsId = splits[1];
                layer.confirm('Confirm to delete this news?', {
                    title: 'Delete',
                    btn: ['Delete','Cancel'], //按钮
                    skin: 'danger'
                }, function(index){
                   $.ajax({
                       type: "POST",
                       url: "/rest/deleteNews",
                       dataType: "json",
                       data: {
                            "newsId": newsId
                       },
                       success: function(data) {
                           if(data.status == "1"){
                               getNewsList();
                           }
                       },
                       error: function(e) {
                           layer.msg("Delete news error!");
                       }
                   });
                   layer.close(index);
                   layer.msg("Delete successfully!");
                }, function(){
                });
           });
      }
   }
}

function addNews(){
     $("#newsId").removeAttr("data");
     $("#newsTitle").val("");
     $("#newsContent").val("");
     $(function () { $('#myModal').modal({
         keyboard: true
     })});
};

function saveNews(){
        var newsId = $("#newsId").attr("data");
        var newsTitle = $("#newsTitle").val();
        var newsContent = $("#newsContent").val();
        requestParams ={};
        requestParams.newsId = newsId;
        if(newsTitle == null || newsTitle == ""){
            layer.msg("Name is null");
            return ;
        }
        requestParams.title = newsTitle;
        requestParams.content = newsContent;
        console.log(requestParams)
        console.log(newsId)
        if(newsId != null && newsId != "") {
            requestParams.newsId = newsId;
            var params = JSON.stringify(requestParams);
            $.ajax({
                type: "POST",
                url: "/rest/updateNews",
                dataType: "json",
                data: params,
                contentType: "application/json",
                success: function(data) {
                    if(data.status == "1"){
                        getNewsList();
                        $("#closeEdit").click();
                    }
                },
                error: function(e) {
                    layer.msg("Update news error!");
                }
            });
        } else {
            var params = JSON.stringify(requestParams);
            $.ajax({
                type: "POST",
                url: "/rest/addNews",
                dataType: "json",
                data: params,
                contentType: "application/json",
                success: function(data) {
                    console.log(data)
                    if(data.status == "1"){
                        getNewsList();
                        $("#closeEdit").click();
                    }
                },
                error: function(e) {
                    layer.msg("Save news error!");
                }
            });
        }
}
