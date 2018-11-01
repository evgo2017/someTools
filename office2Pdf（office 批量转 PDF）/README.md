# office2Pdf

作者：evgo，公众号（随风前行），Github（evenIfAlsoGo）

## 程序功能：

office 文件转为 PDF 文件。

将目标文件夹内所有 office 文件都生成一份对应的 PDF 文件，不包括子文件夹。

>  Excel 文件会根据内部的工作表数量生成多份文件（每次导出只能导出当前工作表的 PDF）

## 文件说明：

### office2Pdf.py

用 Python 语言实现功能的源码。

在 Python 环境下运行即可。

推荐使用。

### office2Pdf.exe

使用 pyinstaller 将 office2Pdf.py 打包为 exe 文件。

windows 下直接打开即可运行程序，不需要安装 Python 环境。

兼容性暂时无法测试，若打开有疑问可联系我尝试解决。

较推荐使用。

### office2Pdf.java

用 Java 语言实现功能的源码。

只是实现了基础功能，不够完善。有一定的对比学习意义。

因为 Java 安装运行较为麻烦，分享不够便利，于是换成 Python 语言实现。

不推荐。

## 使用方法：

输入目标文件夹的绝对路径（若为当前程序所在的文件夹，直接回车）即可，程序会自动转换文件夹内所有 office 文件，运行过程会打印在窗口上。

运行示例图：

![py运行示例](.\example.png)

（Python 程序运行示例图）

## 最后

若有更新信息，会在此文档进行说明。



使用过程中出现疑问，请联系我。

个人公众号：随风前行

邮箱：evenIfAlsoGo@gmail.com