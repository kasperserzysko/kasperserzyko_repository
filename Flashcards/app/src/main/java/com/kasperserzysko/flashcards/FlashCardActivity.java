package com.kasperserzysko.flashcards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexboxLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;


public class FlashCardActivity extends AppCompatActivity {

    FlexboxLayout flexbox;
    TextView view_translate;
    EditText edt_answer;
    Button btn_check;
    ArrayList<Character> arr_content = new ArrayList<>();

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flexbox = findViewById(R.id.flexbox);
        view_translate = findViewById(R.id.view_translation);
        edt_answer = findViewById(R.id.edt_answer);
        btn_check = findViewById(R.id.btn_check);


        getFlashcard();

       btn_check.setOnClickListener(v -> {
           Toast.makeText(this, createContent(), Toast.LENGTH_SHORT).show();
           if (answerCheck()) {
               flexbox.removeAllViews();
               getFlashcard();
               arr_content.clear();
               edt_answer.setText("");
           }
       });

    }

    private void createFlashcard(TextView view, String content, String translation){

        //CREATING CHAR LIST FROM STRING
        char[] chrl_content = content.toUpperCase(Locale.ROOT).toCharArray();

        for( char i : chrl_content){
            arr_content.add(i);
        }

        // ADDING VIEWS TO ACTIVITY
        for (char c : crypt_flashcard(chrl_content)) {
            if (c == ' '){
                createSpace();
            } else if(c == '*'){
                createTextView(" ");
            }else {
                //CREATING TEXT VIEWS
                createTextView(String.valueOf(c));
            }
        }
        changeTranslate(view, translation);
    }

    //CRYPTING FLASHCARDS
    private char[] crypt_flashcard(char[] chrl_flashcard){

        Random random = new Random();
        for (int i = 0; i <=chrl_flashcard.length/2+1; i++){
            int random_index = random.nextInt(chrl_flashcard.length-1);
            if(chrl_flashcard[random_index] != ' ') {
                chrl_flashcard[random_index] = '*';
            }else
                i--;
        }return chrl_flashcard;
    }

    //CREATING SPACE
    private void createSpace(){
        Space space = new Space(this);
        space.setLayoutParams(new FlexboxLayout.LayoutParams(30, 0));
        flexbox.addView(space);

    }

    //CREATING TEXTVIEW
    private void createTextView(String content_text){
        Typeface tf = ResourcesCompat.getFont(this, R.font.assistant_semibold);
        TextView textView = new TextView(this);

        textView.setLayoutParams(new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT));
        textView.setText(content_text);
        textView.setTypeface(tf);
        textView.setWidth(85);
        textView.setHeight(150);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setTextSize(30);
        textView.setBackgroundResource(R.drawable.letter_container);

        flexbox.addView(textView);
        createSpace();
    }

    //TEXT VIEW WITH POLISH TRANSLATION
    private void changeTranslate(TextView view, String translation){
        view.setText(translation);
    }
    private String createContent(){
        //while (arr_content.isEmpty()){
        //    try {
        //        wait();
        //    } catch (InterruptedException e) {
        //        e.printStackTrace();
         //   }
       // }
        StringBuilder builder = new StringBuilder();
        for(char i : arr_content){
            builder.append(i);
        }


        return builder.toString();
    }

    private boolean answerCheck(){

        String answer = edt_answer.getText().toString().toLowerCase();

        if(createContent().toLowerCase(Locale.ROOT).equals(answer)){
            Toast.makeText(this, createContent(), Toast.LENGTH_SHORT).show();

            return true;
        }else {
            Toast.makeText(this, createContent(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void getFlashcard(){

        Random random = new Random();
        String randomNumber = String.valueOf(random.nextInt(3));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(randomNumber);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Flashcard flashcard = snapshot.getValue(Flashcard.class);
                createFlashcard(view_translate, flashcard.getContent(), flashcard.getTranslation());

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