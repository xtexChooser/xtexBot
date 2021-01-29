# What is this?
xtexBot为xtex个人制作的一个QQ Robot，基于Mirai（https://github.com/mamoe/mirai）

# Features

# How to use?
- 按照下面的配置文档编写JSON格式的配置`config.json`
- 运行

# Configuration

## account

### qq

id: long: QQ号

password: string: QQ密码

## admin

qq: long: 管理员的QQ

## group

### invite

operate: string: 被邀请进群时的操作, ACCEPT: 接受, DENY: 拒绝, NONE: 不作处理
message: string: (Optional) 拒绝进群时的消息

## friend

add: string: 添加新朋友的操作, 与group.invite.operate相同
