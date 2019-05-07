$(document).ready(function(){
         getServiceBaseInfo();
         getMetricsInfo();
});

function getServiceBaseInfo(){
         $.post("/collection/getBaseInfo",null,function(data){
              var name = data.name;
              var host = data.host;
              var port = data.port;
              $("#serviceinfo").html(name +" [ "+host+":"+port+" ] ");
         });

}


function getMetricsInfo(){
         $.post("/collection/getMetricsInfo",null,function(data){


              var gaugeInfo = data.gaugeInfo;
              var counterInfo = data.counterInfo;

              $("#gauge").html("");
              $("#counter").html("");

              for (var key in gaugeInfo) {
                   var obj = gaugeInfo[key];
                   var div = InfoDiv(key,obj+"   ms");
                   $("#gauge").append( div );
              }

              for (var key in counterInfo) {
                   var obj = counterInfo[key];
                   var div = InfoDiv(key,obj + "    times");
                   $("#counter").append( div );

              }
         });


 function InfoDiv(key,value){
    var div = '<li class="one-key">' +
                     '<div class="group1"> ' + key + '</div>'+
                     '<div class="right single">'+
                     '<font color = "green" > ' + value + '</font>'+
                     '</div></li>';
    return div;
 }

}