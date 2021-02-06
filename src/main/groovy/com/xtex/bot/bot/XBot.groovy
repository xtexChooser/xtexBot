package com.xtex.bot.bot

import com.xtex.bot.bot.admin.Administrator
import com.xtex.bot.command.CommandHandler
import com.xtex.bot.internal.BotFactory
import com.xtex.bot.json.config.Configuration
import com.xtex.bot.json.config.GroupConfiguration
import com.xtex.bot.util.FileChecker
import com.xtex.bot.util.exception.operation.LoginException
import net.mamoe.mirai.Bot
import net.mamoe.mirai.event.events.BotInvitedJoinGroupRequestEvent
import net.mamoe.mirai.event.events.BotJoinGroupEvent
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.event.events.NewFriendRequestEvent
import okhttp3.OkHttpClient
import org.jetbrains.annotations.Nullable
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static com.xtex.bot.util.ToStringUtil.toString

class XBot implements Runnable {

    static final Logger logger = LoggerFactory.getLogger 'Bot'
    static final Logger loggerGroupMessage = LoggerFactory.getLogger 'Message:Group'
    final Configuration configuration
    final File data
    final OkHttpClient httpClient = new OkHttpClient()
    @Nullable
    Bot bot = null
    @Nullable
    Administrator admin = null

    XBot(Configuration configuration) {
        this.configuration = configuration
        data = new File('data')
        FileChecker.ensureDir data
    }

    @SuppressWarnings('GroovyAssignabilityCheck')
    @Override
    void run() {
        login()
        logger.info 'Bot running!'
        bot.eventChannel.subscribeAlways GroupMessageEvent, this::handleGroupMessage
        bot.eventChannel.subscribeAlways BotInvitedJoinGroupRequestEvent, this::handleGroupInvite
        bot.eventChannel.subscribeAlways BotJoinGroupEvent.Invite, this::handleInvitedGroup
        bot.eventChannel.subscribeAlways NewFriendRequestEvent, this::handleNewFriend
        while (bot.online)
            Thread.sleep(3000)
    }

    void handleGroupMessage(GroupMessageEvent event) {
        loggerGroupMessage.trace '{}: {}: {}', toString(event.group),
                toString(event.sender), event.message.contentToString()
        CommandHandler.handle this, event.group, event.sender, event.message
    }

    void handleGroupInvite(BotInvitedJoinGroupRequestEvent event) {
        logger.info '{}[{}] invites bot to join to group {}',
                toString(event.invitor), event.invitorId, event.groupId
        admin.sendNotification "$event.invitorId 邀请Bot进群 $event.groupId"
        switch (configuration.group.invite.operate) {
            case GroupConfiguration.InviteConfiguration.Operate.ACCEPT:
                event.accept()
                break
            case GroupConfiguration.InviteConfiguration.Operate.DENY:
                event.ignore()
                if (event.invitor != null)
                    event.invitor.sendMessage(configuration.group.invite.message)
                break
        }
    }

    void handleInvitedGroup(BotJoinGroupEvent.Invite event) {
        //noinspection SpellCheckingInspection
        logger.info '{} invites bot to join to group {} and the server is auto accepted by bot\'s qq setting',
                toString(event.invitor), toString(event.group)
        admin.sendNotification "${toString(event.invitor)} 邀请Bot进群 ${toString(event.group)} 并且QQ服务器已自动同意"
        if (configuration.group.invite.operate == GroupConfiguration.InviteConfiguration.Operate.DENY){
                event.group.sendMessage(configuration.group.invite.message)
                if (!event.group.quit()) {
                    logger.error 'Failed to quit auto joined group {}', toString(event.group)
                    admin.sendNotification "未能退出服务器自动同意的群聊 ${toString(event.group)}"
                }
        }
    }

    void handleNewFriend(NewFriendRequestEvent event) {
        logger.info '{}[{}] want to be friend with bot from {}: {}', event.fromId, event.fromNick,
                event.fromGroupId == 0 ? 'other method' : "group ${toString(event.fromGroup)}", event.message
        switch (configuration.friend.add) {
            case GroupConfiguration.InviteConfiguration.Operate.ACCEPT:
                event.accept()
                break
            case GroupConfiguration.InviteConfiguration.Operate.DENY:
                event.reject false
                break
        }
    }

    void login() throws LoginException {
        if (bot != null)
            throw new LoginException('Now is logged in.')
        try {
            logger.info 'Logging in...'
            bot = BotFactory.newBot configuration.account.qq.id, configuration.account.qq.password, new XBotConfiguration(this)
            bot.login()
            logger.info 'Logged in!'
            admin = new Administrator(this, configuration.admin)
            if (bot.getFriend(admin.configuration.qq) == null) {
                bot.close()
                throw new LoginException("The bot are not the friend of the administrator.")
            }
        } catch (Exception e) {
            throw new LoginException(e)
        }
    }

}
