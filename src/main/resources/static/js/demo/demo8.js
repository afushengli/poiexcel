var sheet;
var spread;
var spreadNS
var updateRows = [];
var columnSetting = [];
var  pagIndex = 1;
var unClickNum = 60 * 1;
var clearTime;

$(document).ready(function () {

    //spread.fromJSON(JSON.parse(test1));
    spread = new GC.Spread.Sheets.Workbook(document.getElementById("ss"));
    spreadNS = GC.Spread.Sheets;

    /* spread = new GC.Spread.Sheets.Workbook(document.getElementById("ss"), { sheetCount: 1 });*/
    initSpread(spread);

});



function resetInterval() {
    clearTime =  setInterval(function () {
        unClickNum--
        if (unClickNum <= 0) {
            //socket.emit('roomId', { locked: true, spreadData: fromSpread.toJSON() });

            //进行解锁
            update();

        }
    }, 1000)
}


function initSpread(spread) {
    var spreadNS = GC.Spread.Sheets;

    var sheet = spread.sheets[0];

    var datasource;
    $.ajax({
        type: "POST",
        url: "/user/getStudents",
        data: {id:1},
        async: false,

        success: function(data){
            datasource=data;
        }
    });

    sheet.suspendPaint();
    sheet.setColumnWidth(0, 200);
    sheet.setColumnWidth(1, 120);
    sheet.setColumnWidth(2, 120);
    sheet.setColumnWidth(3, 120);
    sheet.setColumnWidth(4, 120);
    sheet.setColumnWidth(5, 120);
    sheet.setColumnWidth(6, 120);


    // or use bindColumns to bind all custom columns
    var colInfos = [
        { name: 'id', displayName: 'id'},
        { name: 'stuName', displayName: 'stuName' },
        { name: 'stuSex', displayName: 'stuSex'},
        { name: 'stuAge', displayName: 'stuAge'},
        { name: 'stuSchoolName', displayName: 'stuSchoolName' },
        { name: 'stuClassName', displayName: 'stuClassName' },
        { name: 'ver', displayName: 'ver' ,visible: false},
    ];
    sheet.setDataSource(datasource);
    sheet.bindColumns(colInfos);


    /* sheet.setValue(0, 0, 'id');
     sheet.setValue(0, 1, 'stuName');
     sheet.setValue(0, 2, 'stuSex');
     sheet.setValue(0, 3, 'stuAge');
     sheet.setValue(0, 4, 'stuSchoolName');
     sheet.setValue(0, 5, 'stuClassName');
     sheet.setValue(0, 6, 'ver');

     var stus;
     $.ajax({
         type: "POST",
         url: "/user/getStudents",
         data: {id:1},
         async: false,

         success: function(data){
             stus=data;
         }
     });



     //  var stu = { name: "Wang feng", age: 25, sex: "male", address: { postcode: "710075" } };
     var source = new spreadNS.Bindings.CellBindingSource(stu);


     sheet.setBindingPath(1, 0, "id");
     sheet.setBindingPath(1, 1, "stuName");
     sheet.setBindingPath(1, 2, "stuSex");
     sheet.setBindingPath(1, 3, "stuAge");
     sheet.setBindingPath(1, 4, "stuSchoolName");
     sheet.setBindingPath(1, 5, "stuClassName");
     sheet.setBindingPath(1, 6, "ver");

     sheet.setDataSource(source);
     sheet.setSelection(1, 0, 1, 1);
     var path = sheet.getBindingPath(2, 2);
     //sheet.getRange(2, 2, 6, 1).backColor("rgb(208,206,206)");*/
    sheet.resumePaint();
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
}



function protect(id,flag){
    var datasource = [];
    $.ajax({
        type: "POST",
        url: "/user/getStudents",
        data: {id:id},
        async: false,

        success: function(data){
            datasource=data;
        }
    });


    var sheet = spread.getActiveSheet();

    var colInfos = [
        { name: 'id', displayName: 'id'},
        { name: 'stuName', displayName: 'stuName'},
        { name: 'stuSex', displayName: 'stuSex' },
        { name: 'stuAge', displayName: 'stuAge'},
        { name: 'stuSchoolName', displayName: 'stuSchoolName'},
        { name: 'stuClassName', displayName: 'stuClassName' },
        { name: 'ver', displayName: 'ver' ,visible: false},
    ];
    sheet.setDataSource(datasource);
    sheet.bindColumns(colInfos);

   return datasource;

}




function edit1(){

    var  datasource  =  protect(1,false);
    var sheet = spread.getActiveSheet();
    var  sheetName = sheet.name();

    resetInterval();

    $.ajax({
        type: "POST",
        url: "/opreate/edit",
        async: false,
        data: {sheetName: sheetName},
        success: function(data){
            if(data.status == 0){

                spread.suspendPaint();

                for(var i = 0;i<datasource.length;i++){
                    //获取隐藏域的值
                    //sheet.getCell(i,  0).locked(false);
                    sheet.getCell(i,  1).locked(false);
                    sheet.getCell(i,  2).locked(false);
                    sheet.getCell(i,  3).locked(false);
                    sheet.getCell(i,  4).locked(false);
                    sheet.getCell(i,  5).locked(false);

                }
                sheet.resumePaint();
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



            }else{
                var aa= data.data;
                alert(aa+"正在修改");
            }
        }
    });
}

function  update(){
    var dirtyCells = spread.getActiveSheet().getDirtyCells();
    var len = dirtyCells.length;
    var sheet = spread.getActiveSheet();
    var  sheetName = sheet.name();

    window.clearInterval(clearTime);


    var stus = [];

    if (len > 0) {
        for (var i = 0; i < len; i++) {
            var cell = dirtyCells[i];
            var stu ={};
            var value = sheet.getValue(cell.row, 0);
            console.log(value);

            if(value !== null && value !== undefined && value !== ''){

                //ajax 把数据发送到后台
                stu.id =  value;
                stu.stuName = sheet.getValue(cell.row, 1);
                stu.stuSex = sheet.getValue(cell.row, 2);
                stu.stuAge = sheet.getValue(cell.row, 3);
                stu.stuSchoolName = sheet.getValue(cell.row, 4);
                stu.stuClassName = sheet.getValue(cell.row, 5);
                stu.ver = sheet.getValue(cell.row, 6);

                stus.push(stu);
                console.log(stus);


            }
        }


        $.ajax({
            type: 'POST',
            url: '/opreate/save',
            dataType: 'json',
            async: false,
            data:  {"sheetName":sheetName,"stuList":JSON.stringify(stus)  },   //将Json对象序列化成Json字符串，toJSON()需要引用jquery.json.min.js,
            success: function (data) {
                if(data.status==1){
                    alert(data.msg);
                }else if(data.status==0){
                    alert(data.msg);
                    var  datasource  = protect(1,true);

                    for(var i = 0;i<datasource.length;i++){
                        //获取隐藏域的值
                        //sheet.getCell(i,  0).locked(false);
                        sheet.getCell(i,  1).locked(true);
                        sheet.getCell(i,  2).locked(true);
                        sheet.getCell(i,  3).locked(true);
                        sheet.getCell(i,  4).locked(true);
                        sheet.getCell(i,  5).locked(true);

                    }
                    sheet.resumePaint();
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


                }
            }
        });
    }


    //没有任何操作了
    document.body.onclick = function () {
        unClickNum = 60 * 1;
    }
    document.body.onkeydown = function () {
        unClickNum = 60 * 1;
    }

}

//页面关闭  或者  重新加载
window.onbeforeunload = function (e) {
    var message = 'some word';
    e = e || window.event;

    if (e) {
        e.returnValue = message;
        update();
    }

    return message;
};



