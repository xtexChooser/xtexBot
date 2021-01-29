package com.xtex.bot.util

class FileChecker {

    static void ensureFile(File file) {
        ensureDir file.parentFile
        if(!file.exists()) file.createNewFile()
    }

    static void ensureDir(File file) {
        if(!file.exists()) file.mkdirs()
    }

}
