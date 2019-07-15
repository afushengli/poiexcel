var sheet;
var spread;
var updateRows = [];
var columnSetting = [];
$(document).ready(function () { 
	//var test1 = '{"version":"10.1.0","sheetCount":2,"sheets":{"Sheet1":{"name":"Sheet1","rowCount":3,"columnCount":3,"activeRow":2,"activeCol":2,"data":{"dataTable":{"0":{"0":{"style":{"backColor":"rgb(255, 0, 0)"}},"1":{"style":{"backColor":"rgb(255, 0, 0)"}},"2":{"style":{"backColor":"rgb(255, 0, 0)"}}},"1":{"0":{"style":{"backColor":"rgb(255, 0, 0)"}},"1":{"style":{"backColor":"rgb(255, 0, 0)"}},"2":{"style":{"backColor":"rgb(255, 0, 0)"}}},"2":{"0":{"style":{"backColor":"rgb(255, 0, 0)"}},"1":{"style":{"backColor":"rgb(255, 0, 0)"}},"2":{"style":{"backColor":"rgb(255, 0, 0)"}}}},"defaultDataNode":{"style":{"themeFont":"Body"}}},"rowHeaderData":{"defaultDataNode":{"style":{"themeFont":"Body"}}},"colHeaderData":{"defaultDataNode":{"style":{"themeFont":"Body"}}},"selections":{"0":{"row":2,"rowCount":1,"col":2,"colCount":1},"length":1},"theme":"Office","index":0},"Sheet2":{"name":"Sheet2","data":{"dataTable":{}},"selections":{"0":{"row":0,"rowCount":1,"col":0,"colCount":1},"length":1},"theme":"Office","index":1}}}';	
	spread = new GC.Spread.Sheets.Workbook(document.getElementById('ss'), { sheetCount: 1 });
	
	//spread.fromJSON(JSON.parse(test1));
	sheet = spread.getActiveSheet();
	
});


function exportData(){
    var json = spread.toJSON({includeBindingSource: true});
    var excelIo = new GC.Spread.Excel.IO();
    // here is excel IO API   
    excelIo.save(json, function (blob) {
    	var fd = new FormData(document.forms.namedItem("myform"));
    	fd.append("test.xlsx",blob);
    	
    	$.ajax({
    		url: "/user/saveExport.do",
    		type:"POST",
    		contentType:false,
    		processData: false,
    		data:fd,
    		success: function (data) {
    			if(data.isSuccess == 1){
    				alert("上传成功！");
    			}else{
    				alert(data.errorMessage);
    			}
    		},
    		error: function (ex) {
    			alert("上传失败："+ex);
    		}
    	});
    }, function (e) {
    	alert(e);
    });
}

function importData(){
    var excelIo = new GC.Spread.Excel.IO();
    // Download Excel file
    //var excelFilePath = 'resources/Excel/importExcel.xlsx';
    console.time("测试后台导入excel");
    var xhr = new XMLHttpRequest();
    xhr.open('GET', "/user/downLoad.do", true);
    xhr.responseType = 'blob';   
    xhr.onload = function(e) {

      if (this.status == 200) {
        // get binary data as a response
        var blob = this.response;
        // convert Excel to JSON
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
    console.timeEnd("测试后台导入excel");

}




