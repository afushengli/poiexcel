
(async function () {
    try {
        //初始化全局变量
        var longTimer;
        var heartTimer;
        var autoTimer;
        var init = false;
        var resetLongTime = 10 * 2;

        var longTime = resetLongTime;
        var autoSaveTime = 3000;
        var heartTime = 3000;



        //1、获取文件名
        var searchURL = window.location.search;
        searchURL = decodeURI(searchURL.substring(1, searchURL.length));
        fileName = searchURL.split("=")[1];

        //2、初始化spreadjs
        var spread = new GC.Spread.Sheets.Workbook(document.getElementById("ss"));
        spreadNS = GC.Spread.Sheets;
        var excelIo = new GC.Spread.Excel.IO();
        protectedSpreadjs(true)

        //3、加载spread数据
        await initSpreadData()
        //4、开启表单保护
        protectedSpreadjs(true)
        init = true;

    } catch (error) {

    }


    //点击锁定并修改按钮
    $('#edit').click(async function () {
        if (!init) {
            return alert('还未初始化完成')
        }
        try {
            //请求后台锁定文件
            await editFile()
            //加载spread数据查看是否可以操作
            await initSpreadData();
            //开启长时间不操作检测
            longInterval();
            //开启心跳检测
            heartInterval()
            //开启自动保存
            autoInterval()
            //解除表单保护
            protectedSpreadjs(false)
        } catch (error) {
            console.log(error)
        }
    })

    //点击保存并解锁
    $('#save').click(function () {
        if (!init) {
            return alert('还未初始化完成')
        }
        saveAndUnLock()
    })

    //没有任何操作了
    document.body.onclick = function () {
        longTime = resetLongTime;
    }
    document.body.onkeydown = function () {
        longTime = resetLongTime;
    }

    //页面关闭
    window.onbeforeunload = function (e) {
        saveAndUnLock()
    };








    //表单保护
    function protectedSpreadjs(isProtected) {
        var sheet = spread.getActiveSheet();
        sheet.options.isProtected = isProtected;
    }


    //加载spread数据
    function initSpreadData() {

        return new Promise((resolve, reject) => {

            var xhr = new XMLHttpRequest();
            xhr.open('GET', "/user/downFileFromBack?fileName=" + fileName + "", true); //同步请求
            xhr.responseType = 'blob';
            xhr.onload = async function (e) {
                if (this.status == 200) {
                    // get binary data as a response
                    var blob = this.response;
                    var result = await blobToJson(blob)
                    if (typeof result === 'string' && result.indexOf('未登录') != -1) {
                        redirtLogin()
                        return;
                    }
                    // convert Excel to JSON
                    excelIo.open(blob, function (json) {
                        var workbookObj = json;
                        spread.fromJSON(workbookObj);
                        var sheet = spread.getActiveSheet();
                        //设置表单保护属性
                        var option = {
                            allowSelectLockedCells: true,
                            allowSelectUnlockedCells: true,
                            allowFilter: true,
                            allowResizeRows: true,
                            allowResizeColumns: true
                        };
                        sheet.options.protectionOptions = option;
                        resolve()
                    }, function (e) {
                        // process error
                        alert(e.errorMessage);
                        reject()
                    }, {});
                }
            };
            xhr.onerror = function (e) {
                alert("加载数据失败:" + e.message)
                reject()
            }
            xhr.send();
        })

    }





    //1.长时间不保存
    function longInterval() {
        clearInterval(longTimer);
        longTimer = null;
        longTimer = setInterval(function () {
            longTime--;
            if (longTime <= 0) {
                saveAndUnLock();
            }
        }, 1000)
    }

    //2.心跳检测
    function heartInterval() {
        clearInterval(heartTimer);
        heartTimer = null;
        heartTimer = setInterval(function () {
            var sheet = spread.getActiveSheet();
            var sheetName = sheet.name();
            $.ajax({
                type: "POST",
                url: "/opreate/sendHearteat",
                data: { sheetName: sheetName, fileName: fileName },
                success: function (data) {
                    if (typeof data === 'string' && data.indexOf('未登录') != -1) {
                        redirtLogin()
                        return;
                    }
                    if (data.status == 0) {
                        console.log('心跳检测:' + data.msg)
                    } else {
                        console.log('心跳检测:' + data.msg)
                    }
                }
            });
        }, heartTime)
    }

    //3.自动保存
    function autoInterval() {
        clearInterval(autoTimer);
        autoTimer = null;
        autoTimer = setInterval(function () {
            saveFile()
        }, autoSaveTime)
    }


    // 锁定文件
    function editFile() {
        return new Promise((resolve, reject) => {
            var sheet = spread.getActiveSheet();
            var sheetName = sheet.name();
            $.ajax({
                type: "POST",
                url: "/opreate/editFile",
                async: false,
                data: { sheetName: sheetName, fileName: fileName },
                success: function (data) {
                    if (typeof data === 'string' && data.indexOf('未登录') != -1) {
                        redirtLogin()
                        return;
                    }
                    if (data.status == 0) {
                        alert('锁定并修改成功')
                        resolve()
                    } else {
                        var aa = data.data;
                        alert(aa + "正在修改");
                        reject()
                    }
                },
                error: function (ex) {
                    alert("锁定失败:" + ex.message);
                }
            });
        })

    }



    //保存并锁定
    function saveAndUnLock() {
        var json = spread.toJSON();
        excelIo.save(json, function (blob) {
            var fd = new FormData(document.forms.namedItem("myform"));
            fd.append("file", blob, fileName);  //fileName  是文件名   https://www.jianshu.com/p/282eda9d1d0a
            $.ajax({
                url: "/opreate/saveExport",
                type: "POST",
                contentType: false,
                processData: false,
                data: fd,
                success: function (data) {
                    if (typeof data === 'string' && data.indexOf('未登录') != -1) {
                        redirtLogin()
                        return;
                    }
                    if (data.status === 0) {
                        resetPage()
                        alert('保存并锁定成功')
                    } else {
                        resetPage()
                        alert("保存并锁定失败:" + data.msg)
                    }

                },
                error: function (ex) {
                    resetPage()
                    alert("保存并锁定失败:" + ex.message);
                }
            });
        }, function (e) {
            resetPage()
            alert("保存并锁定失败:" + e.message);
        });
    }

    //只保存数据
    function saveFile() {
        var json = spread.toJSON();
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
                    if (typeof data === 'string' && data.indexOf('未登录') != -1) {
                        redirtLogin()
                        return;
                    }
                    if (data.status == 0) {
                        console.log("自动保存成功:" + data.msg)
                    } else {
                        alert("自动保存失败1:" + data.msg)
                        resetPage()
                    }
                },
                error: function (ex) {
                    alert("自动保存失败2:" + ex.message);
                    resetPage()
                }
            });
        }, function (e) {
            alert("自动保存失败3:" + e.message);
            resetPage()
        });
    }






    //页面初始化状态
    function resetPage() {
        clearInterval(longTimer);
        longTimer = null;
        clearInterval(heartTimer);
        heartTimer = null;
        clearInterval(autoTimer);
        autoTimer = null;
        longTime = resetLongTime;
        protectedSpreadjs(true)
    }

    function redirtLogin() {
        location.href = "index.html"
    }

    function blobToJson(blob) {
        return new Promise((resolve, reject) => {
            var reader = new FileReader();

            reader.onload = function (event) {
                var content = reader.result;//内容就在这里
                resolve(content)
            };
            reader.readAsText(blob);
        })

    }

})()