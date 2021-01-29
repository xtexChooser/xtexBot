package com.xtex.bot.main

import com.xtex.bot.bot.XBot
import com.xtex.bot.json.config.Configuration
import org.slf4j.LoggerFactory

class Main {

    static void main(String[] args) {
        def bot = new XBot(Configuration.load())
        def logger = LoggerFactory.getLogger 'Main'
        logger.info 'Run bot...'
        bot.run()
        logger.info 'Bot stopped.'
    }

}
