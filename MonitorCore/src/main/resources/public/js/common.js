
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg); //获取url中"?"符后的字符串并正则匹配
    var context = "";
    if (r != null)
        context = r[2];
    reg = null;
    r = null;
    return context == null || context == "" || context == "undefined" ? "" : context;
}


function isJSON(str) {
    if (typeof str == 'string') {
        try {
            var obj=JSON.parse(str);
            if(str.indexOf('{')>-1){
                return true;
            }else{
                return false;
            }

        } catch(e) {
            console.log(e);
            return false;
        }
    }else if(typeof str == 'object'){
        return true;
    }
    return false;
}

function timestampToDate(timestamp) {
    var date = new Date(timestamp * 1000);
    Y = date.getFullYear() ;
    M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) ;
    D = date.getDate() ;
    h = date.getHours();
    m = date.getMinutes() ;
    s = date.getSeconds();
    if(D<10){
        D = '0'+ D;
    }
    if(h<10){
        h = '0'+ h;
    }
    if(m<10){
        m = '0'+ m;
    }
    if(s<10){
        s = '0'+ s;
    }
    return Y + '-' + M + '-' + D + ' ' + h + ':' + m + ':' + s;
}

function syntaxHighlightRedisResult(content){
    if( !content ){
        return;
    }
    var res = "";
    if( isJSON(content) ){
        res = syntaxHighlight( content );
    }else{
        var arr = content.split(",");
        var cls = 'string';
        for( var i in arr ){
            var tmps = arr[i].split("=");
            if( tmps.length == 2 ){
                res += '<span class="key">' + tmps[0] + '</span>';
                res += '=';
                res += '<span class="string">' + tmps[1] + '</span><br>';
            }else{
                res += '<span class="redis-result-item ' + cls + '">' + arr[i] + '</span><br>';
            }
        }
    }
    return res;
}


function syntaxHighlightLine(content, splitStr){
    if( !content ){
        return;
    }
    var separate =  splitStr || "\n";
    var arr = content.split( separate );
    var res = "";
    var cls = 'number';
    for( var i in arr ){
        var tmps = arr[i].split(":");
        if( tmps.length == 2 ){
            res += '<span class="key">' + tmps[0] + '</span>';
            res += ':';
            res += '<span class="string">' + tmps[1] + '</span>';
        }else{
            res += '<span class="' + cls + '">' + arr[i] + '</span>';
        }
    }
    return res;
}

function syntaxHighlight(json) {
    if (typeof json != 'string') {
        json = JSON.stringify(json, undefined, 2);
    }
    json = json.replace(/&/g, '&');
    json = json.replace(/</g, '<');
    json = json.replace(/>/g, '>');
    return json.replace(/("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g, function(match) {
        var cls = 'number';
        if (/^"/.test(match)) {
            if (/:$/.test(match)) {
                cls = 'key';
            } else {
                cls = 'string';
            }
        } else if (/true|false/.test(match)) {
            cls = 'boolean';
        } else if (/null/.test(match)) {
            cls = 'null';
        }
        return '<span class="' + cls + '">' + match + '</span>';
    });
}

/*only enter numbers*/
function enterNumber(){
	var keyCode = event.keyCode;
	alert(keyCode);
	if (keyCode >= 48 && keyCode <= 57) {  
        event.returnValue = true;    
    } else {
    	layer.msg("port wrong", function(){});
        event.returnValue = false;    
    } 
}


function firstUpperCase(str) {
    return str.toLowerCase().replace(/\b[a-z]/g,function(s){return s.toUpperCase();});
}