package com.xtex.bot.command

import com.xtex.bot.bot.XBot
import com.xtex.bot.command.admin.StopCommand
import com.xtex.bot.command.base.HelpCommand
import com.xtex.bot.command.wiki.WikiSearchCommand
import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.contact.Member
import net.mamoe.mirai.message.data.MessageChain

class CommandSet extends HashMap<String, Command> implements Command {

    public static final CommandSet ROOT = new CommandSet('/', [
            'help': new HelpCommand(),
            'stop': new StopCommand(),
            'wiki': new WikiSearchCommand()
    ])

    final String path

    CommandSet(String path, Map<String, Command> map) {
        super(map)
        this.path = path
    }

    @Override
    void execute(XBot bot, Group group, Member sender, List<String> args, MessageChain message) {
        if (args.size() < 1) {
            syntaxError group, sender
        } else if (!containsKey(args[0])) {
            at group, sender, 'Command not found.'
        } else {
            def command = get args[0]
            args.remove 0
            command.execute bot, group, sender, args, message
        }
    }

    @Override
    String usage() {
        "${path} [${keySet().join '|'}]"
    }

}
