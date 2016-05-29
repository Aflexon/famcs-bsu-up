function guid() {
    return s4() + s4() + '-' + s4() + '-' + s4() + '-' +
        s4() + '-' + s4() + s4() + s4();
}

function s4() {
    return Math.floor((1 + Math.random()) * 0x10000)
        .toString(16)
        .substring(1);
}

function cloneObject(obj){
    var key, clone = {};
    for(key in obj) if(obj.hasOwnProperty(key)) clone[key] = obj[key];
    return clone;
}

String.prototype.supplant = function(o) {
    return this.replace(/\{([^{}]*)\}/g, function(a, b) {
        var r = o[b];
        return typeof r === 'string' || typeof r === 'number' ? r : a;
    });
};

function htmlentities(s){
    var pre = document.createElement('pre');
    var text = document.createTextNode(s);
    pre.appendChild(text);
    return pre.innerHTML;
}

function formatDate(date){
    var minutes = date.getMinutes();
    if (minutes < 10) minutes = '0' + minutes;

    var hh = date.getHours();
    if (hh < 10) hh = '0' + hh;

    var dd = date.getDate();
    if (dd < 10) dd = '0' + dd;

    var mm = date.getMonth() + 1;
    if (mm < 10) mm = '0' + mm;

    var yy = date.getFullYear();

    return dd + '.' + mm + '.' + yy + ' ' + hh + ':' + minutes;
}
