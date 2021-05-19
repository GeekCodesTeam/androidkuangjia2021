package com.geek.libutils.app;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Title: emoji特殊处理
 * @author: houjie
 * @date： 2021-01-26 11:01
 */
public class EmojiStringUtils {
    /**
     * @Title: 判断是否存在特殊字符串
     * @author: houjie
     * @date： 2021-01-26 11:01
     */
    public static boolean hasEmoji(String content) {
        Pattern pattern = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]");
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return true;
        }
        return false;
    }

    /**
     * @Title: 替换字符串中的emoji字符
     * @author: houjie
     * @date： 2021-01-26 11:01
     */
    public static String replaceEmoji(String str) {
        if (!hasEmoji(str)) {
            return str;
        } else {
            str = str.replaceAll("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", " ");
            return str;
        }
    }
}