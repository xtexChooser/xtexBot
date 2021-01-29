package com.xtex.bot.command

import com.xtex.bot.bot.XBot
import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.contact.Member
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.MessageChain

interface Command {

    void execute(XBot bot, Group group, Member sender, List<String> args, MessageChain message)

    String usage()

    default void at(Group group, Member sender, String text) {
        group.sendMessage new At(sender.id) + text
    }

    default void syntaxError(Group group, Member sender) {
        at group, sender, 'Command syntax error.'
    }

}