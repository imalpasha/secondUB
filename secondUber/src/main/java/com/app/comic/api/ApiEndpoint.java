package com.app.comic.api;



public class ApiEndpoint{

    public static String getUrl() {
        return "http://192.168.0.167/second_book/public/api/";
    }

    public static String imagePath() {
        return "http://192.168.0.167/second_book/public/user_image/";
    }

    public static String imageBookPath() {
        return "http://192.168.0.167/second_book/public/book_image/";
    }


    // @Override
    public String getName() {
        return "Production Endpoint";
    }
    //
}

//http://sheetsu.com/apis/c4182617