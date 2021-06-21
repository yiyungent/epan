root = "";
httpAddr = "http://localhost:8080/";
sliceSize = 1024*1024*500 // 500MB, TODO: 由于服务端暂时未做 切片接收合并处理，所以这里将一个切片定义这么大