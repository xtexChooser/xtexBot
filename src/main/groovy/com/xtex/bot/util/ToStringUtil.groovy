package com.xtex.bot.util

import net.mamoe.mirai.contact.Friend
import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.contact.Member

class ToStringUtil {

    static String toString(Group group) {
        if(group == null) return 'null'
        return "${group.id}(${group.name})"
    }

    static String toString(Member member) {
        if(member == null) return 'null'
        return "${member.id}(${member.nick})"
    }

    static String toString(Friend friend) {
        if(friend == null) return 'null'
        return "${friend.id}(${friend.nick})"
    }

}
