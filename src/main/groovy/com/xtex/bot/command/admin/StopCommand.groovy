package com.xtex.bot.command.admin

import com.xtex.bot.bot.XBot
import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.contact.Member
import net.mamoe.mirai.message.data.MessageChain

class StopCommand implements AdminCommand {

    @Override
    void realExecute(XBot bot, Group group, Member sender, List<String> args, MessageChain message) {
        bot.bot.close()
    }

    @Override
    String usage() {
        '/stop'
    }

}
