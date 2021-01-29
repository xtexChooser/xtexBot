package com.xtex.bot.internal;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.utils.BotConfiguration;

public class BotFactory {

    public static Bot newBot(long qq, String password, BotConfiguration configuration) {
        return net.mamoe.mirai.BotFactory.INSTANCE.newBot(qq, password, configuration);
    }

}
