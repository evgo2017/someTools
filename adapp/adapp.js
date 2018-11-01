const puppeteer = require('puppeteer')
const fs = require('fs')
const download = require('download')

// 读取数据
var filePath = "./data.json"
var data = JSON.parse(fs.readFileSync(filePath))
// 
async function run() {
	// 初始化
	const brower = await puppeteer.launch()
	const page = await brower.newPage()
	// 对每个软件地址操作
	for(let i in data) {
		// 转到软件地址
		await page.goto(data[i].url)
		// 我没有添加更新版本的功能，这或许是另一个插件该做的事情
		// 获取下载地址
		var url = await page.$eval('.normal_download', el => el.href)
		// 下载软件,存放在 dist 文件夹内
		download(url).pipe(fs.createWriteStream('./dist/'+data[i].name+'.exe'))
	}
	await brower.close()
}
//运行
run()

