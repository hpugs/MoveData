package com.hpugs.movedata.modle;

import com.alibaba.excel.util.StringUtils;
import org.jsoup.Jsoup;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpuNameUtil {

    private static Pattern spuNumPattern = Pattern.compile("^\\d+\\.");

    private static Pattern charPattern = Pattern.compile("[`~!@#$^&*()+\\-_%=|{}':;',\\[\\]<>/?~！@#￥……&*（）——+|{}【】‘；：”“\"’。，、》？《」「]");

    private static Pattern HTML_PATTERN = Pattern.compile("<[^>]*>");

    public static void main(String[] args) {
        String text = "空山新雨后，\n天气晚来秋。";
        System.out.print(text + "   ");
        text = removeHtmlTags(text);
        System.out.println(text);

        text = "空山<p>新雨后，天气晚来秋。";
        System.out.print(text + "   ");
        text = removeHtmlTags(text);
        System.out.println(text);

        text = "<span>空山新<p style='color=red'>雨</p>后，天气晚来秋。</span>";
        System.out.print(text + "   ");
        text = removeHtmlTags(text);
        System.out.println(text);

        text = "<p style=\"color=red\">空山新雨后，天气晚来秋。</p>";
        System.out.print(text + "   ");
        text = removeHtmlTags(text);
        System.out.println(text);

        text = "<空山>新雨后，天气晚来秋。</空山>";
        System.out.print(text + "   ");
        text = removeHtmlTags(text);
        System.out.println(text);
    }

    public static String removeLeadingDigits(String text) {
        Matcher matcher = spuNumPattern.matcher(text);
        if (matcher.find()) {
            return matcher.replaceFirst("");
        }
        return text;
    }

    public static boolean containChar(String str){
        Matcher matcher = charPattern.matcher(str);
        return matcher.replaceAll("").trim().length() == 0;
    }

    public static String removeHtml(String text) {
        if(StringUtils.isEmpty(text)){
            return text;
        }

        text.replaceAll("\n", "!abc!");

        // jsoup提取文本内容
        text = Jsoup.parse(text).text();
        return text.replaceAll("!abc!", "\n");
    }

    public static String removeHtmlTags(String htmlText) {
        // 匹配HTML标签的正则表达式，确保尖括号内部不包含中文字符
        String htmlPattern = "<[^>]*>";
        Pattern pattern = Pattern.compile(htmlPattern);
        Matcher matcher = pattern.matcher(htmlText);

        StringBuffer plainText = new StringBuffer();
        while (matcher.find()) {
            String group = matcher.group();
            // 检查标签内部是否包含中文字符，若不包含则替换为空格，否则保留原始内容
            if (!containsChineseCharacters(group)) {
                matcher.appendReplacement(plainText, "");
            } else {
                matcher.appendReplacement(plainText, group);
            }
        }
        matcher.appendTail(plainText);

        return plainText.toString().trim();
    }

    public static boolean containsChineseCharacters(String input) {
        // 判断字符串是否包含中文字符
        return input.matches(".*[\\u4E00-\\u9FA5].*");
    }

}
