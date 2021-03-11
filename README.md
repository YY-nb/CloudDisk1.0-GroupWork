网盘接口信息
前端需要的接口如下
api: {
post: post,//上传文件        
    get: get,//获取文件列表
    checkName: get,//检查昵称是否被占用     
    login: post,//登录                                      
    validCode: post,//邮箱验证                    
    signUp: post,//注册                                 
    delete: post,//删除文件或文件夹
    move: post,//移动文件或文件夹到目标位置
    rename: post,//重命名文件或文件夹  
    changeMail:post,//修改绑定邮箱   
    createFolder:post//创建文件夹   
pendingList: get,//待审核列表,
pass: post,//通过审核
}

后端提供的接口（请把接口url以及必需的参数列出来）
api：
● /user/login  
用户登录接口
参数列表：
email                 ->邮箱
password           ->密码
返回信息：
{
message: 'success',
    data: {
email: '',
userName: '',
token: ''
    }
}
{
message: 'failed',
error: '具体信息'
}
● /admin/login
管理员登录
参数列表：
name
password
返回信息：


● /user/checkName
验证昵称重复
参数列表：
userName    

● /user/getCode
验证邮箱以及发送验证码
参数列表：
email
{
message: 'success',
}
{
message: 'failed',
error: ''
}
● /user/register
注册
参数列表：
userName
email
password
avatar    ->头像 （可选）(文件类型）
authCode    ->验证码
{
message: 'success',
data: {
email: '',
userName: '',
avatar: '', //头像url
token: ''
  }
}
{
message: 'failed',
error: ''
}
● /user/update
修改用户信息 （可修改昵称，邮箱，会先检查改后的是否重复）
参数列表：
userName   
email新邮箱
authCode验证码
用户旧名 oldUserName
{
message: 'success',
data: {
email: '',
userName: '',
token: ''
  }
}
{
message: 'failed',
error: ''
}

● /user/uploadFile
上传文件
参数列表：
myFile     (后端会用MultipartFile类来处理前端传的文件， MultipartFile类的对象名我命名成了myFile)
path          路径
用户名 userName
{
message: 'success'
}
{
message: 'failed',
error: ''
}

● /user/getFileList
获取文件列表
参数：
path    路径
用户名 userName
返回信息：
{
message: 'success',
  "data": {
"items": {
date: '',
      	creator: '',
      	lastEditedBy: '',
      	name: '',
      	path: '',//硬盘位置如/home/cloudDisk/www/user/someone/folder1/subFolder,如果有空格请用引号包住整个文件夹名字
      		size: '',//文件夹不用返回这个；不用格式化，直接给我一串数字，这个应该可以通过xxx.length()获取
      		type: '',//文件夹:dir  ,文件:file
      	url: '',//只有图片需要返回这个，这个再想想怎么生成临时的url
    }
  }
}
{
message: 'failed',
  error: ''
}

● /user/createFolder
创建文件夹
参数：
path     路径
name    新建的文件夹名字
用户名 userName
{
message: 'success'
}
{
message: 'failed',
error: ''
}

● 删除文件或文件夹
files    路径（有多个，都是这名字，一次性删除多个文件或文件夹）
最好是
{
{path, type}
{path,type}
}         包在一起的形式，每个路径后跟一个type区分文件/文件夹
(审核者  admin:xxxx)
{
message: 'success'
}
{
message: 'failed',
error: ''
}

● 移动文件或文件夹
files    路径（有多个，都是这名字，一次性删除多个文件或文件夹）
{
message: 'success'
}
{
message: 'failed',
error: ''
}

● /user/updateName    重命名文件或文件夹
请求参数：
path     路径
newName    新名称
{
message: 'success'
}
{
message: 'failed',
error: ''
}
● 待审核列表
用户上传文件后，后端要把文件路径或者用户文件表里的ID之类的东西存入一个存放待审核的数据表
参数和返回信息同获取文件列表
● 通过审核
参数同删除，从待审核表中删除目标就可

