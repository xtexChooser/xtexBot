package com.xtex.bot.bot

import net.mamoe.mirai.utils.BotConfiguration
import net.mamoe.mirai.utils.MiraiLogger
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class XBotConfiguration extends BotConfiguration {

    XBotConfiguration(XBot bot) {
        this.fileBasedDeviceInfo new File(bot.data, 'device.json').absolutePath
        this.botLoggerSupplier = (b) -> new MLogger(LoggerFactory.getLogger('Mirai:Bot'))
        this.networkLoggerSupplier = (b) -> new MLogger(LoggerFactory.getLogger('Mirai:Net'))
        this.workingDir = bot.data
    }

    static class MLogger implements MiraiLogger {

        final Logger logger

        MLogger(Logger logger) {
            this.logger = logger
        }

        @Override
        MiraiLogger getFollower() {
            null
        }

        @Override
        void setFollower(@Nullable MiraiLogger miraiLogger) {
        }

        @Override
        String getIdentity() {
            'XBotLogger'
        }

        @Override
        boolean isEnabled() {
            true
        }

        @Override
        void debug(@Nullable String s) {
            logger.debug s
        }

        @Override
        void debug(@Nullable String s, @Nullable Throwable throwable) {
            logger.debug s, throwable
        }

        @Override
        void error(@Nullable String s) {
            logger.error s
        }

        @Override
        void error(@Nullable String s, @Nullable Throwable throwable) {
            logger.error s, throwable
        }

        @Override
        void info(@Nullable String s) {
            logger.info s
        }

        @Override
        void info(@Nullable String s, @Nullable Throwable throwable) {
            logger.info s, throwable
        }

        @Override
        <T extends MiraiLogger> T plus(@NotNull T t) {
            null
        }

        @Override
        void verbose(@Nullable String s) {
            logger.trace s
        }

        @Override
        void verbose(@Nullable String s, @Nullable Throwable throwable) {
            logger.trace s, throwable
        }

        @Override
        void warning(@Nullable String s) {
            logger.warn s
        }

        @Override
        void warning(@Nullable String s, @Nullable Throwable throwable) {
            logger.warn s, throwable
        }

    }

}
