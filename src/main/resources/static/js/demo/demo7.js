var sheet;
var spread;
var spreadNS
var updateRows = [];
var columnSetting = [];
var  pagIndex = 1;

//脏数据
/*通常，只有单元格值的变更才导致其成为脏数据。
如果单元格是脏数据，其所在的行也将是脏数据。
如果插入了一行并为其中的一个单元格设置了值，那么此行将只是一个插入的行，而不是脏数据。被设值的那个单元格也不是脏数据。
加载绑定数据的项不是脏数据，但是，在通过 spreadSheet 修改绑定数据之后，它将变为脏数据。
脏数据的状态不会因为撤销操作而改变。*/
$(document).ready(function () {

	//spread.fromJSON(JSON.parse(test1));
    spread = new GC.Spread.Sheets.Workbook(document.getElementById("ss"), { sheetCount: 1 });
    spreadNS = GC.Spread.Sheets


    /* spread = new GC.Spread.Sheets.Workbook(document.getElementById("ss"), { sheetCount: 1 });*/
      initSpread(spread);



    function initSpread(spread) {
         sheet = spread.getSheet(0);
        sheet.suspendPaint();

        for (var r = 0; r < 30; r++) {
            sheet.setText(r, 0, "A" + (r + 1));
        }

        for (var c = 1; c < 20; c++) {
            sheet.setText(0, c, String.fromCharCode(65 + c) + "1");
        }

        sheet.resumePaint();
        sheet.clearPendingChanges();





        var _getElementByIdtaResult = _getElementById("taResult");

        function appendResult(txt) {
            _getElementByIdtaResult.value=_getElementByIdtaResult.value + txt + "\n";
        }




        function getChangedCellData(dirtyItem) {
            return ["Cell (", dirtyItem.row, ",", dirtyItem.col, ") changed from ", dirtyItem.oldValue, " to ", dirtyItem.newValue].join("");
        }

        function getDirtyCellData(dirtyItem) {
            return ["Cell (", dirtyItem.row, ",", dirtyItem.col, ") oldValue: ", dirtyItem.oldValue, " newValue: ", dirtyItem.newValue].join("");
        }

        //会触发两次
        spread.bind(spreadNS.Events.CellChanged, function (event, data) {
            var row = data.row, col = data.col;
            if (row === undefined || col === undefined) {
                return;
            }
            if (sheet.hasPendingChanges(row, col)) {
                var dirtyDataArray = sheet.getDirtyCells(row, col);
                if (dirtyDataArray.length > 0) {
                    appendResult(getChangedCellData(dirtyDataArray[0]));
                }
            }
        });


        spread.bind(spreadNS.Events.RowChanged, function (event, data) {
            var row = data.row, count = data.count, propName = data.propertyName;
            if (row === undefined || count === undefined || propName === undefined) {
                return;
            }
            if (propName === "addRows" || propName === "deleteRows") {
                appendResult(propName + " @ " + row + (count > 1 ? " count: " + count : ""));
            }
        });

        _getElementById("btnInsertRow").addEventListener('click',function () {
            var sels = sheet.getSelections();
            var len = sels.length;
            if (len > 0) {
                var s = sels[0];
                sheet.addRows(s.row, 1);
            }
        });
        _getElementById("btnDeleteRow").addEventListener('click',function () {
            var sels = sheet.getSelections();
            var len = sels.length;
            if (len > 0) {
                var s = sels[0];
                sheet.deleteRows(s.row, s.rowCount);
            }
        });

        _getElementById("btnGetSelectionDirtyCells").addEventListener('click',function () {
            var sels = sheet.getSelections();
            var len = sels.length;
            if (len > 0) {
                var s = sels[0];
                var row = s.row, col = s.col;
                if (row < 0) {
                    row = 0;
                }
                if (col < 0) {
                    col = 0;
                }

                var cells = sheet.getDirtyCells(row, col, s.rowCount, s.colCount); //选中区域的 脏数据

                if (cells.length > 0) {
                    appendResult("Dirty Cells:\n" + cells.map(function (item) { return getDirtyCellData(item); }).join("\n"));
                }
            }
        });

        _getElementById("btnGetDirtyCells").addEventListener('click',function () {
            var cells = sheet.getDirtyCells();  //所有的脏数据
            if (cells.length > 0) {
                appendResult("Dirty Cells:\n" + cells.map(function (item) { return getDirtyCellData(item); }).join("\n"));
            }
        });

        _getElementById("btnGetDirtyRows").addEventListener('click',function () {
            var rows = sheet.getDirtyRows();

            if (rows.length > 0) {
                appendResult("Dirty rows @ " + rows.map(function (item) { return item.row; }).join(", "));
            }
        });

        _getElementById("btnGetInsertRows").addEventListener('click',function () {
            var rows = sheet.getInsertRows();

            if (rows.length > 0) {
                appendResult("Inserted rows @ " + rows.map(function (item) { return item.row; }).join(", "));
            }
        });

        _getElementById("btnGetDeleteRows").addEventListener('click',function () {
            var rows = sheet.getDeletedRows();

            if (rows.length > 0) {
                appendResult("Deleted rows @ " + rows.map(function (item) { return item.row; }).join(", "));
            }
        });

        _getElementById("btnClearPendingChanges").addEventListener('click',function () {
            sheet.clearPendingChanges();
        });

        _getElementById("btnSetRowCount").addEventListener('click',function () {
            sheet.setRowCount(60);
        });

        _getElementById("btnSetColumnCount").addEventListener('click',function () {
            sheet.setColumnCount(16);
        });
    };



      function _getElementById(id){
          return document.getElementById(id);
      }



});
