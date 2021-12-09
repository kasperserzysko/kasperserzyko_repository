package com.kasperserzysko.flashcards;

import java.util.ArrayList;

public class Util {

    static ArrayList<Integer> database_ids = new ArrayList<>();


    public static ArrayList<Integer> workIDS(){
        for (int i = 0; i <4; i++){
            database_ids.add(i);
        }
        return database_ids;
    }
}
