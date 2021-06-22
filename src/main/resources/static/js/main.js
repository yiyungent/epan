let winWidth,winHeight;
let mouse;
let pathDom;
let box;

window.onload = function() {

    // 获取用户信息
    util.httpGet("/api/user/info", function (res) {
        if (res.code == 1) {
            // 已登录
            util.setCookie("user", JSON.stringify(res.data), 7);
            $("#js-btn-exit").html(util.format($("#js-btn-exit").html(), res.data.userName));

        } else if(res.code == -1) {
            // 未登录
            window.location.href = "/login";
        }
    })

    if (typeof(Worker) !== "undefined") {
        console.log("浏览器支持HTML5");
    } else {
        alert("浏览器不支持HTML5");
    }

    box = document.getElementById('content');
    box.addEventListener("dragover",function (e) { // 拖来拖去
        e.preventDefault();
    },false);
    box.addEventListener("dragleave",function(e) { // 拖离
        e.preventDefault();
    },false);
    box.addEventListener("drop",dropEvent,false); // 扔

    //获取可视区宽度
    winWidth = function(){ return document.documentElement.clientWidth || document.body.clientWidth;};
    //获取可视区高度
    winHeight = function (){ return document.documentElement.clientHeight || document.body.clientHeight;};

    mouse = document.getElementById('mouse-right');
    pathDom = document.getElementById('path');
    pathDom.value = root;
    document.addEventListener('click', function() {
        mouse.style.display = 'none';
    });
    //右键菜单
    document.oncontextmenu = function(event) {
        var event = event || window.event;
        mouse.style.display = 'block';
        var l, t;
        l = event.clientX;
        t = event.clientY;
        if( l >= (winWidth() - mouse.offsetWidth) ) {
            l  = winWidth() - mouse.offsetWidth;
        }
        if(t > winHeight() - mouse.offsetHeight  ) {
            t = winHeight() - mouse.offsetHeight;
        }
        mouse.style.left = l + 'px';
        mouse.style.top = t + 'px';
        return false;
    };
    refresh()


};

function refresh() {
    fileGet(pathDom.value)
}

function newFolder() {
    let tmp = `<div class="item" >
                <div class="item-check"><input type="checkbox" name="checkbox" class="checkbox"" ></div>
                <div class="item-name"><input id="folder-name" type="text" class="form-control" style="max-width: 400px" autofocus="autofocus" onblur="mkdir()" onkeydown="if(event.keyCode==13){mkdir()}"/></div>
                <div class="item-size">-</div>
                <div class="item-date">-</div>
            </div>`;
    let list = document.getElementById('item-list');
    let str = tmp + list.innerHTML;
    list.innerHTML = str;
}

function mkdir() {
    let input = document.getElementById('folder-name');
    if (input.value === ""){
        showTips("文件名不能为空",2000);
        refresh();
        return;
    }

    let reqUrl = httpAddr+"/api/file/mkdir?path="+pathDom.value+"/"+input.value;
    util.httpGet(reqUrl,function (res) {
        refresh();
        if (res.code == 1) {
            showTips("成功",2000);
        }else {
            showTips(res.message,2000)
        }
    },function (e) {
        console.log(e);
        refresh();
        showTips("网络错误！",2000)
    })
}

function fileDelete() {
    let chk_list = document.getElementsByName("checkbox");
    for(let i=0;i<chk_list.length;i++){
        if (chk_list[i].checked) {
            let reqUrl = util.format("{0}/api/file/delete?path={1}&fileName={2}",httpAddr,pathDom.value,chk_list[i].value);
            console.log(reqUrl);
            util.httpGet(reqUrl, function (res) {
                if (res.code == 1) {
                    showTips("成功", 2000);
                    refresh();
                } else {
                    showTips(res.message, 2000)
                }
            }, function (e) {
                console.log(e);
                showTips("网络错误！", 2000)
            })
        }
    }
}

function fileDownload() {
    let chk_list = document.getElementsByName("checkbox");
    for(let i= 0;i<chk_list.length;i++){
        if (chk_list[i].checked) {
            let reqUrl = util.format("{0}/api/file/download?path={1}&fileName={2}",httpAddr,pathDom.value,chk_list[i].value);
            let tt = chk_list[i].getAttribute("data");
            if (tt === "file") {
                // 创建隐藏的可下载链接
                let eleLink = document.createElement('a');
                eleLink.style.display = 'none';
                eleLink.href = reqUrl;
                // 触发点击
                document.body.appendChild(eleLink);
                eleLink.click();
                // 然后移除
                document.body.removeChild(eleLink);
            }
        }
    }
}

function fileAction(action) {
    let chk_list = document.getElementsByName("checkbox");
    for(let i= 0;i<chk_list.length;i++){
        if (chk_list[i].checked) {
            let reqUrl = util.format("{0}action?action={1}&filename={2}&src={3}&dest={4}",httpAddr,action,chk_list[i].value,pathDom.value,root);
            let tt = chk_list[i].getAttribute("data");
            if (tt === "file") {
                util.httpGet(reqUrl, function (res) {
                    if (res.ok) {
                        showTips("成功", 2000);
                        refresh();
                    } else {
                        showTips(res.message, 2000)
                    }
                }, function (e) {
                    console.log(e);
                    showTips("网络错误！", 2000)
                })
            }
        }
    }
}

function select(id) {
    if (id !== "") {
        checkAllState(false);
        let name = "checkbox-" + id;
        document.getElementById(name).checked = true;
    }
}

function makeNav(path) {
    console.log("makeNav: " + path)
    let tmp = `<li class="breadcrumb-item"><a href="#" onclick="fileGet('{1}')">{0}</a></li>`;
    let tmpActive = `<li class="breadcrumb-item active" aria-current="page">{0}</li>`;
    let dom = document.getElementById('path-nav');
    dom.innerHTML = "";
    let array = path.split("/");
    let str = "<li class=\"breadcrumb-item\"><a href=\"#\" onclick=\"fileGet('/')\">根目录</a></li>";
    // let str = "";
    let nowPath = "";
    for (let i = 0;i < array.length;i++){
        if (i === 0) {
            nowPath +=  array[i]
        }else {
            nowPath += "/" + array[i]
        }
        if (i === array.length - 1){
            str += util.format(tmpActive,array[i])
        }else {
            str += util.format(tmp,array[i],nowPath)
        }
    }
    dom.innerHTML = str;
}

function fileGet(path) {
    let tmp = `<div class="item">
                <div class="item-check"><input type="checkbox" id="checkbox-{0}" value="{0}" data="{4}" name="checkbox" class="checkbox" onclick="checkState(this.checked)" ></div>
                <div class="item-name" onmousedown="select('{0}')">{1}</div>
                <div class="item-size" onmousedown="select('{0}')">{2}</div>
                <div class="item-date" onmousedown="select('{0}')">{3}</div>
            </div>`;
    pathDom.value = path;
    let list = document.getElementById('item-list');
    list.innerHTML = "";
    // TODO: 文件列表
    let reqUrl = httpAddr+"/api/file/list?path="+path;
    makeNav(path);

    let disk_p = document.getElementById('disk-progress');
    let disk_info = document.getElementById('disk-info');

    console.log(reqUrl,path);
    util.httpGet(reqUrl,function (res) {
        if (res.code==1) {
            let str = "";
            for (let key in res.data.list){
                let data = res.data.list[key];
                console.log(data)
                if (data.fileType==1){
                    let folder = util.format(`<a href="#" onclick="fileGet('{1}')" style="text-decoration: none"><i class="fa fa-folder-o"></i><span>&nbsp;&nbsp;{0}</span></a>`,data.fileName,pathDom.value+"/"+data.fileName);
                    str += util.format(tmp,data.fileName,folder,"-",data.createTime,"dir")
                }else {
                    let file = util.format(`<i class="fa fa-file-o"></i><span onclick="openFile('{1}','{0}')">&nbsp;&nbsp;{0}</span>`,data.fileName,pathDom.value);
                    str += util.format(tmp,data.fileName,file,util.b2string(data.fileSize,1024),data.createTime,"file")
                }
            }
            list.innerHTML = str;
            // 获取用户信息
            let user = JSON.parse(util.getCookie("user"));
            // 文件容量
            disk_info.innerHTML = util.format("{0}/{1}&nbsp;",util.b2string(user.usedDiskSize,1024),util.b2string(user.diskSize,1024))
            // TODO: 文件容量 百分比
            let disk_used_p = Number(user.usedDiskSize)/Number(user.diskSize)*100;
            console.log(disk_used_p);
            disk_p.innerHTML = util.format("{0}%",disk_used_p.toFixed(1));
            disk_p.style.width = util.format("{0}%",disk_used_p.toFixed(1));
        } else {
            showTips("请求错误", 1000)
        }
    },function (e) {
        console.log(11,e);
        showTips("网络错误！",1000)
    });
}

function openFile(path, fileName) {
    let reqUrl = util.format("{0}/api/file/download?path={1}&fileName={2}",httpAddr,path,fileName);
    let suffixIndex = fileName.lastIndexOf(".");
    let fileSuffix = fileName.substr(suffixIndex+1);
    console.log("打开文件: " + reqUrl);
    if (["mp3"].indexOf(fileSuffix)>=0) {
        console.log("音频播放: " + reqUrl);
        // 音频 播放
        // ap.list.add([{
        //     name: fileName,
        //     // artist: 'artist',
        //     url: reqUrl,
        //     // cover: 'cover.jpg',
        //     fixed: true,
        //     // mini: true
        // }]);
    }
}

/************* 文件上传 ***************/

function dropEvent(e) {
    e.preventDefault(); //取消默认浏览器拖拽效果
    let fileList = e.dataTransfer.files; //获取文件对象
    //检测是否是拖拽文件到页面的操作
    if (fileList.length === 0) {
        return false;
    }
    console.log(fileList.length);

    for (let i = 0;i < fileList.length;i++) {
        fileRead(pathDom.value, fileList[i]);
    }
}

function fileRead(path,file) {
    let fileReader = new FileReader();
    fileReader.readAsDataURL(file.slice(0, 4));
    fileReader.onload = function (ev) {
        updateFile(path,file)
    };
    fileReader.onerror = function (ev) {
        console.log(file.name);
        showTips("文件夹"+ file.name,1000)
    };
}

function inputFile() {
    let fileList = document.getElementById('upfile').files;
    if (fileList.length === 0) {
        return
    }
    for (let i = 0;i < fileList.length;i++) {
        updateFile(pathDom.value, fileList[i]);
    }
    document.getElementById('upfile').value = '';
}

let updateInfos = new Map();

function updateInfoClose() {
    updateInfos.clear();
    document.getElementById('update-info').style.display = "none";
    document.getElementById('update-list').innerHTML = "";
}

function updateFile(path,file) {
    let tmp =`<div class="progress">
            <div class="progress-info">
            <div class="progress-name">{0}</div>
            <div class="progress-size">{1}</div>
            <div class="progress-path">{2}</div>
            <div id="{3}" class="progress-width">0%</div>
            </div>
            <div id="{4}" class="progress-bar progress-bar-striped" style="width: 0%;"  aria-valuemin="0" aria-valuemax="100"></div>
        </div>`;

    let totalSize = file.size,
        filename = file.name,
        fileAbs = path+"/"+filename,
        total = Math.ceil(file.size / sliceSize),
        existBlob = new Map(),
        loadedMap = new Map();


    console.log(filename,totalSize,total,fileAbs);
    function addProgress() {
        document.getElementById('update-info').style.display = "block";
        if (!updateInfos.has(fileAbs)) {
            let list = document.getElementById('update-list');
            let pSize = util.b2string(totalSize,1024);
            let index = path.lastIndexOf("\/");
            let pPath = path.substring(index+1,path.length);
            let pWidth = util.format("{0}-width",fileAbs);
            let pBar = util.format("{0}-bar",fileAbs);
            list.innerHTML += util.format(tmp, filename,pSize,pPath,pWidth,pBar);
            updateInfos.set(fileAbs, fileAbs)
        }
    }

    function setProgress(isNeed,isFinished) {
        if (!updateInfos.has(fileAbs)) {
            return
        }

        let loaded = totalSize;
        if (isNeed){
            loaded = 0;
            loadedMap.forEach(function (v,k,map) {
                loaded += v
            });
            if (loaded > totalSize){
                loaded = totalSize * 0.999;
            }
        }

        let progress = (loaded / totalSize * 100).toFixed(1) + '%';
        let pWidth = util.format("{0}-width",fileAbs);
        let pBar = util.format("{0}-bar",fileAbs);
        if (loaded === totalSize){
            document.getElementById(pBar).style.width = "0%";
            if (isFinished) {
                refresh();
            }
            if (isNeed){
                document.getElementById(pWidth).innerHTML = `<i class="fa fa-check-circle" style="color: #00CC00"></i>`
            }else {
                document.getElementById(pWidth).innerHTML = `<i class="fa fa-check-circle" style="color: #00CC00">秒传</i>`
            }
        }else {
            document.getElementById(pWidth).innerHTML = progress;
            document.getElementById(pBar).style.width = progress;
        }
    }

    function md5File(file,callback) {
        let spark = new SparkMD5(),
            fileReader = new FileReader();
        fileReader.readAsBinaryString(file);
        fileReader.onload = function (ev) {
            spark.appendBinary(ev.target.result);
            callback(spark.end());
        };
    }

    function checkFile(md5,callback) {
        // TODO: 文件上传检查
        let reqUrl = httpAddr +"/api/file/uploadCheck";
        let cmd = {path:path,fileName:filename,total:total,fileSignKey:md5,size:totalSize};
        util.httpPost(reqUrl,JSON.stringify(cmd), function (res) {
            if (res.code > 0){
                if (res.code == 2){
                    callback(true,res.upload)
                }else {
                    callback(false)
                }
            }else {
                showTips(res.message, 2000);
            }
        })
    }

    md5File(file,function (md5) {
        checkFile(md5,function (need,exist) {
            addProgress();
            if (need){
                if (exist) {
                    console.log(exist);
                    for (let key in exist){
                        existBlob.set(key, key);
                        loadedMap.set(key,sliceSize);
                    }
                }

                let reqUrl = httpAddr +"/api/file/upload";
                let current = 0;
                while (current < total) {
                    current++;
                    if (!existBlob.has(current.toString())){
                        let start = sliceSize * (current-1);
                        let end = sliceSize * current;
                        end = end > totalSize ? totalSize : end;
                        let blob = file.slice(start, end);

                        let xhr =  new XMLHttpRequest();
                        let fd = new FormData();
                        fd.append('path',path);
                        fd.append('file', blob);
                        fd.append('fileName', filename);
                        fd.append('current', current.toString());
                        fd.append('fileSignKey', md5);

                        xhr.open("post",reqUrl,true);
                        xhr.onreadystatechange = function () {
                            if (xhr.readyState === 4) {
                                if (xhr.status === 200) {
                                    loadedMap.set(fd.get("current"),blob.size);
                                    setProgress(true,true);
                                } else {
                                    showTips("网络错误！",1000)
                                }
                            }
                        };
                        xhr.upload.onprogress = function(e) {
                            loadedMap.set(fd.get("current"),e.loaded);
                            setProgress(true,false);
                        };
                        xhr.send(fd)
                    }
                }

            }else {
                setProgress(false,true);
            }
        })
    });

}
