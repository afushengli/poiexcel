var sheet;
var spread;
var updateRows = [];
var columnSetting = [];

//var  importson = {"version":"12.0.0","namedStyles":[{"foreColor":"Text 1 0","themeFont":"Body","font":"normal normal 14.7px DengXian","backColor":"Accent 1 80","name":"20% - Accent1"},{"foreColor":"Text 1 0","themeFont":"Body","font":"normal normal 14.7px DengXian","backColor":"Accent 2 80","name":"20% - Accent2"},{"foreColor":"Text 1 0","themeFont":"Body","font":"normal normal 14.7px DengXian","backColor":"Accent 3 80","name":"20% - Accent3"},{"foreColor":"Text 1 0","themeFont":"Body","font":"normal normal 14.7px DengXian","backColor":"Accent 4 80","name":"20% - Accent4"},{"foreColor":"Text 1 0","themeFont":"Body","font":"normal normal 14.7px DengXian","backColor":"Accent 5 80","name":"20% - Accent5"},{"foreColor":"Text 1 0","themeFont":"Body","font":"normal normal 14.7px DengXian","backColor":"Accent 6 80","name":"20% - Accent6"},{"foreColor":"Text 1 0","themeFont":"Body","font":"normal normal 14.7px DengXian","backColor":"Accent 1 60","name":"40% - Accent1"},{"foreColor":"Text 1 0","themeFont":"Body","font":"normal normal 14.7px DengXian","backColor":"Accent 2 60","name":"40% - Accent2"},{"foreColor":"Text 1 0","themeFont":"Body","font":"normal normal 14.7px DengXian","backColor":"Accent 3 60","name":"40% - Accent3"},{"foreColor":"Text 1 0","themeFont":"Body","font":"normal normal 14.7px DengXian","backColor":"Accent 4 60","name":"40% - Accent4"},{"foreColor":"Text 1 0","themeFont":"Body","font":"normal normal 14.7px DengXian","backColor":"Accent 5 60","name":"40% - Accent5"},{"foreColor":"Text 1 0","themeFont":"Body","font":"normal normal 14.7px DengXian","backColor":"Accent 6 60","name":"40% - Accent6"},{"foreColor":"Background 1 0","themeFont":"Body","font":"normal normal 14.7px DengXian","backColor":"Accent 1 40","name":"60% - Accent1"},{"foreColor":"Background 1 0","themeFont":"Body","font":"normal normal 14.7px DengXian","backColor":"Accent 2 40","name":"60% - Accent2"},{"foreColor":"Background 1 0","themeFont":"Body","font":"normal normal 14.7px DengXian","backColor":"Accent 3 40","name":"60% - Accent3"},{"foreColor":"Background 1 0","themeFont":"Body","font":"normal normal 14.7px DengXian","backColor":"Accent 4 40","name":"60% - Accent4"},{"foreColor":"Background 1 0","themeFont":"Body","font":"normal normal 14.7px DengXian","backColor":"Accent 5 40","name":"60% - Accent5"},{"foreColor":"Background 1 0","themeFont":"Body","font":"normal normal 14.7px DengXian","backColor":"Accent 6 40","name":"60% - Accent6"},{"foreColor":"Background 1 0","themeFont":"Body","font":"normal normal 14.7px DengXian","backColor":"Accent 1 0","name":"Accent1"},{"foreColor":"Background 1 0","themeFont":"Body","font":"normal normal 14.7px DengXian","backColor":"Accent 2 0","name":"Accent2"},{"foreColor":"Background 1 0","themeFont":"Body","font":"normal normal 14.7px DengXian","backColor":"Accent 3 0","name":"Accent3"},{"foreColor":"Background 1 0","themeFont":"Body","font":"normal normal 14.7px DengXian","backColor":"Accent 4 0","name":"Accent4"},{"foreColor":"Background 1 0","themeFont":"Body","font":"normal normal 14.7px DengXian","backColor":"Accent 5 0","name":"Accent5"},{"foreColor":"Background 1 0","themeFont":"Body","font":"normal normal 14.7px DengXian","backColor":"Accent 6 0","name":"Accent6"},{"foreColor":"#9c0006","themeFont":"Body","font":"normal normal 14.7px DengXian","backColor":"#ffc7ce","name":"Bad"},{"foreColor":"#fa7d00","themeFont":"Body","font":"normal bold 14.7px DengXian","borderLeft":{"color":"#7f7f7f","style":1},"borderRight":{"color":"#7f7f7f","style":1},"borderTop":{"color":"#7f7f7f","style":1},"borderBottom":{"color":"#7f7f7f","style":1},"borderVertical":null,"borderHorizontal":null,"diagonalUp":null,"diagonalDown":null,"backColor":"#f2f2f2","name":"Calculation"},{"foreColor":"Background 1 0","themeFont":"Body","font":"normal bold 14.7px DengXian","borderLeft":{"color":"#3f3f3f","style":6},"borderRight":{"color":"#3f3f3f","style":6},"borderTop":{"color":"#3f3f3f","style":6},"borderBottom":{"color":"#3f3f3f","style":6},"borderVertical":null,"borderHorizontal":null,"diagonalUp":null,"diagonalDown":null,"backColor":"#a5a5a5","name":"Check Cell"},{"backColor":null,"formatter":"_(* #,##0.00_);_(* (#,##0.00);_(* \"-\"??_);_(@_)","name":"Comma"},{"backColor":null,"formatter":"_(* #,##0_);_(* (#,##0);_(* \"-\"_);_(@_)","name":"Comma [0]"},{"backColor":null,"formatter":"_(\"$\"* #,##0.00_);_(\"$\"* (#,##0.00);_(\"$\"* \"-\"??_);_(@_)","name":"Currency"},{"backColor":null,"formatter":"_(\"$\"* #,##0_);_(\"$\"* (#,##0);_(\"$\"* \"-\"_);_(@_)","name":"Currency [0]"},{"foreColor":"#7f7f7f","themeFont":"Body","font":"italic normal 14.7px DengXian","backColor":null,"name":"Explanatory Text"},{"foreColor":"#006100","themeFont":"Body","font":"normal normal 14.7px DengXian","backColor":"#c6efce","name":"Good"},{"foreColor":"Text 2 0","themeFont":"Body","font":"normal bold 20px DengXian","borderLeft":null,"borderRight":null,"borderTop":null,"borderBottom":{"color":"Accent 1 0","style":5},"borderVertical":null,"borderHorizontal":null,"diagonalUp":null,"diagonalDown":null,"backColor":null,"name":"Heading 1"},{"foreColor":"Text 2 0","themeFont":"Body","font":"normal bold 17.3px DengXian","borderLeft":null,"borderRight":null,"borderTop":null,"borderBottom":{"color":"Accent 1 50","style":5},"borderVertical":null,"borderHorizontal":null,"diagonalUp":null,"diagonalDown":null,"backColor":null,"name":"Heading 2"},{"foreColor":"Text 2 0","themeFont":"Body","font":"normal bold 14.7px DengXian","borderLeft":null,"borderRight":null,"borderTop":null,"borderBottom":{"color":"Accent 1 40","style":2},"borderVertical":null,"borderHorizontal":null,"diagonalUp":null,"diagonalDown":null,"backColor":null,"name":"Heading 3"},{"foreColor":"Text 2 0","themeFont":"Body","font":"normal bold 14.7px DengXian","backColor":null,"name":"Heading 4"},{"foreColor":"#3f3f76","themeFont":"Body","font":"normal normal 14.7px DengXian","borderLeft":{"color":"#7f7f7f","style":1},"borderRight":{"color":"#7f7f7f","style":1},"borderTop":{"color":"#7f7f7f","style":1},"borderBottom":{"color":"#7f7f7f","style":1},"borderVertical":null,"borderHorizontal":null,"diagonalUp":null,"diagonalDown":null,"backColor":"#ffcc99","name":"Input"},{"foreColor":"#fa7d00","themeFont":"Body","font":"normal normal 14.7px DengXian","borderLeft":null,"borderRight":null,"borderTop":null,"borderBottom":{"color":"#ff8001","style":6},"borderVertical":null,"borderHorizontal":null,"diagonalUp":null,"diagonalDown":null,"backColor":null,"name":"Linked Cell"},{"foreColor":"#9c6500","themeFont":"Body","font":"normal normal 14.7px DengXian","backColor":"#ffeb9c","name":"Neutral"},{"foreColor":"Text 1 0","themeFont":"Body","font":"normal normal 16px DengXian","locked":true,"textIndent":0,"wordWrap":false,"vAlign":2,"hAlign":3,"borderLeft":null,"borderRight":null,"borderTop":null,"borderBottom":null,"borderVertical":null,"borderHorizontal":null,"diagonalUp":null,"diagonalDown":null,"backColor":null,"name":"Normal"},{"borderLeft":{"color":"#b2b2b2","style":1},"borderRight":{"color":"#b2b2b2","style":1},"borderTop":{"color":"#b2b2b2","style":1},"borderBottom":{"color":"#b2b2b2","style":1},"borderVertical":null,"borderHorizontal":null,"diagonalUp":null,"diagonalDown":null,"backColor":"#ffffcc","name":"Note"},{"foreColor":"#3f3f3f","themeFont":"Body","font":"normal bold 14.7px DengXian","borderLeft":{"color":"#3f3f3f","style":1},"borderRight":{"color":"#3f3f3f","style":1},"borderTop":{"color":"#3f3f3f","style":1},"borderBottom":{"color":"#3f3f3f","style":1},"borderVertical":null,"borderHorizontal":null,"diagonalUp":null,"diagonalDown":null,"backColor":"#f2f2f2","name":"Output"},{"backColor":null,"formatter":"0%","name":"Percent"},{"foreColor":"Text 2 0","themeFont":"Headings","font":"normal bold 24px DengXian Light","backColor":null,"name":"Title"},{"foreColor":"Text 1 0","themeFont":"Body","font":"normal bold 14.7px DengXian","borderLeft":null,"borderRight":null,"borderTop":{"color":"Accent 1 0","style":1},"borderBottom":{"color":"Accent 1 0","style":6},"borderVertical":null,"borderHorizontal":null,"diagonalUp":null,"diagonalDown":null,"backColor":null,"name":"Total"},{"foreColor":"#ff0000","themeFont":"Body","font":"normal normal 14.7px DengXian","backColor":null,"name":"Warning Text"},{"foreColor":"Text 1 0","themeFont":"Body","font":"normal normal 16px DengXian","locked":true,"textIndent":0,"wordWrap":false,"vAlign":2,"hAlign":3,"borderLeft":null,"borderRight":null,"borderTop":null,"borderBottom":null,"borderVertical":null,"borderHorizontal":null,"diagonalUp":null,"diagonalDown":null,"backColor":null,"name":"__builtInStyle1"},{"foreColor":"Text 1 0","themeFont":"Body","font":"normal normal 16px DengXian","locked":true,"textIndent":0,"wordWrap":false,"vAlign":2,"hAlign":1,"borderLeft":null,"borderRight":null,"borderTop":null,"borderBottom":null,"borderVertical":null,"borderHorizontal":null,"diagonalUp":null,"diagonalDown":null,"backColor":null,"name":"__builtInStyle2"},{"foreColor":"Text 1 0","themeFont":"Body","font":"normal normal 16px DengXian","locked":true,"textIndent":0,"wordWrap":false,"vAlign":2,"hAlign":1,"borderLeft":null,"borderRight":null,"borderTop":null,"borderBottom":null,"borderVertical":null,"borderHorizontal":null,"diagonalUp":null,"diagonalDown":null,"backColor":null,"formatter":"General","name":"__builtInStyle3"},{"foreColor":"Text 1 0","themeFont":"Body","font":"normal normal 16px DengXian","locked":true,"textIndent":0,"wordWrap":false,"vAlign":2,"hAlign":1,"borderLeft":null,"borderRight":null,"borderTop":null,"borderBottom":null,"borderVertical":null,"borderHorizontal":null,"diagonalUp":null,"diagonalDown":null,"backColor":null,"formatter":"0.00_);[Red]\\(0.00\\)","name":"__builtInStyle4"},{"foreColor":"Text 1 0","themeFont":"Body","font":"normal normal 16px DengXian","locked":true,"textIndent":0,"wordWrap":false,"vAlign":2,"hAlign":3,"borderLeft":null,"borderRight":null,"borderTop":null,"borderBottom":null,"borderVertical":null,"borderHorizontal":null,"diagonalUp":null,"diagonalDown":null,"backColor":null,"formatter":"General","name":"__builtInStyle5"},{"foreColor":"Text 1 0","themeFont":"Body","font":"normal normal 16px DengXian","locked":true,"textIndent":0,"wordWrap":false,"vAlign":2,"hAlign":1,"borderLeft":{"color":"#000000","style":1},"borderRight":{"color":"#000000","style":3},"borderTop":{"color":"#000000","style":1},"borderBottom":{"color":"#000000","style":3},"borderVertical":null,"borderHorizontal":null,"diagonalUp":null,"diagonalDown":null,"backColor":null,"formatter":"General","name":"__builtInStyle6"},{"foreColor":"Text 1 0","themeFont":"Body","font":"normal normal 16px DengXian","locked":true,"textIndent":0,"wordWrap":false,"vAlign":2,"hAlign":1,"borderLeft":{"color":"#000000","style":3},"borderRight":{"color":"#000000","style":3},"borderTop":{"color":"#000000","style":1},"borderBottom":{"color":"#000000","style":3},"borderVertical":null,"borderHorizontal":null,"diagonalUp":null,"diagonalDown":null,"backColor":null,"formatter":"General","name":"__builtInStyle7"},{"foreColor":"Text 1 0","themeFont":"Body","font":"normal normal 16px DengXian","locked":true,"textIndent":0,"wordWrap":false,"vAlign":2,"hAlign":1,"borderLeft":{"color":"#000000","style":3},"borderRight":{"color":"#000000","style":3},"borderTop":{"color":"#000000","style":1},"borderBottom":{"color":"#000000","style":3},"borderVertical":null,"borderHorizontal":null,"diagonalUp":null,"diagonalDown":null,"backColor":null,"formatter":"0.00_);[Red]\\(0.00\\)","name":"__builtInStyle8"},{"foreColor":"Text 1 0","themeFont":"Body","font":"normal normal 16px DengXian","locked":true,"textIndent":0,"wordWrap":false,"vAlign":2,"hAlign":3,"borderLeft":{"color":"#000000","style":3},"borderRight":{"color":"#000000","style":3},"borderTop":{"color":"#000000","style":1},"borderBottom":{"color":"#000000","style":3},"borderVertical":null,"borderHorizontal":null,"diagonalUp":null,"diagonalDown":null,"backColor":null,"formatter":"General","name":"__builtInStyle9"},{"foreColor":"Text 1 0","themeFont":"Body","font":"normal normal 16px DengXian","locked":true,"textIndent":0,"wordWrap":false,"vAlign":2,"hAlign":1,"borderLeft":{"color":"#000000","style":1},"borderRight":{"color":"#000000","style":3},"borderTop":{"color":"#000000","style":3},"borderBottom":{"color":"#000000","style":3},"borderVertical":null,"borderHorizontal":null,"diagonalUp":null,"diagonalDown":null,"backColor":"#ffff00","formatter":"General","name":"__builtInStyle10"},{"foreColor":"Text 1 0","themeFont":"Body","font":"normal normal 16px DengXian","locked":true,"textIndent":0,"wordWrap":false,"vAlign":2,"hAlign":1,"borderLeft":{"color":"#000000","style":3},"borderRight":{"color":"#000000","style":3},"borderTop":{"color":"#000000","style":3},"borderBottom":{"color":"#000000","style":3},"borderVertical":null,"borderHorizontal":null,"diagonalUp":null,"diagonalDown":null,"backColor":"#ffff00","formatter":"General","name":"__builtInStyle11"},{"foreColor":"Text 1 0","themeFont":"Body","font":"normal normal 16px DengXian","locked":true,"textIndent":0,"wordWrap":false,"vAlign":2,"hAlign":1,"borderLeft":{"color":"#000000","style":3},"borderRight":{"color":"#000000","style":3},"borderTop":{"color":"#000000","style":3},"borderBottom":{"color":"#000000","style":3},"borderVertical":null,"borderHorizontal":null,"diagonalUp":null,"diagonalDown":null,"backColor":"#ffff00","formatter":"0.00_ ","name":"__builtInStyle12"},{"foreColor":"Text 1 0","themeFont":"Body","font":"normal normal 16px DengXian","locked":true,"textIndent":0,"wordWrap":false,"vAlign":2,"hAlign":1,"borderLeft":{"color":"#000000","style":3},"borderRight":{"color":"#000000","style":3},"borderTop":{"color":"#000000","style":3},"borderBottom":{"color":"#000000","style":3},"borderVertical":null,"borderHorizontal":null,"diagonalUp":null,"diagonalDown":null,"backColor":null,"formatter":"0.00_);[Red]\\(0.00\\)","name":"__builtInStyle13"},{"foreColor":"Text 1 0","themeFont":"Body","font":"normal normal 16px DengXian","locked":true,"textIndent":0,"wordWrap":false,"vAlign":2,"hAlign":3,"borderLeft":{"color":"#000000","style":3},"borderRight":{"color":"#000000","style":3},"borderTop":{"color":"#000000","style":3},"borderBottom":{"color":"#000000","style":3},"borderVertical":null,"borderHorizontal":null,"diagonalUp":null,"diagonalDown":null,"backColor":null,"formatter":"General","name":"__builtInStyle14"},{"foreColor":"Text 1 0","themeFont":"Body","font":"normal normal 16px DengXian","locked":true,"textIndent":0,"wordWrap":false,"vAlign":2,"hAlign":1,"borderLeft":{"color":"#000000","style":1},"borderRight":{"color":"#000000","style":3},"borderTop":{"color":"#000000","style":3},"borderBottom":{"color":"#000000","style":3},"borderVertical":null,"borderHorizontal":null,"diagonalUp":null,"diagonalDown":null,"backColor":null,"formatter":"General","name":"__builtInStyle15"},{"foreColor":"Text 1 0","themeFont":"Body","font":"normal normal 16px DengXian","locked":true,"textIndent":0,"wordWrap":false,"vAlign":2,"hAlign":1,"borderLeft":{"color":"#000000","style":3},"borderRight":{"color":"#000000","style":3},"borderTop":{"color":"#000000","style":3},"borderBottom":{"color":"#000000","style":3},"borderVertical":null,"borderHorizontal":null,"diagonalUp":null,"diagonalDown":null,"backColor":null,"formatter":"General","name":"__builtInStyle16"},{"foreColor":"Text 1 0","themeFont":"Body","font":"normal normal 16px DengXian","locked":true,"textIndent":0,"wordWrap":false,"vAlign":2,"hAlign":1,"borderLeft":{"color":"#000000","style":3},"borderRight":{"color":"#000000","style":3},"borderTop":{"color":"#000000","style":3},"borderBottom":{"color":"#000000","style":3},"borderVertical":null,"borderHorizontal":null,"diagonalUp":null,"diagonalDown":null,"backColor":null,"formatter":"0.00_ ","name":"__builtInStyle17"},{"foreColor":"Text 1 0","themeFont":"Body","font":"normal normal 21.3px DengXian","locked":true,"textIndent":0,"wordWrap":false,"vAlign":2,"hAlign":1,"borderLeft":{"color":"#000000","style":3},"borderRight":{"color":"#000000","style":3},"borderTop":{"color":"#000000","style":3},"borderBottom":{"color":"#000000","style":3},"borderVertical":null,"borderHorizontal":null,"diagonalUp":null,"diagonalDown":null,"backColor":null,"formatter":"General","name":"__builtInStyle18"},{"foreColor":"Text 1 0","themeFont":"Body","font":"normal normal 16px DengXian","locked":true,"textIndent":0,"wordWrap":false,"vAlign":2,"hAlign":2,"borderLeft":{"color":"#000000","style":3},"borderRight":{"color":"#000000","style":3},"borderTop":{"color":"#000000","style":3},"borderBottom":{"color":"#000000","style":3},"borderVertical":null,"borderHorizontal":null,"diagonalUp":null,"diagonalDown":null,"backColor":null,"formatter":"General","name":"__builtInStyle19"},{"foreColor":"Text 1 0","themeFont":"Body","font":"normal normal 16px DengXian","locked":true,"textIndent":0,"wordWrap":false,"vAlign":2,"hAlign":3,"borderLeft":{"color":"#000000","style":3},"borderRight":{"color":"#000000","style":3},"borderTop":{"color":"#000000","style":1},"borderBottom":{"color":"#000000","style":3},"borderVertical":null,"borderHorizontal":null,"diagonalUp":null,"diagonalDown":null,"backColor":null,"formatter":"General","name":"__builtInStyle20"},{"foreColor":"Text 1 0","themeFont":"Body","font":"normal normal 16px DengXian","locked":true,"textIndent":0,"wordWrap":false,"vAlign":2,"hAlign":3,"borderLeft":{"color":"#000000","style":3},"borderRight":{"color":"#000000","style":3},"borderTop":{"color":"#000000","style":3},"borderBottom":{"color":"#000000","style":3},"borderVertical":null,"borderHorizontal":null,"diagonalUp":null,"diagonalDown":null,"backColor":null,"formatter":"General","name":"__builtInStyle21"},{"foreColor":"Text 1 0","themeFont":"Body","font":"normal normal 16px DengXian","locked":true,"textIndent":0,"wordWrap":false,"vAlign":2,"hAlign":3,"borderLeft":null,"borderRight":null,"borderTop":null,"borderBottom":null,"borderVertical":null,"borderHorizontal":null,"diagonalUp":null,"diagonalDown":null,"backColor":null,"name":"__builtInStyle22"},{"foreColor":"Text 1 0","themeFont":"Body","font":"normal normal 16px DengXian","locked":true,"textIndent":0,"wordWrap":false,"vAlign":2,"hAlign":3,"borderLeft":null,"borderRight":null,"borderTop":null,"borderBottom":null,"borderVertical":null,"borderHorizontal":null,"diagonalUp":null,"diagonalDown":null,"backColor":null,"formatter":"General","name":"__builtInStyle23"}],"startSheetIndex":0,"activeSheetIndex":1,"tabStripRatio":0.5,"sheetCount":2,"sheets":{"工作表1":{"name":"工作表1","index":1,"allowCellOverflow":true,"theme":{"name":"Office","themeColor":{"name":"Office","text1":{"a":255,"r":0,"g":0,"b":0},"text2":{"a":255,"r":68,"g":84,"b":106},"background1":{"a":255,"r":255,"g":255,"b":255},"background2":{"a":255,"r":231,"g":230,"b":230},"accent1":{"a":255,"r":68,"g":114,"b":196},"accent2":{"a":255,"r":237,"g":125,"b":49},"accent3":{"a":255,"r":165,"g":165,"b":165},"accent4":{"a":255,"r":255,"g":192,"b":0},"accent5":{"a":255,"r":91,"g":155,"b":213},"accent6":{"a":255,"r":112,"g":173,"b":71},"hyperlink":{"a":255,"r":5,"g":99,"b":193},"followedHyperlink":{"a":255,"r":149,"g":79,"b":114}},"headingFont":"DengXian Light","bodyFont":"DengXian"},"defaults":{"colHeaderRowHeight":20,"rowHeaderColWidth":40,"rowHeight":21.33,"colWidth":100},"rowCount":28,"columnCount":6,"gridline":{"showVerticalGridline":false,"showHorizontalGridline":false},"selections":{"0":{"row":3,"rowCount":1,"col":5,"colCount":1},"length":1},"activeRow":3,"activeCol":5,"columns":[{"size":108},{"size":147},{"size":108},{"size":108},{"size":173},{"size":167}],"data":{"dataTable":{"0":{"0":{"style":"__builtInStyle6","value":"id"},"1":{"style":"__builtInStyle7","value":"stuName"},"2":{"style":"__builtInStyle7","value":"stuSex"},"3":{"style":"__builtInStyle8","value":"stuAge"},"4":{"style":"__builtInStyle9","value":"stuSchoolName"},"5":{"style":"__builtInStyle20","value":"stuClassName"}},"1":{"0":{"style":"__builtInStyle10"},"1":{"style":"__builtInStyle11"},"2":{"style":"__builtInStyle12"},"3":{"style":"__builtInStyle13"},"4":{"style":"__builtInStyle14"},"5":{"style":"__builtInStyle21"}},"2":{"0":{"style":"__builtInStyle15"},"1":{"style":"__builtInStyle16"},"2":{"style":"__builtInStyle17"},"3":{"style":"__builtInStyle13","value":22},"4":{"style":"__builtInStyle14"},"5":{"style":"__builtInStyle19","value":99}},"3":{"0":{"style":"__builtInStyle15"},"1":{"style":"__builtInStyle18","value":2323},"2":{"style":"__builtInStyle17"},"3":{"style":"__builtInStyle13","value":33},"4":{"style":"__builtInStyle14"},"5":{"style":"__builtInStyle19","value":22}},"4":{"0":{"style":"__builtInStyle15"},"1":{"style":"__builtInStyle16"},"2":{"style":"__builtInStyle17"},"3":{"style":"__builtInStyle13"},"4":{"style":"__builtInStyle14"},"5":{"style":"__builtInStyle21"}},"5":{"0":{"style":"__builtInStyle15"},"1":{"style":"__builtInStyle16"},"2":{"style":"__builtInStyle17"},"3":{"style":"__builtInStyle13"},"4":{"style":"__builtInStyle14"},"5":{"style":"__builtInStyle21"}},"6":{"0":{"style":"__builtInStyle1"},"1":{"style":"__builtInStyle1"},"2":{"style":"__builtInStyle1"},"3":{"style":"__builtInStyle1"},"4":{"style":"__builtInStyle1"},"5":{"style":"__builtInStyle22"}},"7":{"0":{"style":"__builtInStyle1"},"1":{"style":"__builtInStyle1"},"2":{"style":"__builtInStyle1"},"3":{"style":"__builtInStyle1"},"4":{"style":"__builtInStyle1"},"5":{"style":"__builtInStyle22"}},"8":{"0":{"style":"__builtInStyle1"},"1":{"style":"__builtInStyle1"},"2":{"style":"__builtInStyle1"},"3":{"style":"__builtInStyle1"},"4":{"style":"__builtInStyle1"},"5":{"style":"__builtInStyle22"}},"9":{"0":{"style":"__builtInStyle1"},"1":{"style":"__builtInStyle1"},"2":{"style":"__builtInStyle1"},"3":{"style":"__builtInStyle1"},"4":{"style":"__builtInStyle1"},"5":{"style":"__builtInStyle22"}},"10":{"0":{"style":"__builtInStyle1"},"1":{"style":"__builtInStyle1"},"2":{"style":"__builtInStyle1"},"3":{"style":"__builtInStyle1"},"4":{"style":"__builtInStyle1"},"5":{"style":"__builtInStyle22"}},"11":{"0":{"style":"__builtInStyle1"},"1":{"style":"__builtInStyle1"},"2":{"style":"__builtInStyle1"},"3":{"style":"__builtInStyle1"},"4":{"style":"__builtInStyle1"},"5":{"style":"__builtInStyle22"}},"12":{"0":{"style":"__builtInStyle1"},"1":{"style":"__builtInStyle1"},"2":{"style":"__builtInStyle1"},"3":{"style":"__builtInStyle1"},"4":{"style":"__builtInStyle1"},"5":{"style":"__builtInStyle22"}},"13":{"0":{"style":"__builtInStyle1"},"1":{"style":"__builtInStyle1"},"2":{"style":"__builtInStyle1"},"3":{"style":"__builtInStyle1"},"4":{"style":"__builtInStyle1"},"5":{"style":"__builtInStyle22"}},"14":{"0":{"style":"__builtInStyle1"},"1":{"style":"__builtInStyle1"},"2":{"style":"__builtInStyle1"},"3":{"style":"__builtInStyle1"},"4":{"style":"__builtInStyle1"},"5":{"style":"__builtInStyle22"}},"15":{"0":{"style":"__builtInStyle1"},"1":{"style":"__builtInStyle1"},"2":{"style":"__builtInStyle1"},"3":{"style":"__builtInStyle1"},"4":{"style":"__builtInStyle1"},"5":{"style":"__builtInStyle22"}},"16":{"0":{"style":"__builtInStyle1"},"1":{"style":"__builtInStyle1"},"2":{"style":"__builtInStyle1"},"3":{"style":"__builtInStyle1"},"4":{"style":"__builtInStyle1"},"5":{"style":"__builtInStyle22"}},"17":{"0":{"style":"__builtInStyle1"},"1":{"style":"__builtInStyle1"},"2":{"style":"__builtInStyle1"},"3":{"style":"__builtInStyle1"},"4":{"style":"__builtInStyle1"},"5":{"style":"__builtInStyle22"}},"18":{"0":{"style":"__builtInStyle1"},"1":{"style":"__builtInStyle1"},"2":{"style":"__builtInStyle1"},"3":{"style":"__builtInStyle1"},"4":{"style":"__builtInStyle1"},"5":{"style":"__builtInStyle22"}},"19":{"0":{"style":"__builtInStyle1"},"1":{"style":"__builtInStyle1"},"2":{"style":"__builtInStyle1"},"3":{"style":"__builtInStyle1"},"4":{"style":"__builtInStyle1"},"5":{"style":"__builtInStyle22"}},"20":{"0":{"style":"__builtInStyle1"},"1":{"style":"__builtInStyle1"},"2":{"style":"__builtInStyle1"},"3":{"style":"__builtInStyle1"},"4":{"style":"__builtInStyle1"},"5":{"style":"__builtInStyle22"}},"21":{"0":{"style":"__builtInStyle1"},"1":{"style":"__builtInStyle1"},"2":{"style":"__builtInStyle1"},"3":{"style":"__builtInStyle1"},"4":{"style":"__builtInStyle1"},"5":{"style":"__builtInStyle22"}},"22":{"0":{"style":"__builtInStyle1"},"1":{"style":"__builtInStyle1"},"2":{"style":"__builtInStyle1"},"3":{"style":"__builtInStyle1"},"4":{"style":"__builtInStyle1"},"5":{"style":"__builtInStyle22"}},"23":{"0":{"style":"__builtInStyle1"},"1":{"style":"__builtInStyle1"},"2":{"style":"__builtInStyle1"},"3":{"style":"__builtInStyle1"},"4":{"style":"__builtInStyle1"},"5":{"style":"__builtInStyle22"}},"24":{"0":{"style":"__builtInStyle1"},"1":{"style":"__builtInStyle1"},"2":{"style":"__builtInStyle1"},"3":{"style":"__builtInStyle1"},"4":{"style":"__builtInStyle1"},"5":{"style":"__builtInStyle22"}},"25":{"0":{"style":"__builtInStyle1"},"1":{"style":"__builtInStyle1"},"2":{"style":"__builtInStyle1"},"3":{"style":"__builtInStyle1"},"4":{"style":"__builtInStyle1"},"5":{"style":"__builtInStyle22"}},"26":{"0":{"style":"__builtInStyle1"},"1":{"style":"__builtInStyle1"},"2":{"style":"__builtInStyle1"},"3":{"style":"__builtInStyle1"},"4":{"style":"__builtInStyle1"},"5":{"style":"__builtInStyle22"}},"27":{"0":{"style":"__builtInStyle1"},"1":{"style":"__builtInStyle1"},"2":{"style":"__builtInStyle1"},"3":{"style":"__builtInStyle1"},"4":{"style":"__builtInStyle1"},"5":{"style":"__builtInStyle22"}}},"columnDataArray":[{"style":"__builtInStyle3"},{"style":"__builtInStyle3"},{"style":"__builtInStyle2"},{"style":"__builtInStyle4"},{"style":"__builtInStyle5"},{"style":"__builtInStyle23"}],"defaultDataNode":{"style":{"foreColor":"Text 1 0","themeFont":"Body","font":"normal normal 16px DengXian","locked":true,"textIndent":0,"wordWrap":false,"vAlign":2,"hAlign":3,"borderLeft":null,"borderRight":null,"borderTop":null,"borderBottom":null,"borderVertical":null,"borderHorizontal":null,"diagonalUp":null,"diagonalDown":null,"backColor":null}}},"rows":[{"size":21},{"size":21},{"size":21},{"size":28},{"size":21},{"size":21},{"size":21},{"size":21},{"size":21},{"size":21},{"size":21},{"size":21},{"size":21},{"size":21},{"size":21},{"size":21},{"size":21},{"size":21},{"size":21},{"size":21},{"size":21},{"size":21},{"size":21},{"size":21},{"size":21},{"size":21},{"size":21},{"size":21}],"printInfo":{"margin":{"top":75,"bottom":75,"left":70,"right":70,"header":30,"footer":30},"paperSize":{"kind":9},"blackAndWhite":false,"orientation":1,"pageOrder":1,"zoomFactor":1},"tables":[]},"Evaluation Version":{"name":"Evaluation Version","index":0,"allowCellOverflow":true,"defaults":{"colHeaderRowHeight":20,"rowHeaderColWidth":40,"rowHeight":30,"colWidth":60},"rowCount":20,"columnCount":20,"data":{"dataTable":{"1":{"1":{"value":"西安葡萄城 SpreadJS 表格控件提供技术支持。"}},"2":{"1":{"value":""}},"3":{"1":{"value":"本评估版本仅限本地部署。临时部署授权仅限测试用途。"}},"4":{"1":{"value":""}},"5":{"1":{"value":"详情咨询请发送邮件到sales.xa@grapecity.com。"}}}}}}};
$(document).ready(function () {

	spread = new GC.Spread.Sheets.Workbook(document.getElementById('ss'));

	//spread.fromJSON(JSON.parse(test1));

    importData();


    sheet = spread.getActiveSheet();

    sheet.getCell(0,0).value("1");

   /* sheet = spread.getActiveSheet();
    spread.suspendPaint();
    spread.options.tabStripRatio = 0.8;*/

    var datasource = '';


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


function  getEditData(){
    var dirtyCells = spread.getActiveSheet().getDirtyCells();
    var len = dirtyCells.length;

    var stu =    {"id": null,"stuName":null,"stuSex":"共计：","stuAge":0,"stuSchoolName":null,"stuClassName":null};
    var stus = [];

    if (len > 0) {
        for (var i = 0; i < len; i++) {
            var cell = dirtyCells[i];
           // alert("row:" + cell.row + "   " + "col:" + cell.col + "    " + "oldValue:" + cell.oldValue + "    " + "newValue:" + cell.newValue);
            //获得行的第一列 id 值
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


                stus.push(stu);

                console.log(stus);

                $.ajax({
                    type: 'POST',
                    url: '/user/updateStu',
                    dataType: 'json',
                    async: false,
                    data:  {"stuList":JSON.stringify(stus)  },   //将Json对象序列化成Json字符串，toJSON()需要引用jquery.json.min.js,
                    success: function (data) {
                       console.log(data)
                    }
                });

              //  var colCount = sheet.getColumnCount();


            }

        }
    }
}




//单元格绑定
function   cellBbding(){

    sheet = spread.getActiveSheet();

    $.ajax({
        type: 'POST',
        url: '/user/getStudents',
        dataType: 'json',
        async: false,
        success: function (data) {
            datasource =data;
        }
    });


    var person =  {"id":2,"stuName":"李四","stuSex":0,"stuAge":21,"stuSchoolName":"站桩","stuClassName":"一年假"};
    var person2 =  {"id":3,"stuName":"李四1111","stuSex":1,"stuAge":2111,"stuSchoolName":"站桩11111","stuClassName":"一年假111111"};

    sheet.setBindingPath(1 , 0, 'id');
    sheet.setBindingPath(1, 1, 'stuName');
    sheet.setBindingPath(1, 2, 'stuSex');
    sheet.setBindingPath(1, 3, 'stuAge');

    sheet.setBindingPath(1, 4, 'stuSchoolName');
    sheet.setBindingPath(1, 5, 'stuClassName');


    var source = new GC.Spread.Sheets.Bindings.CellBindingSource(person);
    sheet.setDataSource(source);


    sheet.setBindingPath(2 , 0, 'id');
    sheet.setBindingPath(2, 1, 'stuName');
    sheet.setBindingPath(2, 2, 'stuSex');
    sheet.setBindingPath(2, 3, 'stuAge');

    sheet.setBindingPath(2, 4, 'stuSchoolName');
    sheet.setBindingPath(2, 5, 'stuClassName');


    var source1 = new GC.Spread.Sheets.Bindings.CellBindingSource(person2);
    sheet.setDataSource(source1);


/*    for(var i =0; i<datasource.length;i++ ){
        var m =i+1;
        sheet.setBindingPath(m , 0, 'name');
        sheet.setBindingPath(m, 1, 'age');
        sheet.setBindingPath(m, 2, 'sex');
        sheet.setBindingPath(m, 3, 'address.postcode');
        sheet.setDataSource(source);
    }*/





}







//表格绑定
function   tableBbding(){

    sheet = spread.getActiveSheet();

    $.ajax({
        type: 'POST',
        url: '/user/getStudents',
        dataType: 'json',
        async: false,
        success: function (data) {
            datasource =data;
        }
    });

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
       // tableColumn.name(labels[index]);
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
    //sheet.deleteRows(1, 2)





    //sheet.deleteRows(datasource.length+1, 1)



}


//表单绑定
function sheeBbding(){

    sheet = spread.getActiveSheet();

    console.log(sheet.name());
    spread.suspendPaint();
    spread.options.tabStripRatio = 0.8;

/*    datasource =  [
        {"id":1,"stuName":"1212","stuSex":1,"stuAge":20, "stuSchoolName":"sawew","stuClassName":"qweqeqweqw"},
        {"id":2,"stuName":"李四","stuSex":0,"stuAge":21,"stuSchoolName":"站桩","stuClassName":"一年假"},
        {"id":3,"stuName":"王五","stuSex":0,"stuAge":23,"stuSchoolName":"李家","stuClassName":"二年级"},
        {"id":4,"stuName":"赵柳","stuSex":0,"stuAge":24,"stuSchoolName":"得到","stuClassName":"三年级"}];*/

    $.ajax({
        type: 'POST',
        url: '/user/getStudents',
        dataType: 'json',
        async: false,
        success: function (data) {
            datasource =data;
        }
    });

    var count =    {"id": null,"stuName":null,"stuSex":"共计：","stuAge":0,"stuSchoolName":null,"stuClassName":null};
    datasource.push(count);

    //console.log(JSON.stringify(datasource));

    sheet.autoGenerateColumns = true;
    sheet.setDataSource(datasource);


    spread.resumePaint();

    //加载的带有公式的模板，也会造成单元格dirty。 同样使用sheet.clearPendingChanges()方法清除掉dirty

    sheet.clearPendingChanges();

}

function importData(){
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

            sheeBbding();
        }, function (e) {
            // process error
            alert(e.errorMessage);
        }, {});
      }

    };

    xhr.send();
    console.timeEnd("测试后台导入excel");
}


function importData1(){
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

                 tableBbding();
               // cellBbding();
            }, function (e) {
                // process error
                alert(e.errorMessage);
            }, {});
        }

    };

    xhr.send();
    console.timeEnd("测试后台导入excel");
}




