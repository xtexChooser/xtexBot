package com.xtex.bot.command.wiki

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.xtex.bot.bot.XBot
import com.xtex.bot.command.Command

import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.contact.Member
import net.mamoe.mirai.message.data.MessageChain
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import org.jetbrains.annotations.NotNull
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class WikiSearchCommand implements Command {

    static final Logger logger = LoggerFactory.getLogger 'Wiki:Search'

    @Override
    void execute(XBot bot, Group group, Member sender, List<String> args, MessageChain message) {
        if (args.size() != 1) {
            syntaxError group, sender
            return
        }
        def page = args[0]
        bot.httpClient.newCall(new Request.Builder().get()
                .url("${bot.configuration.wiki}?action=opensearch&format=json&utf8=1&search=${page}&limit=1&redirects=return")
                .build()).enqueue(new Callback() {

            @Override
            void onFailure(@NotNull Call call, @NotNull IOException e) {
                logger.error "Failed to get page $page", e
                at group, sender, 'Request failure.'
            }

            @Override
            void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                def json = new Gson().fromJson response.body().string(), JsonArray
                def source = json[0].asString
                def to = json[1].asJsonArray[0].asString
                def redirected = (source != to)
                def url = json[3].asJsonArray[0].asString
                at group, sender, redirected ? "(重定向)$source:$to\n\n$url" : "$source\n\n$url"
            }

        })
    }

    @Override
    String usage() {
        '/wiki [TEXT]'
    }

}
