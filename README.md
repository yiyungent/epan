
<p align="center">
<!-- <img src="docs/images/logo.png" alt="epan"> -->
</p>
<h1 align="center">epan</h1>

> 基于 SpringBoot 的在线云盘, 支持对接 Hadoop 存储源

[![repo size](https://img.shields.io/github/repo-size/yiyungent/epan.svg?style=flat)]()
[![LICENSE](https://img.shields.io/github/license/yiyungent/epan.svg?style=flat)](https://github.com/yiyungent/epan/blob/master/LICENSE)

<!-- [English](README_en.md) -->

## 介绍

一个简单的云盘

> SpringBoot + MyBatis + Maven + MySQL  
> 文件存储于 Hadoop HDFS 中

## 技术

- 秒传
  - 从文件中计算特征值，查询数据库，若已存在，则更新数据库指向目标文件，服务端相同文件只存一份。

- 多文件上传
  - 多个文件加入队列，逐个发送到服务端

- 删除
  - 删除文件夹：递归删除文件夹下所有文件，仅当物理文件没有被	引用时，才被删除，其余只会更新VirtualFile表


## 截图

![](/docs/images/Snipaste_2021-06-29_22-52-39.jpg)
![](/docs/images/Snipaste_2021-06-29_22-56-54.jpg)


## Build

> Build

```bash
mvn complie
```

> 打包 jar

```bash
mvn package
```

生成的 jar 位于 `target/epan-0.0.1-SNAPSHOT.jar`

> 运行

```bash
java -jar target/epan-0.0.1-SNAPSHOT.jar
```

## Docker

> Docker 快速部署

```bash
docker run -d -p 8080:8080 --name epan-container yiyungent/epan
```

> Docker build

```bash
docker build -t epan -f Dockerfile .
```

## Hadoop 搭建

- Docker 下快速搭建开发测试环境: [hadoop-docker](https://github.com/yiyungent/hadoop-docker)

## 环境

- 运行环境: Oracle JDK 1.8+
- 开发环境: IntelliJ IDEA 2020.2.1

## 相关项目

- [hadoop-docker](https://github.com/yiyungent/hadoop-docker)

## 鸣谢

- 前端设计来自 <a href="https://github.com/yddeng/filecloud" target="_blank">filecloud</a>，感谢作者 yddeng 的贡献

## Donate

epan is an MIT licensed open source project and completely free to use. However, the amount of effort needed to maintain and develop new features for the project is not sustainable without proper financial backing.

We accept donations through these channels:

- <a href="https://afdian.net/@yiyun" target="_blank">爱发电</a>

## Author

**epan** © [yiyun](https://github.com/yiyungent), Released under the [MIT](./LICENSE) License.<br>
Authored and maintained by yiyun with help from contributors ([list](https://github.com/yiyungent/epan/contributors)).

> GitHub [@yiyungent](https://github.com/yiyungent)