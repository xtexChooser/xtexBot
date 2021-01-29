package com.xtex.bot.json.config

class GroupConfiguration {

    InviteConfiguration invite

    static class InviteConfiguration {

        Operate operate
        String message = 'Bot do not allow any join group invite'

        enum Operate {
            ACCEPT, DENY, NONE
        }

    }

}
