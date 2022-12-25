package com.mcp.osc.infrastructure.util.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : KG
 * description:
 * create date: 4:01 PM 2022/9/21
 * modified by:
 */

public class RegexJudger {
    public static boolean match(String pattern, String destStr) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(destStr);

        return m.matches();
    }
}
