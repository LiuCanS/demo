package com.atguigu.springcloud;

public class Test {
    public static void main(String[] args) {
        String test = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<string xmlns=\"http://entinfo.cn/\">111833510032001966</string>";

        int length = test.length();
        String substring = test.substring(0, length - 1);

        int begin = substring.lastIndexOf(">");
        int index = substring.lastIndexOf("<");
        String substring1 = substring.substring(begin + 1, index);
        System.out.println(substring1);

    }

}
