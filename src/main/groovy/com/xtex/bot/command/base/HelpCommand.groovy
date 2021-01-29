package com.xtex.bot.command.base

import com.xtex.bot.bot.XBot
import com.xtex.bot.command.Command
import com.xtex.bot.command.CommandSet
import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.contact.Member
import net.mamoe.mirai.message.data.MessageChain

class HelpCommand implements Command {

    @Override
    void execute(XBot bot, Group group, Member sender, List<String> args, MessageChain message) {
        def target = CommandSet.ROOT
        for(String arg : args) {
            target = target.get arg
            if(target == null) {
                at group, sender, "Command not found at '${arg}'"
                return
            }
        }
        group.sendMessage target.usage()
    }

    @Override
    String usage() {
        '/help <command>\n查询命令帮助'
    }

}
