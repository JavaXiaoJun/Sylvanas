$(document).ready(function(){
         getServiceBaseInfo();
         getMonitorData();
});

function getServiceBaseInfo(){
         $.post("/collection/getBaseInfo",null,function(data){
              var name = data.name;
              var host = data.host;
              var port = data.port;
              $("#serviceinfo").html(name +" [ "+host+":"+port+" ] ");
         });

}


function getMonitorData(){
         $.post("/collection/getMonitorData",null,function(data){

              $("#card-list").html("");
              for (var key in data) {
                   var obj = data[key];
                   var div = InfoDiv(key,obj);

                   $("#card-list").append( div );

              }


         });


 function InfoDiv(key,value){

        var div = '<li class="one-card"><div class="card-wrapper"><div class="card-head">'+
              '<b class="card-head-text">' + key + '</b>' +
              '</div><div class="content-wrapper"><div class="content"><ul class="data-map">';


        for (var k in value) {
                    var obj = value[k];
                    div = div +
                          '<li class="one-key">' +
                           '<div class="group1"> ' + k + '</div>'+
                           '<div class="right single">'+
                           '<font color = "green" > ' + obj + '</font>'+
                           '</div></li>';
               }

 		div = div +
 	          '</ul></div></div></div></li>';


    return div;
 }

}