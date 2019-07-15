var sheet;
var spread;
var SheetArea;
var updateRows = [];
var columnSetting = [];
var pagIndex = 1;

//表单保护
//通过行为改变值
//行与列
var row;
var cloumn;
var rowCount;
var columnCount;
var cell;

$(document).ready(function () {

	spread = new GC.Spread.Sheets.Workbook(document.getElementById('ss'));

	//spread.fromJSON(JSON.parse(test1));

    SheetArea = GC.Spread.Sheets.SheetArea;


    //spread.suspendPaint();
   // spread.options.tabStripRatio = 0.8;

    importData();

    var datasource = '';


});
/*
function addThreeRow(){
    sheet = spread.getActiveSheet();
    if(rowCount>1 ){
        alert("不能选择多行，请选择单行");
        return;
    }

    spread.suspendPaint();
    var value = sheet.getValue(row, 7);

    if(value==3){
        //添加行
        addRow(row+1,3.1);
    }else{
         while(true) {
             row++;
             var mm = sheet.getValue(row , 7);
             if (mm == 3) {
                 addRow(row,3.1)
                 break;
             }
        }
    }
    spread.resumePaint();
}

function addRow(rowNum,hiddenNo) {
    sheet.addRows(rowNum, 1);
    sheet.copyTo(rowNum-1,0,rowNum,0,1,8,GC.Spread.Sheets.CopyToOptions.style);
    var style = new GC.Spread.Sheets.Style();
    style.locked = false;
    sheet.setStyle(rowNum,  2, style);
    sheet.setStyle(rowNum,  3, style);
    sheet.setStyle(rowNum,  4, style);
    sheet.setStyle(rowNum,  6, style); //备注
    sheet.setStyle(rowNum,  7, style); //备注
    sheet.getCell(rowNum,7).value(hiddenNo);
}


function addFourTitleRow() {


    sheet = spread.getActiveSheet();
    if(rowCount>1 ){
        alert("不能选择多行，请选择单行");
        return;
    }
    //获取选中单元格的内容
    var value = sheet.getValue(row, 7);
    if(value <3){
        alert("请在三级、四级标题后选择要添加的四级标题");
        return;
    }

    spread.suspendPaint();
    //添加行
    sheet.addRows(row+1, 1);

    //复制格式
    //GC.Spread.Sheets.CopyToOptions.all
    sheet.copyTo(row,0,row+1,0,1,8,GC.Spread.Sheets.CopyToOptions.style);
    var style = new GC.Spread.Sheets.Style();
    style.locked = false;
    sheet.setStyle(row+1,  2, style);
    sheet.setStyle(row+1,  3, style);
    sheet.setStyle(row+1,  4, style);
    sheet.setStyle(row+1,  6, style); //备注


    sheet.getCell(row+1,7).value(4); //

    //修改公式
    // 判断是在最后一个插入，如果是的话，修改上一级的公式
    var nextHiddenVal = sheet.getValue(row+2, 7);
    if(nextHiddenVal!=4){
        //查看添加的行的父级所在的行
        while(true) {
            row--;
            var mm = sheet.getValue(row , 7);
            if (mm == 3) {
                //修改公式
                if(sheet.hasFormula(6, 4)){    //是不是有公式
                    //获取公公式
                    var formula1  = sheet.getFormula(6, 4);
                    console.log(formula1);
                }
                break;
            }
        }


    }


    spread.resumePaint();
}




function deleteRow(){
    if(row == undefined){
        alert("请选中要删除的行");
        return;
    }
    var value = sheet.getValue(row, 7);
    if(value <=3){
        alert("不能够删除的科目");
        return;
    }
    sheet.deleteRows(row, 1);
}
*/
//设置表格的可编辑列
function abeleEdit(){
    var sheet = spread.getActiveSheet();
    var totalCount = sheet.getRowCount(GC.Spread.Sheets.SheetArea.viewport);
    spread.suspendPaint();


    for(var i =0 ;i<totalCount;i++){
        //获取隐藏域的值
        var value = sheet.getValue(i, 7);
        if(value >=3){
            sheet.getCell(i,  2).locked(false);  //此种方法不涉及样式
            sheet.getCell(i,  3).locked(false);
            sheet.getCell(i,  4).locked(false);
            sheet.getCell(i,  6).locked(false);
            sheet.getCell(i,  5).locked(false);
        }
    }

    spread.resumePaint();
}



function importData(){
    console.time("测试后台导入excel");

    spread.fromJSON(importJson);


            sheet = spread.getActiveSheet();

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

            sheet.bind(GC.Spread.Sheets.Events.SelectionChanged, function (e, info) {
                row = info.newSelections[0].row;
                cloumn = info.newSelections[0].col;
                rowCount =  info.newSelections[0].rowCount;
                columnCount =  info.newSelections[0].colCount ;

                console.log("New Selection(" + "row: " + info.newSelections[0].row + ", " + "column: " + info.newSelections[0].col + ", " + "rowCount: " + info.newSelections[0].rowCount + ", " + "columnCount: " + info.newSelections[0].colCount + ")");
            });

            //设置可读可写属性
            abeleEdit();

    console.timeEnd("测试后台导入excel");
}





