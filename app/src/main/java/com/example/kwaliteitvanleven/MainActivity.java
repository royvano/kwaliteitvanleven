package com.example.kwaliteitvanleven;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView countLabel;
    private TextView questionLabel;
    private Button answerBtn1;
    private Button answerBtn2;
    private Button answerBtn3;
    private Button answerBtn4;

    private int countPoints;
    static final private int QUIZ_COUNT = 5;
    private int quizCount = 1;

    ArrayList<ArrayList<String>> quizArray = new ArrayList<>();

    String quizData[][] = {
            // {"Onderwerp", "Antwoord1", "A2", "A3", "A4"}
            {"pijn in de nek?", "Nee", "soms", "regelmatig", "vaak"},
            {"overmatige transpiratie?", "Nee", "soms", "regelmatig", "vaak"},
            {"benauwdheid?", "Nee", "soms", "regelmatig", "vaak"},
            {"pijn in de borst?", "Nee", "soms", "regelmatig", "vaak"},
            {"onrustig slapen?", "Nee", "soms", "regelmatig", "vaak"},
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countPoints = 0;
        countLabel = (TextView)findViewById(R.id.countLabel);
        questionLabel = (TextView)findViewById(R.id.questionLabel);
        answerBtn1 = (Button)findViewById(R.id.answerBtn1);
        answerBtn2 = (Button)findViewById(R.id.answerBtn2);
        answerBtn3 = (Button)findViewById(R.id.answerBtn3);
        answerBtn4 = (Button)findViewById(R.id.answerBtn4);

        // Maak quizArray van quizData.
        for (int i = 0; i < quizData.length; i++) {
            //Array maken.
            ArrayList<String> tmpArray = new ArrayList<>();
            tmpArray.add(quizData[i][0]);  // Country
            tmpArray.add(quizData[i][1]);  // Right Answer
            tmpArray.add(quizData[i][2]);  // Choice1
            tmpArray.add(quizData[i][3]);  // Choice2
            tmpArray.add(quizData[i][4]);  // Choice3

            // Voeg tmpArray toe aan quizArray.
            quizArray.add(tmpArray);
        }

        showNextQuestion();

    }

    public void showNextQuestion() {

        // Update quizCountLabel.
        countLabel.setText("Hebt u de afgelopen week last van:");

        // Random nummer tussen 0 en quizArray's grootte
        Random random = new Random();
        int randomNum = random.nextInt(quizArray.size());

        // Kies 1 vraag/quiz set
        ArrayList<String> quiz = quizArray.get(randomNum);

        // Vraag instellen
        // Array format: {"Onderwerp", "Antwoord1", "A2", "A3", "A4"}
        questionLabel.setText(quiz.get(0));

        // Onderwerp uit array halen en derest shuffelen
        quiz.remove(0);
        //Collections.shuffle(quiz);

        // Antwoorden instellen
        answerBtn1.setText(quiz.get(0));
        answerBtn2.setText(quiz.get(1));
        answerBtn3.setText(quiz.get(2));
        answerBtn4.setText(quiz.get(3));

        // Deze vraag/quiz uit array halen
        quizArray.remove(randomNum);

    }

    public void checkAnswer(View view) {

        //Vraag opvragen
        TextView question = (TextView) findViewById(R.id.questionLabel);
        String questionString = question.getText().toString();

        // Ingedrukte knop opvragen
        Button answerBtn = (Button) findViewById(view.getId());
        String btnText = answerBtn.getText().toString();

        String alertTitle = "Bedankt!";

        switch (btnText){
        case "Nee":
            countPoints++;
            break;
        case "soms":
            countPoints += 2;
            break;
        case "regelmatig":
            countPoints += 3;
            break;
        case "vaak":
            countPoints += 4;
            break;
        }

        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yyyy");
        Date todayDate = new Date();
        final String thisDate = currentDate.format(todayDate);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Antwoorden").child("User123");
        
        myRef.child("test1").child(thisDate).child(questionString).setValue(btnText);

        if(quizCount > 4) {
            final String Ja = "Ja";
            final String Nee = "Nee";

            String countString = Integer.toString(countPoints);
            myRef.child("test1").child(thisDate).child("Totale_punten").setValue(countString);

            // Maak een melding
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(alertTitle);
            builder.setMessage("Denk je dat je goed in je vel zit?");

            builder.setPositiveButton(Ja, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (quizCount == QUIZ_COUNT)
                    {
                        // Show Result.
                        Intent intent = new Intent(getApplicationContext(), hulpActivity.class);
                        intent.putExtra("DE_SCORE", Integer.toString(countPoints));
                        intent.putExtra("GOED_EIGEN_GEVOEL", Ja);
                        myRef.child("test1").child(thisDate).child("GOED_EIGEN_GEVOEL").setValue(Ja);
                        startActivity(intent);

                    } else {
                        quizCount++;
                        showNextQuestion();
                    }
                }
            });

            builder.setNegativeButton(Nee, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (quizCount == QUIZ_COUNT) {
                        // Show Result.
                        Intent intent = new Intent(getApplicationContext(), hulpActivity.class);
                        intent.putExtra("DE_SCORE", Integer.toString(countPoints));
                        intent.putExtra("GOED_EIGEN_GEVOEL", Nee);

                        myRef.child("test1").child(thisDate).child("GOED_EIGEN_GEVOEL").setValue(Nee);
                        startActivity(intent);

                    } else {
                        quizCount++;
                        showNextQuestion();
                    }
                }
            });
            builder.setCancelable(false);
            builder.show();
        } else {
            quizCount++;
            showNextQuestion();
        }
    }
}
