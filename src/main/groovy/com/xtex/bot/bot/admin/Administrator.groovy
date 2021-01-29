package com.xtex.bot.bot.admin

import com.xtex.bot.bot.XBot
import com.xtex.bot.json.config.AdminConfiguration
import net.mamoe.mirai.contact.Friend
import net.mamoe.mirai.contact.Member

class Administrator {

    final XBot bot
    final AdminConfiguration configuration
    final Friend admin

    Administrator(XBot bot, AdminConfiguration configuration) {
        this.bot = bot
        this.configuration = configuration
        this.admin = bot.bot.getFriend(configuration.qq)
    }

    void sendNotification(String text) {
        admin.sendMessage "[BOT] $text"
    }

    boolean isAdministrator(Member member) {
        isAdministrator(member.id)
    }

    boolean isAdministrator(long id) {
        id == admin.id
    }

}
