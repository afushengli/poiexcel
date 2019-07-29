var sheet;
var spread;
var spreadNS
var updateRows = [];
var columnSetting = [];
var  pagIndex = 1;

var unClickNum = 10 * 1; //几秒自动保存
var longTime = 25 * 1; //长时间不操作


var clearTime;  //定时器的名称

var  longTimeNoOPperate ;

var  sendHeart ;
var fileName;
var editCount =0;

$(document).ready(function () {


    var searchURL = window.location.search;
    searchURL = decodeURI(searchURL.substring(1, searchURL.length));
    fileName = searchURL.split("=")[1];

    console.log(fileName);

    //spread.fromJSON(JSON.parse(test1));
    spread = new GC.Spread.Sheets.Workbook(document.getElementById("ss"));
    spreadNS = GC.Spread.Sheets;

    //请求后台的文件数据
    importFileFormBack(fileName);


    //没有任何操作了
    document.body.onclick = function () {
        unClickNum = 10 * 1;
        longTime =  25 * 1;
    }


    document.body.onkeydown = function () {
        unClickNum = 10 * 1;
        longTime =  25 * 1;
    }







});

function importFileFormBack(fileName){
    var excelIo = new GC.Spread.Excel.IO();
    // Download Excel file
    var xhr = new XMLHttpRequest();
    xhr.open('GET', "/user/downFileFromBack?fileName="+fileName+"", true); //同步请求
    xhr.responseType = 'blob';
    xhr.onload = function(e) {

        if (this.status == 200) {
            // get binary data as a response
            var blob = this.response;
            // convert Excel to JSON
            excelIo.open(blob, function (json) {
                var workbookObj = json;
                spread.fromJSON(workbookObj);

                var sheet = spread.getActiveSheet();
                sheet.resumePaint();

                //表单保护
                sheet.options.isProtected = true;
                //设置表单保护属性
                var option = {
                    allowSelectLockedCells:true,
                    allowSelectUnlockedCells:true,
                    allowFilter: true,
                    allowResizeRows: true,
                    allowResizeColumns: true
                };
                sheet.options.protectionOptions = option;
            }, function (e) {
                // process error
                alert(e.errorMessage);
            }, {});
        }

    };

    xhr.send();
}



function editBefore(fileName){
    var excelIo = new GC.Spread.Excel.IO();
    var xhr = new XMLHttpRequest();
    xhr.open('GET', "/user/downFileFromBack?fileName="+fileName+"", true);
    xhr.responseType = 'blob';
    xhr.onload = function(e) {

        if (this.status == 200) {
            var blob = this.response;
            excelIo.open(blob, function (json) {
                var workbookObj = json;
                spread.fromJSON(workbookObj);

            }, function (e) {
                // process error
                alert(e.errorMessage);
            }, {});
        }
    };
    xhr.send();
}




//自动保存
function resetInterval() {

    clearInterval(clearTime);
    clearTime =null;


    clearTime =  setInterval(function () {
        unClickNum--
        if (unClickNum <= 0) {
            //进行自动保存
            //autoUpdate();
        }
    }, 1000)
}



function edit1(){

    //编辑之前要先加载一下数据
    editBefore(fileName);

    //1.长时间不保存
    clearInterval(longTimeNoOPperate);
    longTimeNoOPperate =null;
   longTimeNoOPperate =  setInterval(function () {
        longTime--;
        if (longTime <= 0) {
            update(false);
        }
    }, 1000)

    //2
    clearInterval(sendHeart);
    sendHeart =null;
    sendHeart=  setInterval(function () {
        $.ajax({
            type: "POST",
            url: "/opreate/sendHearteat",
            data: {sheetName: sheetName ,fileName:fileName,dateTime:  new Date().format("yyyy-MM-dd hh:mm:ss")},
            success: function(data){
                if (data.status == 0) {
                    console.log(data.msg )
                } else {
                    console.log(data.msg )
                }
            }
        });
    }, 3000)


    //3.自动保存
    resetInterval();




    var sheet = spread.getActiveSheet();
    var  sheetName = sheet.name();
    $.ajax({
        type: "POST",
        url: "/opreate/editFile",
        async: false,
        data: {sheetName: sheetName ,fileName:fileName},
        success: function(data){
            if(data.status == 0){
                var sheet = spread.getActiveSheet();
                sheet.options.isProtected = false;
                editCount++;
            }else{
                var aa= data.data;
                alert(aa+"正在修改");

                var sheet = spread.getActiveSheet();
                sheet.options.isProtected = true;
            }
        }
    });





}




function  update(flag){




    unClickNum = 10 * 1;
    longTime = 25 * 1;
    editCount =0;



    var dirtyCells = spread.getActiveSheet().getDirtyCells();
    var len = dirtyCells.length;
    var sheet = spread.getActiveSheet();
    var  sheetName = sheet.name();


    var json = spread.toJSON({includeBindingSource: true});
    var excelIo = new GC.Spread.Excel.IO();
    // here is excel IO API
    excelIo.save(json, function (blob) {
        var fd = new FormData(document.forms.namedItem("myform"));
        fd.append("file", blob,fileName);  //fileName  是文件名   https://www.jianshu.com/p/282eda9d1d0a

        $.ajax({
            url: "/opreate/saveExport",
            type: "POST",
            contentType: false,
            processData: false,
            data: fd,
            success: function (data) {

                if(flag){

                    console.log("手动保存");
                    if (data.status == 0) {

                        clearInterval(sendHeart);
                        sendHeart =null;

                        clearInterval(longTimeNoOPperate);
                        longTimeNoOPperate =null;

                        clearInterval(clearTime);
                        clearTime =null;


                        alert(data.msg);


                    } else {
                        alert(data.msg);
                    }
                }else{

                    if (data.status == 0) {
                        console.log("长时间不操作:"+ data.msg);

                        clearInterval(sendHeart);
                        sendHeart =null;

                        clearInterval(longTimeNoOPperate);
                        longTimeNoOPperate =null;

                        clearInterval(clearTime);
                        clearTime =null;


                    } else {
                        console.log("长时间不操作:"+ data.msg);
                    }
                }

                sheet.options.isProtected = true;
            },
            error: function (ex) {
                console.log("上传失败:"+ ex);
               // alert("上传失败：" + ex);
            }
        });
    }, function (e) {
        alert(e);
    });


}


//自动保存，不解锁，前端也不保护表单
function autoUpdate(){

    unClickNum = 10 * 1;
    longTime = 25 * 1;


    var dirtyCells = spread.getActiveSheet().getDirtyCells();
    var len = dirtyCells.length;
    var sheet = spread.getActiveSheet();
    var  sheetName = sheet.name();


    if (len > 0) {
        editCount =0;
        var json = spread.toJSON({includeBindingSource: true});
        var excelIo = new GC.Spread.Excel.IO();
        // here is excel IO API
        excelIo.save(json, function (blob) {
            var fd = new FormData(document.forms.namedItem("myform"));
            fd.append("file", blob,fileName);  //fileName  是文件名   https://www.jianshu.com/p/282eda9d1d0a
            $.ajax({
                url: "/opreate/saveData",
                type: "POST",
                contentType: false,
                processData: false,
                data: fd,
                success: function (data) {
                    console.log(data);
                    if (data.status == 0) {
                        console.log("自动保存成功:"+data.msg )
                    } else {
                        console.log("自动保存失败1:"+data.msg )
                    }
                },
                error: function (ex) {
                    console.log("自动保存失败2:"+ex );
                }
            });
        }, function (e) {
            console.log("自动保存失败3:"+e);
        });
    }else{

        if(editCount>0) {
            editCount = 0;
            var json = spread.toJSON({includeBindingSource: true});
            var excelIo = new GC.Spread.Excel.IO();
            excelIo.save(json, function (blob) {

                var fd = new FormData(document.forms.namedItem("myform"));
                fd.append("file", blob, fileName);  //fileName  是文件名   https://www.jianshu.com/p/282eda9d1d0a
                $.ajax({
                    url: "/opreate/saveData",
                    type: "POST",
                    contentType: false,
                    processData: false,
                    data: fd,
                    success: function (data) {
                        console.log(data);
                        if (data.status == 0) {
                            //alert(data.msg);
                            console.log(data.msg);
                        } else {
                            // alert(data.msg);
                            console.log(data.msg);
                        }
                    }
                });
            }, function (e) {
                console.log("自动保存失败3:" + e);
            });
        }


      /*  if(editCount>0){
            editCount =0;
            $.ajax({
                url: "/opreate/saveData",
                type: "POST",
                data: {fileName:fileName,sheetName:null},
                success: function (data) {
                    console.log(data);
                    if (data.status == 0) {
                        //alert(data.msg);
                        console.log(data.msg);
                    } else {
                       // alert(data.msg);
                        console.log(data.msg);
                    }
                }
            });
          }*/
    }
}




//页面关闭  或者  重新加载
window.onbeforeunload = function (e) {
    var message = 'some word';
    e = e || window.event;

    if (e) {
        e.returnValue = message;
        update(false);   //保存
    }
    return message;
};



//date 扩展
(function () {
    Date.prototype.format = function (format) {
        var weekDay = ["星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];
        var o = {
            "M+": this.getMonth() + 1, //month
            "d+": this.getDate(), //day
            "h+": this.getHours(), //hour
            "m+": this.getMinutes(), //minute
            "s+": this.getSeconds(), //second
            "q+": Math.floor((this.getMonth() + 3) / 3), //quarter
            "S": this.getMilliseconds(), //millisecond
            "w": weekDay[this.getDay()] //week
        }

        if (/(y+)/.test(format)) {
            format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        }

        for (var k in o) {
            if (new RegExp("(" + k + ")").test(format)) {
                format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
            }
        }
        return format;
    };
})();





