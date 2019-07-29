var sheet;
var spread;
var SheetArea;
var updateRows = [];
var columnSetting = [];
var pagIndex = 1;

//表单保护
//通过行为改变值
//行与列
//获取与设置数据
var row;
var cloumn;
var rowCount;
var columnCount;
var cell;
var excelIo ;

$(document).ready(function () {

	spread = new GC.Spread.Sheets.Workbook(document.getElementById('ss'));

	//spread.fromJSON(JSON.parse(test1));

    SheetArea = GC.Spread.Sheets.SheetArea;

    excelIo = new GC.Spread.Excel.IO();
   /* sheet = spread.getActiveSheet();

    sheet.getCell(0,0).value("1");*/

    //spread.suspendPaint();
   // spread.options.tabStripRatio = 0.8;

    importData();

    var datasource = '';


});


function export1() {

    var fileName = document.getElementById('exportFileName').value;
    if (fileName.substr(-5, 5) !== '.xlsx') {
        fileName += '.xlsx';
    }

    var json = spread.toJSON();

    // here is excel IO API
    excelIo.save(json, function(blob) {
        saveAs(blob, fileName);
    }, function(e) {
        // process error
        console.log(e);
    }, {
        //password: password
    });

}


function import1() {
    var  begin = new  Date().getTime();
    var excelFile = document.getElementById("fileDemo").files[0];
    var password = document.getElementById('password').value;
    // here is excel IO API
    excelIo.open(excelFile, function(json) {
        console.log(excelFile  + "    1111");
        console.log("json:" +JSON.stringify(json));

        var  workbookObj = json;
        spread.fromJSON(workbookObj);

        var  end = new  Date().getTime();
        console.log(end -begin);


        //允许扩展粘贴
        spread.options.allowExtendPasteRange = true;





        spread.options.highlightInvalidData = true; //导入的时候加高亮显示

        //var sheet = spread.getActiveSheet();

        //console.log(JSON.stringify( sheet.toJSON()));
        //导入的时候，需要在每个sheet加上校验

        var sheetCount = spread.getSheetCount();
        // console.log(sheetCount); //获取

        for(var j=0; j<sheetCount; j++) {
            var sheet = spread.getSheet(j);
            sheet.bind(GC.Spread.Sheets.Events.ValidationError, function(e, args) {
                console.log(args);
                // 判断是否展示错误信息
                if (args.validator.showErrorMessage()) {
                    // 选择框，选确定后重新输入，选取消可以强制执行
                    console.log(args.validator.errorMessage());
                    if(args.validator.errorMessage() !=null && args.validator.errorMessage().length >0 ){
                        alert(args.validator.errorMessage());
                    }
                }
            });
        }
    }, function(e) {
        // process error
        alert(e.errorMessage);
        if (e.errorCode === 2 /*noPassword*/ || e.errorCode === 3 /*invalidPassword*/ ) {
            document.getElementById('password').onselect = null;
        }
    }, {
        password: password
    });
}

function addThreeRow(){
    sheet = spread.getActiveSheet();

    if(row == undefined){
        alert("请选中行");
        return;
    }

    if(rowCount>1 ){
        alert("不能选择多行，请选择单行");
        return;
    }

    spread.suspendPaint();
    var value = sheet.getValue(row, 7);
    var value1 = sheet.getValue(row+1, 7);


    if(value==3 && value1!=4 ){ //在3级中间
        //单纯添加行
        addRow(row+1,3.1);
    }else{
        var nextRow =  row;
         while(true) {
             nextRow++;
             var mm = sheet.getValue(nextRow , 7);
             if (mm == 3) {
                 addRow(nextRow,3.1)
                 break;
             }
             if (mm == 2) {
                 addRow(nextRow,3.1)
                 break;
             }
        }
    }
    spread.resumePaint();
}

//添加样式
function addRow(rowNum,hiddenNo) {
    sheet.addRows(rowNum, 1);

    var value = sheet.getValue(rowNum-1, 7);
    var value1 = sheet.getValue(rowNum+1, 7);

    //复制格式
    //为了复制里面的公式，做了全复制，把里面的内容清空
    if(value == 3){//在3级后面插入
        if(value1==3){
            sheet.copyTo(rowNum+1,0,rowNum,0,1,8,GC.Spread.Sheets.CopyToOptions.all);
        }else if(value1==2){
            sheet.copyTo(rowNum-1,0,rowNum,0,1,8,GC.Spread.Sheets.CopyToOptions.all);
        }else if(value1==3.1){
            sheet.copyTo(rowNum+1,0,rowNum,0,1,8,GC.Spread.Sheets.CopyToOptions.all);
        }
    }else{
        if(value==2){ //在2级后面插入
            if(value1==3){ //后面是3级
                sheet.copyTo(rowNum+1,0,rowNum,0,1,8,GC.Spread.Sheets.CopyToOptions.all);
            }else if(value1==2){
                var nextTreeLevel = rowNum;
                while(true){
                    nextTreeLevel--;
                    if(sheet.getValue(nextTreeLevel, 7)==3){
                        sheet.copyTo(nextTreeLevel,0,rowNum,0,1,8,GC.Spread.Sheets.CopyToOptions.all);
                        break;
                    }
                }
            }
        }else if (value==4 && value1==3) {
            sheet.copyTo(rowNum+1,0,rowNum,0,1,8,GC.Spread.Sheets.CopyToOptions.all);
        }
    }


    sheet.getCell(rowNum,  2 ).locked(false);
    sheet.getCell(rowNum,  3 ).locked(false);
    sheet.getCell(rowNum,  4 ).locked(false);
    sheet.getCell(rowNum,  5 ).locked(false);
    sheet.getCell(rowNum,  6 ).locked(false);
    sheet.getCell(rowNum,  7 ).locked(false);
    sheet.getCell(rowNum,7).value(hiddenNo);

    sheet.setFormula(rowNum,3,undefined);
    sheet.setFormula(rowNum,4,undefined);
    sheet.setFormula(rowNum,5,undefined);

    sheet.getCell(rowNum,  2).value("");
    sheet.getCell(rowNum,  3).value("");
    sheet.getCell(rowNum,  4).value("");
    sheet.getCell(rowNum,  5).value("");
    sheet.getCell(rowNum,  6).value("");
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


    var value1 = sheet.getValue(row+1, 7);

    spread.suspendPaint();
    //添加行
    sheet.addRows(row+1, 1);

    if(value==3){

        if(value1==3 || value1==3.1 || value1==2){
            var nextFourLevel = row;
            while(true){
                nextFourLevel++;
                if(sheet.getValue(nextFourLevel, 7)==4){
                    sheet.copyTo(nextFourLevel,0,row+1,0,1,8,GC.Spread.Sheets.CopyToOptions.all);
                    break;
                }
            }
        }else{
            sheet.copyTo(row+2,0,row+1,0,1,8,GC.Spread.Sheets.CopyToOptions.all);
            //修改公式
            if(sheet.hasFormula(row, 4)){    //是不是有公式
                setFormula2(row,4);
            }

            if(sheet.hasFormula(row, 5)){
                setFormula2(row,5);
            }
        }

    }else if(value==4){
        sheet.copyTo(row,0,row+1,0,1,8,GC.Spread.Sheets.CopyToOptions.all);
    }else if(value==3.1){
        var preTreeLevel = row;
        var flag =true;
        while(true){
            if(flag){
                preTreeLevel--;
                if(preTreeLevel ==0 ){
                    flag = false;
                }
                if(sheet.getValue(preTreeLevel, 7)==4){
                    sheet.copyTo(preTreeLevel,0,row+1,0,1,8,GC.Spread.Sheets.CopyToOptions.all);
                    break;
                }
            }else{
                preTreeLevel++;
                if(sheet.getValue(preTreeLevel, 7)==4){
                    sheet.copyTo(preTreeLevel,0,row+1,0,1,8,GC.Spread.Sheets.CopyToOptions.all);
                    break;
                }
            }
        }
    }



    sheet.getCell(row+1,  2).locked(false);
    sheet.getCell(row+1,  3).locked(false);
    sheet.getCell(row+1,  4).locked(false);
    sheet.getCell(row+1,  6).locked(false); //备注
    sheet.getCell(row+1,  5).locked(false);

    sheet.getCell(row+1,  2).value("");
    sheet.getCell(row+1,  3).value("");
    sheet.getCell(row+1,  4).value("");
    sheet.getCell(row+1,  6).value();


    sheet.getCell(row+1,7).value(4);
    //修改公式
    // 判断是在最后一个插入，如果是的话，修改上一级的公式
    var nextHiddenVal = sheet.getValue(row+2, 7);
    var  parentLevelRow = row;  //此处必须赋值，不能直接改变 row 的值,否则会导致删除出错

    if(nextHiddenVal!=4){
        //查看添加的行的父级所在的行
        while(true) {
            parentLevelRow--;
            var mm = sheet.getValue(parentLevelRow , 7);
            if (mm == 3) {
                //修改公式
                if(sheet.hasFormula(parentLevelRow, 4)){    //是不是有公式
                    setFormula(parentLevelRow,4);
                }

                if(sheet.hasFormula(parentLevelRow, 5)){    //是不是有公式
                    setFormula(parentLevelRow,5);
                }
                break;
            } else if(mm == 2){
                //设置公式
                break;
            }
        }
    }

    spread.resumePaint();
}

//设置单元格公式
function setFormula2(rowNum,cloumNum){
    //获取公公式
    var formula1  = sheet.getFormula(rowNum, cloumNum);
    //console.log(typeof formula1  );

    var lastFlag = formula1.substring(formula1.indexOf('(')+1,formula1.indexOf(':'));
    var nextLastFlagInt =  parseInt(lastFlag.substring(1,lastFlag.length))-1;

    var lastFlag1 = lastFlag.substring(0,1) + nextLastFlagInt;
    //console.log(lastFlag1);

    var formula1 = formula1.replace(lastFlag,lastFlag1);
    //console.log(formula1);

    sheet.getCell(rowNum, cloumNum).formula("="+formula1);

}

//设置单元格公式
function setFormula(rowNum,cloumNum){
    //获取公公式
    var formula1  = sheet.getFormula(rowNum, cloumNum);
    //console.log(typeof formula1  );

    var lastFlag = formula1.substring(formula1.indexOf(':')+1,formula1.length-1);
    var nextLastFlagInt =  parseInt(lastFlag.substring(1,lastFlag.length))+1;

    var lastFlag1 = lastFlag.substring(0,1) + nextLastFlagInt;
    //console.log(lastFlag1);

    var formula1 = formula1.replace(lastFlag,lastFlag1);
    //console.log(formula1);

    sheet.getCell(rowNum, cloumNum).formula("="+formula1);

}



function deleteRow(){
    if(row == undefined){
        alert("请选中要删除的行");
        return;
    }
    var value = sheet.getValue(row, 7);
    console.log(row);
    console.log(value);
    if(value <=3){
        alert("不能够删除的科目");
        return;
    }

    if(value ==4){
        sheet.deleteRows(row, 1);
    }

    if(value==3.1){
        //删除所有的
        var deleteNum = row;
        var dd =0;
        while(true){
            deleteNum++;
            dd++;
            if(sheet.getValue(deleteNum, 7) ==3 || sheet.getValue(deleteNum, 7) ==2 || sheet.getValue(deleteNum, 7) ==3.1 ){
                sheet.deleteRows(row, dd);
                break;
            }
        }
    }

}

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
           /* var style = new GC.Spread.Sheets.Style();
            style.locked = false;
            //style.backColor = 'lightGreen';
            sheet.setStyle(i,  2, style);
            sheet.setStyle(i,  3, style);
            sheet.setStyle(i,  4, style);
            sheet.setStyle(i,  6, style); //备注*/
        }
    }
    sheet.setActiveCell(-1, -1);//去掉默认选中的单元格，https://gcdn.grapecity.com.cn/showtopic.aspx?forumid=185&topicid=19340&go=prev

    spread.resumePaint();
}






function importData(){
    var excelIo = new GC.Spread.Excel.IO();

    console.time("测试后台导入excel");

    //spread.fromJSON(importson);
    var xhr = new XMLHttpRequest();
    xhr.open('GET', "/user/downLoadWork", true);
    xhr.responseType = 'blob';
    xhr.onload = function(e) {

      if (this.status == 200) {
        // get binary data as a response
        var blob = this.response;
        // convert Excel to JSON
        excelIo.open(blob, function (json) {
            var workbookObj = json;
            spread.fromJSON(workbookObj);

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

        }, function (e) {
            // process error
            alert(e.errorMessage);
        }, {});
      }

    };

    xhr.send();
    console.timeEnd("测试后台导入excel");
}





