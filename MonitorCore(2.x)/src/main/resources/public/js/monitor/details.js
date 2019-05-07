$(document).ready(function(){

         getServiceBaseInfo();
         getServiceInfo();

         $(".field").click(function(){
              var field = $(this).html();
              getServiceActuatorInfo(field);
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
              url:"/collection/metricsInfo",
              data:data,
              contentType: "application/json",
              dataType: "json",
              success: function(data) {

                  $("#content").html(syntaxHighlight(data));
                  $("#title").html(field);

              }
      });
  }

function getServiceInfo(){
         $.post("/collection/getServiceInfo",null,function(data){

             $("#info").html("");

             for (var key in data) {
                  var obj = data[key];
                  var div = InfoDiv(key,obj);
                  $("#info").append( div );
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

