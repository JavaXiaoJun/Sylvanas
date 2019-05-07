$(document).ready(function(){
         getServiceBaseInfo();
         getHealthInfo();
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

function getHealthInfo(){
         $.post("/collection/getHealthInfo",null,function(data){

              var description = data.description;
              var discoveryComposite = data.discoveryComposite;
              var diskSpace = data.diskSpace;
              var refreshScope = data.refreshScope;
              var hystrix = data.hystrix;

              if(description != null){
                 $("#description").html(description);
              }else{
                 $("#description").html("--");
              }

              if(discoveryComposite != null){
                    if(discoveryComposite.description != null){
                       $("#discoveryComposite-description").html(discoveryComposite.description);
                    }else{
                       $("#discoveryComposite-description").html("--");
                    }
                    if(discoveryComposite.services != null){
                       $("#services").html(discoveryComposite.services);
                    }else{
                       $("#services").html("--");
                    }
              }else{
                   $("#discoveryComposite-description").html("--");
                   $("#services").html("--");
              }

              if(diskSpace != null){
                    if(diskSpace.status == "DWON"){
                         $("#status").attr("color","red");
                         $("#status").html(diskSpace.status);
                    }
                    if(diskSpace.total != null){
                       $("#total").html(diskSpace.total+"G");
                    }else{
                       $("#total").html("--");
                    }
                    if(diskSpace.free != null){
                       $("#free").html(diskSpace.free+"G");
                    }else{
                       $("#free").html("--");
                    }
                    if(diskSpace.threshold != null){
                       $("#threshold").html(diskSpace.threshold);
                    }else{
                       $("#threshold").html("--");
                    }
              }else{
                   $("#total").html("--");
                   $("#free").html("--");
                   $("#threshold").html("--");
              }

              if(refreshScope != null){
                 if(refreshScope == "DWON"){
                   $("#refreshScope").attr("color","red")
                 }else{
                   $("#refreshScope").attr("color","green")
                 }
                 $("#refreshScope").html(refreshScope);
              }else{
                 $("#refreshScope").html("--");
              }

              if(hystrix != null){
                 if(hystrix == "DWON"){
                   $("#hystrix").attr("color","red")
                 }else{
                   $("#hystrix").attr("color","green")
                 }
                 $("#hystrix").html(hystrix);
              }else{
                  $("#hystrix").html("--");
              }
         });

}

function getMetricsInfo(){
         $.post("/collection/getMetricsInfo",null,function(data){

              var systemInfo = data.systemInfo;
              var memoryInfo = data.memoryInfo;
              var heapInfo = data.heapInfo;
              var nonHeapInfo = data.nonHeapInfo;
              var threadInfo = data.threadInfo;
              var classesInfo = data.classesInfo;
              var gcInfo = data.gcInfo;
              var httpsessionsInfo = data.httpsessionsInfo;
             /* var gaugeInfo = data.gaugeInfo;
              var counterInfo = data.counterInfo;*/
              if(systemInfo != null){
               $("#uptime").html(systemInfo.uptime + " [ d:h:m:s ]");
               $("#systemload").html(systemInfo.systemload_average);
               $("#processers").html(systemInfo.processors + " processors");
              }

              if(classesInfo != null){
                 $("#current_classes").html(classesInfo.classes_loaded);
                 $("#total_classes").html(classesInfo.classes);
                 $("#unload").html(classesInfo.classes_unloaded);
              }

              if(threadInfo != null){
                 $("#current_thread").html(threadInfo.threads);
                 $("#total_start").html(threadInfo.threads_totalStarted);
                 $("#daemon").html(threadInfo.threads_daemon);
                 $("#peak").html(threadInfo.threads_peak);
              }

              if(gcInfo != null){
                 $("#scavenge_count").html(gcInfo.gc_ps_scavenge_count);
                 $("#scavenge_time").html(gcInfo.gc_ps_scavenge_time +" ms");
                 $("#marksweep_count").html(gcInfo.gc_ps_marksweep_count);
                 $("#marksweep_time").html(gcInfo.gc_ps_marksweep_time +" ms");
              }

              if(httpsessionsInfo != null){
                 if(httpsessionsInfo.httpsessions_active != null){
                   $("#active").html(httpsessionsInfo.httpsessions_active);
                 }else{
                   $("#active").html("--");
                 }
                 if(httpsessionsInfo.httpsessions_active != null){
                   $("#maximun").html(httpsessionsInfo.httpsessions_max);
                 }else{
                   $("#maximun").html("--");
                 }
              }

              if(memoryInfo != null){
                    $("#memory").html("Memory("+memoryInfo.mem_free+"M/"+memoryInfo.mem+"M)");
                    $("#memory_meter").attr("value",memoryInfo.per)
              }

              if(heapInfo != null){
                    $("#heap_memory").html("Heap Memory("+heapInfo.heap_used+"M/"+heapInfo.heap_committed+"M)");
                    $("#heap_memory_meter").attr("value",heapInfo.per)
                    $("#inital_heap").html(heapInfo.heap_init+"M");
                    $("#max_heap").html(heapInfo.heap+"M");
              }

              if(nonHeapInfo != null){
                    $("#nonheap_memory").html("Non-Heap Memory("+nonHeapInfo.nonheap_used+"M/"+nonHeapInfo.nonheap_committed+"M)");
                    $("#nonheap_memory_meter").attr("value",nonHeapInfo.per)
                    $("#inital_nonheap").html(nonHeapInfo.nonheap_init+"M");
                    $("#max_nonheap").html(nonHeapInfo.nonheap);
              }

         });

}