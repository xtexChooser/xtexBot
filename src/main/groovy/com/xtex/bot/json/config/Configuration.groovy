package com.xtex.bot.json.config

import com.google.gson.Gson
import org.apache.commons.io.FileUtils

import java.nio.charset.StandardCharsets

class Configuration {

    AccountConfiguration account
    AdminConfiguration admin
    GroupConfiguration group
    FriendConfiguration friend

    static Configuration load() {
        new Gson().fromJson(FileUtils.readFileToString(new File('config.json'), StandardCharsets.UTF_8), Configuration)
    }

}
