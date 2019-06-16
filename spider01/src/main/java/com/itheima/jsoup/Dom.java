package com.itheima.jsoup;

import org.jsoup.Jsoup;

import java.io.File;
import java.io.IOException;

public class Dom {
    public static void main(String[] args) throws IOException {
        Jsoup.parse(new File("c:/a.txt"),"utf-8");
    }
}
