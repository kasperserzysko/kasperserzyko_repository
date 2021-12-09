package com.kasperserzysko.flashcards;

public class Flashcard {

     private String content;
     private String translation;

    public Flashcard(){}

    public Flashcard(String content, String translation) {
        this.content = content;
        this.translation = translation;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }
}
