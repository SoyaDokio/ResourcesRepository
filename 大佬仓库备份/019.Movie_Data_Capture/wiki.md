# **严禁**在墙内的社交平台宣传此项目


# 快速开始:
### 1. 下载[可执行程序](https://github.com/yoshiko2/Movie_Data_Capture/releases/latest)或者源码运行(高级用户/开发者)<br>

### 2. [**代理设置**](#手动代理设置)(墙内用户)

> 设置全局代理或墙外可忽略上述代理设置
### 3. 把软件拉到和电影的同一目录  

### 4. 影片命名规则：
* 普通影片：`ipx-xxx.mp4`
* DMM/FANZA：`kawd00969.mp4`
* FC2：`FC2-xxxxx.mp4`


* 带中文字幕：`ipx-xxx-C.mp4`
* 分集：`ipx-xxx-CD1.mp4 ipx-xxx-CD2.mp4`
* 流出：`ipx-xxx-流出.mp4`
* 无码破解：`ipx-xxx-hack.mp4`

### 5. 运行软件等待完成
> * Windows用户如要在```cmd,powershell,windows-terminal```中运行，请```cd /d```到程序所在目录再运行<br>
> * Linux / MacOS / BSD 用户在终端中运行，请```cd```到程序所在目录再运行<br>

###  6. 可在 Kodi, Emby, Jellyfin, Plex 中导入输出文件夹<br>

------
* emby/jellyfin头像上传可使用[gfriends](https://github.com/xinxin8816/gfriends)
* PLEX要安装插件：XBMC nfo Movies Importer

详细请看以下完整文档

# 配置config.ini
## 运行模式
```
[common]
main_mode=1
```
1为普通模式  
2为整理模式：仅根据女优把电影命名为番号并分类到女优名称的文件夹下  
3为更新模式：更新现有NFO和图片  


### 设置成功输出目录和失败输出目录
```
success_output_folder=output
failed_output_folder=failed
```


### 软链接
方便PT下载完既想刮削又想继续上传的仓鼠党同志
```
[common]
soft_link=0
```
0为关闭  
1为开启软链接模式  
2移走文件后，在原来位置增加一个可追溯的软链接，指向文件新位置 (实验性功能)  


### 移动失败刮削文件至失败输出文件夹
```
[common]
failed_move=1
```
1为开启  
0为关闭


### 自动退出
```
[common]
auto_exit=0
```
1为开启  
0为关闭


### 影片标签翻译至简体中文
```
[common]
transalte_to_sc = 1
```
1为开启  
0为关闭

### 演员性别抓取
```
[common]
;actor_gender value: female(♀) or male(♂) or both(♀ ♂) or all(♀ ♂ ⚧) 
actor_gender=female
```  
* 目前仅支持javdb  
female 女  
male 男  
both 男+女  
all 男+女+跨性别

### 日志
```
[common]
; 跳过最近(默认:30)天新修改过的.NFO，可避免整理模式(main_mode=3)和软连接(soft_link=0)时
; 反复刮削靠前的视频文件，0为处理所有视频文件
nfo_skip_days=30
; 处理完多少个视频文件后停止，0为处理所有视频文件
stop_counter=0
; 以上两个参数配合使用可以以多次少量的方式刮削或整理数千个文件而不触发翻译或元数据站封禁
```  
程序启动参数'-o 日志目录'则可生成日志，检查几周内日志可找出失败文件手工处理。
日志默认开启，默认日志目录以及关闭日志的方法，可通过运行程序时带参数``--help``来查看  

如果确实需要关闭日志功能，命令行显式指定参数--log-dir=即可，等号后留空，或者填入/dev/null(Linux/MAC OSX)或NUL(Windows)。
#### 默认日志目录
* Windows  
win+R打开运行输入``%USERPROFILE%\.avlogs``回车  
* MacOS  
``$HOME/.avlogs 或 ~/.avlogs``  

#### 关闭默认日志
**Windows**
按下`Win+R`热键出现运行对话框，输入`cmd`按下回车；
在打开的命令行窗口（有些人叫它DOS窗口）里输入`rmdir /S .avlogs`按下回车（注意avlogs前面有个点）；
想一两秒钟是不是确定要删光日志，确定后按下`Y`回车；
最后输入`type nul >> .avlogs`生成同名空文件，搞定收工。

如何恢复默认日志功能：打开命令行窗口后输入`del /Q .avlogs`即可。

注意要按`Win+R`运行的cmd窗口，初始目录才确定是用户目录，如果是通过其它方式启动的命令行窗口，例如从文件管理器地址栏输入cmd，可能需要先切换当前目录到用户目录：`CD /D %USERPROFILE%`然后再进行以上操作。

最后是无废话版本
先`Win+R`，然后复制粘贴以下内容并按下回车，看到`Done.`说明成功。
`cmd /C cd /D %USERPROFILE% && rmdir /S /Q .avlogs && type nul >> .avlogs && if exist .avlogs echo Done. && pause`

***nix**
直接无废话版，打开终端窗口输入命令行：
`rm -rf ~/.avlogs && touch ~/.avlogs`

### 刮削失败文件开关
```
[common]
ignore_failed_list=0
```

### 下载已刮削影片缺失信息
```
[common]
download_only_missing_images=1
```
默认1
功能为只下载缺少的图片，再次刮削，或者模式3，目的只是为了补齐.nfo元数据信息或者前次未下载的剧照等缺少信息时，能较大的提升速度，并且降低数据源的网络请求次数，节省时间。
- 例如首次刮削没打开翻译，以便生成日语title的路径而非质量较差的翻译结果。再次模式3原地刮削时打开了翻译，目的是更新.nfo内的元数据title和outline为翻译文字，启用此选项后，没缺少图片时不会再次下载图片。
- 再如整理的旧数据没有剧照，用模式3补上剧照下载，那么在封面图片没有缺少时就不会下载，只会更新.nfo和补齐剧照。

值为1时，或存在命令行参数-i时，扫描电影文件夹时将忽略faild_folder中的failed_list.txt失败文件清单，可用于重新尝试刮削失败视频，与5.0.1版本以前的版本行为一致（即：始终从头开始，而不是略过失败文件）。

---

## 网络设置 
### 手动代理设置
```
[proxy]
switch=0
```
代理开关 1开0关  
* 在网关设置代理或**全局代理**的用户直接关闭  
* 开启时下文proxy需输入本地代理地址和端口  

```
[proxy]
type=http
```
代理类型 http或socks5

```
[proxy]
proxy=127.0.0.1:1081  
```

打开```config.ini```,在```[proxy]```下的```proxy```行设置本地代理地址和端口，支持Shadowxxxx/X,V2XXX本地代理端口  
素人系列抓取建议使用日本代理  
**本地代理软件开全局模式的用户同上**  
**如果遇到proxy字样错误，关闭手动代理(switch设置0)，并开启代理软件全局模式，或者重启电脑，代理软件，网卡**  


### 连接超时重试设置
```
[proxy]
timeout=10  
```
10为超时重试时间 单位：秒


### 连接重试次数设置
```
[proxy]
retry=3  
```
3即为重试次数

### 本地代理证书（可选高级）
```
[proxy]
cacert_file=
```
代理证书绝对或相对路径

### 单影片多线程刮削
```
[proxy]
multi_threading=1
```
单影片刮削时多个源并发刮削，并非多影片并发刮削  

---

## 检查更新开关
```
[update]  
update_check=1  
```
0为关闭，1为开启，不建议关闭  

---

## 刮削网站优先级
```
[priority]
website=javbus,javdb,fanza,xcity,mgstage,fc2,avsox,jav321
```
用```,```英文逗号分开网站，刮削顺序从左往右  

---


## 排除指定字符和目录
```
[escape]  
literals=\  
folders=failed,output
```

```literals=``` 标题指定字符删除，例如```iterals=\()```，则删除标题中```\()```字符  
```folders=``` 指定目录，例如```folders=failed,output```，多目录刮削时跳过failed,output  

---


## 调试模式
```
[debug_mode]
switch=1  
```

如要开启调试模式，请手动输入以上代码到```config.ini```中，开启后可在抓取中显示影片元数据  

---

## 设置自定义目录和影片重命名规则，标题最长字符数设置
```
[Name_Rule]
location_rule=actor+'/'+number
naming_rule=number+'-'+title
max_title_len = 50
```
已有默认配置

### 命名参数
```
title = 片名
original_title = 原生片名（未翻译片名）
actor = 演员
studio = 公司
director = 导演
release = 发售日
year = 发行年份
number = 番号
cover = 封面链接
tag = 类型
outline = 简介
runtime = 时长
series = 系列
```

上面的参数以下都称之为**变量**

自定义规则方法：有两种元素，变量和字符，无论是任何一种元素之间连接必须要用加号 **+** ，比如：```'naming_rule=['+number+']-'+title```，其中冒号 ' ' 内的文字是字符，没有冒号包含的文字是变量，元素之间连接必须要用加号 **+** 

### locaton_rule
该为影片路径规则
目录结构规则：默认 ```location_rule=actor+'/'+number```

**不推荐修改时在这里添加 title**，有时 title 过长，因为 Windows API 问题，抓取数据时新建文件夹容易出错。

### naming_rule
该为媒体库内标题的命名规则规则，NFO文件内标题<title>命名规则
影片命名规则：默认 ```naming_rule=number+'-'+title```

**在 Emby, Kodi等本地媒体库显示的标题，不影响目录结构下影片文件的命名**，依旧是 番号+后缀。  

---

## 更新开关
```
[update]
update_check=1
```  
---

1为开，0为关

## 机器翻译
```
[transalte]
switch=0
```  
1为开，0为关
```
[transalte]
engine=google-free
```  
翻译引擎 可选```google-free```和```azure```
```
[transalte]
key=
```  
azure翻译密钥，仅供翻译引擎选择azure时使用
```
[transalte]
delay=1
```
翻译延迟（秒），强烈建议设置为1及以上，以免触发反爬
```
[transalte]
values=title,outline
```
要翻译的元数据类型
```
[transalte]
service_site=translate.google.cn
```
谷歌翻译站点，请参考[这里](https://github.com/yoshiko2/Movie_Data_Capture/pull/599)

---


## 预告片
```
[trailer]
switch=0
```  
---

## 无码后缀
```
[uncensored]
uncensored_prefix=S2M,BT,LAF,SMD
```  
---

## 文件类型
```
[media]
media_type=.mp4,.avi,.rmvb,.wmv,.mov,.mkv,.flv,.ts,.webm,.iso
```
影片后缀
```
sub_type=.smi,.srt,.idx,.sub,.sup,.psb,.ssa,.ass,.txt,.usf,.xss,.ssf,.rt,.lrc,.sbv,.vtt,.ttml
```
字幕后缀

---

## 水印
```
[watermark]
switch=1
water=2
```
左上 0, 右上 1, 右下 2， 左下 3  

---

## 剧照
```
[extrafanart]
switch=0
extrafanart_folder=extrafanart
parallel_download=5
```
* switch 剧照下载开关
* extrafanart_folder 剧照存储文件夹  
* parallel_download=5 多线程下载队列数  0则关闭多线程 过多则容易导致封IP

## 剧情简介
```
[storyline]
site=1:avno1,4:airavwiki
censored_site=2:airav,5:xcity,6:amazon
uncensored_site=3:58avgo
run_mode=1
show_result=0
```
website为javbus javdb avsox xcity carib时  
site censored_site uncensored_site 为获取剧情简介信息的可选数据源站点列表。   
列表内站点同时并发查询，优先级从左到右，靠左站点没数据才会采用后面站点获得的。  
其中airav avno1 58avgo是中文剧情简介，区别是airav只能查有码，avno1有码无码都能查，58avgo只能查无码或者流出破解马赛克的影片(此功能没使用)。    
xcity和amazon是日语的，由于amazon商城没有番号信息，选中对应DVD的准确率仅99.6%。如果三个列表全部为空则不查询，    
设置成不查询可大幅提高刮削速度。  

* `run_mode`:运行模式：0:顺序执行(最慢) 1:线程池(默认值) 2:进程池(启动开销比线程池大，并发站点越多越快)
* `show_result`:剧情简介调试信息 0关闭 1简略 2详细(详细部分不记入日志)，剧情简介失效时可打开2查看原因

---

## 简/繁中文输出
```
[cc_convert]
mode=1
vars=actor,director,label,outline,series,studio,tag,title
```
* mode 繁简转换开关 0:不转换 1:繁转简 2:简转繁
* vars 需转换的元数据
---

## javdb域名
```
[javdb]
sites=33,34
```
* 设置最新的javdb域名后缀
* javdb需登录的抓取请阅读[这里](#javdb-cookies操作)

# 运行参数
## 覆盖配置文件
以下运行参数均为可选参数
- `--help`输出的帮助信息末尾，以及mdc每次启动后的提示信息中，都将显示目前正在使用的配置文件完全路径，以避免无任何信息反馈的黑箱操作。
- `-m 数字`或`--main-mode 数字`，覆盖配置文件中[common]小节中的main_mode。
- `-d 数字`或`--nfo-skip-days 数字`，覆盖配置文件中[common]小节中的nfo_skip_days。最近N天内被修改过.nfo的电影将被跳过，为0则不跳过任何文件，配合`-c`增量处理时使用，可以分多次处理完几千个文件。
- `-c 数字`或`--stop-counter 数字`，覆盖配置文件中[common]小节中的stop_counter。用来限制本次运行处理电影的个数，-c1只处理一个电影文件即退出。
- `-i`或`--ignore-failed-list`，覆盖配置文件中[common]小节中的ignore_failed_list。使用此参数会忽略failed_list.txt列表，被记录为失败后的文件也将再此重试。
- `-g`或`--debug`，覆盖配置文件中[debug_mode]小节中的switch，使用此参数时等同于设置了[debug_mode]小节中的switch=1，有时用参数比修改ini文件更加方便快捷。
- `-z`或`--zero-operation`，零操作功能，使用此参数时，程序不会进行任何真实的刮削操作，而是“假装”执行一遍整个削刮任务，可以用此参数预览一下接下来将处理的全部电影是否是自己需要的，及被识别出的number是否正确，结合`-q`参数可以缩小查询范围。

## 手动指定番号
`-n`或`--number`   
如果遇到番号比较奇葩的影片，同时存在于可刮削的网站，可用输入以下
```
Movie_Data_Capture xxxxxxxx.mp4 -n xxxxxxxx
```
单次运行仅能处理一部影片  
## 刮削其他目录的影片
```-p```  
```
./Movie_Data_Capture -p /mnt/cache/
```
可使用相对/绝对路径
## 文件名及路径查询过滤
```-q```  
```Movie_Data_Capture -q``` '波多野' 只刮削或整理文件完全路径中包含波多野三个字的视频文件。  
```Movie_Data_Capture -q '\.iso$|\.ts$'``` 后缀是.iso或者.ts的视频文件才加入处理清单。  

# 多集影片处理
**建议使用视频合并合并为一个视频文件**
可以把多集电影按照集数后缀命名为类似  
```ssni-xxx-cd1.mp4```  
```ssni-xxx-cd2.mp4```  
```abp-xxx-CD1.mp4```  
上述规则，只要含有```-CDn./-cdn.```类似命名规则，即可使用分集功能  

# Docker部署
前往[VergilGao](https://github.com/VergilGao/docker-avdc)的教程

## 中文字幕处理

运行 ```Movie_Data_capture.py/.exe```

当文件名包含:
中文，字幕，-c., -C., 处理元数据时会加上**中文字幕**标签  

# 异常处理（重要）

请确保软件是完整地！确保ini文件内容是和下载提供ini文件内容的一致的！

* 关于软件打开就闪退  

可以打开cmd命令提示符，把 ```Movie_Data_capture.py/.exe```拖进cmd窗口回车运行，查看错误，出现的错误信息**依据以下条目解决**

* 关于 ```Updata_check``` 和 ```JSON``` 相关的错误  

跳转 [网络设置](#网络设置)
  
  
* 关于字幕文件移动功能  

字幕文件前缀必须与影片文件前缀一致，才可以使用该功能

* 关于```FileNotFoundError: [WinError 3] 系统找不到指定的路径。: 'output''```   

在软件所在文件夹下新建 output 文件夹，可能是你没有把软件拉到和电影的同一目录

* 关于连接拒绝的错误  

请设置好[代理](#针对某些地区的代理设置)

* 关于Nonetype,xpath报错  

同上  

* 关于番号提取失败或者异常  
**可以提取元数据的网站:avsox, javbus, javdb, dmm(fanza), fc2, jav321, mgstage(素人)**

目前已经完善了番号提取机制，功能较为强大，各大网站的影片请用以下规则命名（dmm(fanza)下载的影片除外）
```
COSQ-004.mp4
```
对于dmm(fanza)上下好的电影，请使用影片cid命名，示例如下
```
kawd00969.mp4
```

条件：文件名中间要有下划线或者减号"_","-"，没有多余的内容只有番号为最佳，可以让软件更好获取元数据
对于多影片重命名，可以用 [ReNamer](http://www.den4b.com/products/renamer) 来批量重命名

* 关于PIL/image.py
暂时无解，可能是网络问题或者pillow模块打包问题，你可以用源码运行（要安装好第一步的模块）  

# 配置文件位置
config.ini默认与程序同一目录  
对于多位置刮削，config.ini必须在当前路径太不方便了，先改为依次尝试如下路径，以找到的第一个为准，后续不再尝试。
```
./config.ini
~/mdc.ini
~/.mdc.ini
~/.mdc/config.ini
~/.config/mdc/config.ini
```
这里的～路径符号在*nix中代表$HOME目录，在Windows中代表%USERPROFILE%目录。
各人可按自己使用喜好和隐私要求，选择配置文件放置位置。

# javdb-cookies操作
### cookies提取简单操作
* [下载](https://github.com/yoshiko2/db_cookies/releases/tag/0.1)后放在mdc主程序同一目录下，在chrome手动登录javdb网站后（一定要勾选保持七天登录状态)  
* 浏览器登入的javdb域名需包含config.ini中[javdb]sites字段的后缀（如https://***db33.com)  
* 运行程序即可提取cookies
### cookies提取高级操作
支持通过Chrome浏览器访问javdb网站，并且进行用户登录后，获取仅用户登录时才能访问的页面信息，示例番号：FC2-735670 https://javdb网站/v/vO8Mn
在EXE执行文件的当前目录(与config.ini相同目录)建立cookies文件`javdb数字后缀.json`，内容如下（以下各字段的数值部分数据为虚构，请按自己账户的真实登录数据填写）：
```json
{
    "over18":"1",
    "redirect_to":"%2Fv%2FvO8Mn",
    "remember_me_token":"cbJdeaFpbHMiOnsibWVzc2FnZSI6IklrNVJjbTAzZFVSRVEpUVFhOVU0yNXhJZz09IiwiZXhwIjoiMjAyMS0wNS0xNVQxMzoyODoxNy4wMDBaIiwicHVyIjoiY29va2llLnJlbWVtYmVylX3Rva2VuIn19--a713d411b635889bff3fe3",
    "_jdb_session":"asddefqfwfwwrfdsdaAmqKj1%2FvOrDQP4b7h%2BvGp7brvIShi2Y%2FHBUr%2BklApk06TfhBOK3g5gRImZzoi49GINH%2FK49o3W%2FX64ugBiUAcudN9b27Mg6Ohu%2Bx9Z7A4bbqmqCt7XR%2jMcdDG5czoYHJCPIPZQFU28Gd7Awc2jc5FM5CoIgSRyaYDy9ulTO7DlavxoNL%2F6OFEL%2FyaA6XUYTB2Gs1kpPiUDqwi854mo5%2FrNxMhTeBK%2BjXciazMtN5KlE5JIOfiWAjNrnx7SV3Hj%2FqPNxRxXFQyEwHr5TZa0Vk1%2FjbwWQ0wcIFfh%2FMLwwqKydAh%2FLndc%2Bmdv3e%2FJ%2BiL2--xhqYnMyVRlxJaN--u7n7tZtPd4kIaEbg%3D%3D",
    "locale":"zh",
    "__cfduid":"dee27116d98c2342a5cabc1fe0e7c2f3c91620479752",
    "theme":"auto"
}
```
通过浏览器插件(CookieBro或EdittThisCookie)或者直接在地址栏网站链接信息处都可以复制或者导出cookie内容（注意：需要先在Chrome浏览器中访问 https://javdb网站/login 勾选保持七天登录状态，并输入用户名、密码、图形验证码成功进行页面用户登录后，cookie内容才是有效登录数据），并填写到以上json文件的相应字段中。  

与config.ini类似，为javdb数字后缀.json提供路径搜索次序，按以下次序查找文件，以找到的第一个文件为准，不再向下尝试。  
```
./javdb数字后缀.json
~/javdb数字后缀.json
~/.mdc/javdb数字后缀.json
~/.local/share/mdc/javdb数字后缀.json
```
* 例：javdb33.json
* 关于更多cookies登录相关点[这里](https://github.com/yoshiko2/Movie_Data_Capture/pull/483)

# 拖动法
针对格式比较奇葩的番号  
影片放在和程序同一目录下，拖动至```Movie_Data_Capture.exe```，即可完成刮削和整理

* 软件会自动把元数据获取成功的电影移动到 output 文件夹中，根据演员分类，失败的电影移动到failed文件夹中。

* 把output文件夹导入到 Emby, Kodi中，等待元数据刷新，完成

# NAS的食用方式
Windows，MacOS ``映射网络驱动器``放置程序后使用  
Linux 挂载NFS后使用  

# 使用源码运行
* 高级用户及开发者使用

如果运行**源码**版，运行前请安装**Python环境**和安装以下**模块**  

在终端中输入以下代码来安装模块

* Windows
```
pip install -r requirements.txt
```
* Linux / *BSD / MacOS
```
pip3 install -r requirements.txt
```

# 讨论群
https://t.me/joinchat/Sp_Ec-XNbUlLj4oV

# 写在后面
* **严禁**在中国大陆内的社交平台宣传此项目，在**相应圈子**讨论或者推荐即可  
## 赞助
* 维护开源项目是件困难的事情  
### 欢迎投喂  投喂就是更新的力量
### 学业百忙中抽空维护项目，作者穷逼学生，多少无所谓，建议49RMB起考虑（一天的饭钱）
![donate.png](https://i.postimg.cc/qBmD1v9p/donate.png)
### 接活 业务范围：
✅ 爬虫/程序   
✅ 业界领先的专业JAV挂削/管理方案提供商  
✅ 定制化MDC  
### 联系方式：
* [作者Telegram](https://t.me/yoshiko2)
* email : yoshiko2.dev@gmail.com