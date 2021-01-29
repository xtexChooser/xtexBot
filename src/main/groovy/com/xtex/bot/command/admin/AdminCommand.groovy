package com.xtex.bot.command.admin

import com.xtex.bot.bot.XBot
import com.xtex.bot.command.Command
import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.contact.Member
import net.mamoe.mirai.message.data.MessageChain

interface AdminCommand extends Command {

    default void execute(XBot bot, Group group, Member sender, List<String> args, MessageChain message) {
        if(!bot.admin.isAdministrator(sender)) {
            at group, sender, 'You are not the administrator.'
            return
        }
        realExecute bot, group, sender, args, message
    }

    void realExecute(XBot bot, Group group, Member sender, List<String> args, MessageChain message)

}
