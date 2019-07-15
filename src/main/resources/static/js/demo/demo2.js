var sheet;
var spread;
var updateRows = [];
var columnSetting = [];
var  pagIndex = 1;

$(document).ready(function () {

	spread = new GC.Spread.Sheets.Workbook(document.getElementById('ss'));

	//spread.fromJSON(JSON.parse(test1));


    defaultPage(false);


    sheet = spread.getActiveSheet();

    sheet.getCell(0,0).value("1");

   /* sheet = spread.getActiveSheet();
    spread.suspendPaint();
    spread.options.tabStripRatio = 0.8;*/

    var datasource = '';


});


function   prePage(flag){

    pagIndex -= 1;

    $.ajax({
        type: 'POST',
        url: '/user/queryPage',
        dataType: 'json',
        async: false,
        data:{
            pageIndex:pagIndex
        },
        success: function (data) {
            pagIndex= data.pageNumber;
            datasource =data.data;
        }
    });

    if(flag){
        sheetBading(datasource);
    }else{
        //importData(datasource); //绑定的模板

        templateTableBbding(datasource);
    }

}


function   nextPage(flag){


    pagIndex += 1;

    $.ajax({
        type: 'POST',
        url: '/user/queryPage',
        dataType: 'json',
        async: false,
        data:{
            pageIndex:pagIndex
        },
        success: function (data) {

            pagIndex= data.pageNumber;
            datasource =data.data;
        }
    });


    if(flag){
        sheetBading(datasource);
    }else{
        templateTableBbding(datasource);
        //importData(datasource); //绑定的模板
    }
    //templateTableBbding(datasource); //绑定的模板，此方法不成功，因为会因为请求的数据的多少产生不和谐变化

}


function  templateTableBbding(datasource){
    sheet = spread.getActiveSheet();

   // var table1 = sheet.tables.all()[0];

    //console.log(table1)

    //表单绑定
    var data = {
        stus:datasource
    };

   // table1.bindingPath('stus');

 /*   for( var i = 0; i<datasource.length ;i++){
        sheet.copyTo(1,0,2+i,0,1,6,GC.Spread.Sheets.CopyToOptions.all);
    }*/


    //给sheet 绑定数据
    var source = new GC.Spread.Sheets.Bindings.CellBindingSource(data);
    sheet.setDataSource(source);

    sheet.repaint();
}


//默认加载
function defaultPage(sheet){
    $.ajax({
        type: 'GET',
        url: '/user/queryPage',
        dataType: 'json',
        async: false,
        success: function (data) {
            datasource =data.data;
        }
    });

    if(sheet){
        sheetBading(datasource);
    }else{
        importData(datasource);

    }

}


//sheet绑定
function  sheetBading(datasource){
    sheet = spread.getActiveSheet();
    sheet.autoGenerateColumns = true;
    sheet.setDataSource(datasource);

    spread.resumePaint();
    sheet.clearPendingChanges();
}


//表格绑定
function   tableBbding(datasource){

    sheet = spread.getActiveSheet();

    console.log(datasource.length)


    sheet.autoGenerateColumns = true;

    //在第2行后插入后台数据长度的行
    sheet.addRows(2, datasource.length);

    //复制粘贴
    //instance.copyTo(fromRow, fromColumn, toRow, toColumn, rowCount, columnCount, option);
    //复制第二行的数据 粘贴
    for( var i = 0; i<datasource.length ;i++){
        sheet.copyTo(1,0,2+i,0,1,6,GC.Spread.Sheets.CopyToOptions.all);
    }


    //表单绑定
    var data = {
        stus:datasource
    };

    var tableColumns = [],
        names = ['id', 'stuName', 'stuSex', 'stuAge','stuSchoolName','stuClassName'],
        labels = ['id11', 'stuName', 'stuSex', 'stuAge','stuSchoolName','stuClassName'];  //excel表头的名字

    /*    var value; // Type: any
        value = GC.Spread.Sheets.Tables.TableThemes;

        console.log(value);*/

    console.log(GC.Spread.Sheets.Tables.TableThemes);

    //表格样式
    var tableStyle = GC.Spread.Sheets.Tables.TableThemes.medium3;

    //从第二行开始，datasource.length+1 因为还有个表格头
    var table = sheet.tables.add('tableRecords', 2, 0, datasource.length+1, 6,tableStyle);

    console.log(table)

    //设置表单  ，从第一行的，第0列，行是 后台数据的长度，列是属性的个数
    table.autoGenerateColumns(false);
    names.forEach(function (name, index) {
        var tableColumn = new GC.Spread.Sheets.Tables.TableColumn();
         tableColumn.name(labels[index]);
        tableColumn.dataField(name);
        tableColumns.push(tableColumn);
    });

    table.bindColumns(tableColumns);
    table.bindingPath('stus');

    table.showHeader(false);

    var source = new GC.Spread.Sheets.Bindings.CellBindingSource(data);
    sheet.setDataSource(source);





    //sheet.setRowVisible(2,false,GC.Spread.Sheets.SheetArea.viewport);



    //把表格的样式设置为没有样式
    var table1 = sheet.tables.all()[0];
    table1.style(null);
    sheet.repaint();


    //从第1行开始删除1行
    sheet.deleteRows(1, 2)





    //sheet.deleteRows(datasource.length+1, 1)



}



function importData(datasource){
    var excelIo = new GC.Spread.Excel.IO();

    console.time("测试后台导入excel");

    //spread.fromJSON(importson);


    // convert Excel to JSON
    // Download Excel file
    //var excelFilePath = 'resources/Excel/importExcel.xlsx';
    var xhr = new XMLHttpRequest();
    xhr.open('GET', "/user/downLoad", true);
    xhr.responseType = 'blob';
    xhr.onload = function(e) {

      if (this.status == 200) {
        // get binary data as a response
        var blob = this.response;
        // convert Excel to JSON
        excelIo.open(blob, function (json) {
            var workbookObj = json;
            spread.fromJSON(workbookObj);

            tableBbding(datasource);


        }, function (e) {
            // process error
            alert(e.errorMessage);
        }, {});
      }

    };

    xhr.send();
    console.timeEnd("测试后台导入excel");
}





