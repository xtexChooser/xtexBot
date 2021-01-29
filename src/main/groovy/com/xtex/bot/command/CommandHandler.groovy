package com.xtex.bot.command

import com.xtex.bot.bot.XBot
import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.contact.Member
import net.mamoe.mirai.message.data.MessageChain

class CommandHandler {

    static void handle(XBot bot, Group group, Member sender, MessageChain message) {
        if (message.contentToString().startsWith '/') {
            def args = new ArrayList<String>(Arrays.asList(message.contentToString().substring(1).split(" ")))
            CommandSet.ROOT.execute bot, group, sender, args, message
        }
    }

}
