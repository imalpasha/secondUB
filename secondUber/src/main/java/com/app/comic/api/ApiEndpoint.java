package com.app.comic.api;



public class ApiEndpoint{

    public static String getUrl() {
        return "http://192.168.0.69/share_ride/public/api/";
    }

    public static String imagePath() {
        return "http://192.168.0.69/share_ride/public/user_image/";
    }

    public static String imageBookPath() {
        return "http://192.168.0.69/share_ride/public/book_image/";
    }

    // @Override
    public String getName() {
        return "Production Endpoint";
    }
    //
}

//http://sheetsu.com/apis/c4182617