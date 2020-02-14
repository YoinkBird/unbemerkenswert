package com.example.firstspark;

import static spark.Spark.get;

public class FirstSpark {
    public static void main(String[] args) {
        //get("/first", (req, res) -> "First Spark application");
        get("/hello/:name", (req, res) -> "Hello " + req.params(":name"));
    }
}

// src: http://zetcode.com/java/spark/
