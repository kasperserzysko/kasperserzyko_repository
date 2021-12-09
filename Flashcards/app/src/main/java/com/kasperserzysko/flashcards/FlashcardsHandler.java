package com.kasperserzysko.flashcards;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class FlashcardsHandler{

    public FlashcardsHandler(){}

    public void getFlashcard(){

        Random random = new Random();
        String randomNumber = String.valueOf(random.nextInt(3));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(randomNumber);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Flashcard flashcard = snapshot.getValue(Flashcard.class);
                if( flashcard == null ) {
                    System.out.println("No messages");
                }
                else {
                    System.out.println("The first message is: " + flashcard.getContent() );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
            });
    }

}
