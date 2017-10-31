package com.example.kwaliteitvanleven;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class hulpActivity extends AppCompatActivity {

    private TextView resultlabel;
    private TextView titellabel;
    private TextView scoreLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hulp);

        resultlabel = (TextView)findViewById(R.id.resultLabel);
        titellabel = (TextView)findViewById(R.id.titellabel);
        scoreLabel = (TextView)findViewById(R.id.totalScoreLabel);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null){
            String scorePunten = (String) bundle.get("DE_SCORE");
            int scoreInt = Integer.parseInt(scorePunten);

            if(scoreInt <= 5){ resultlabel.setText("Hieruit blijkt dat je je goed voelt"); } else
            if(scoreInt <= 10){ resultlabel.setText("Hieruit blijkt dat je je redelijk goed voelt"); } else
            if(scoreInt <= 15){ resultlabel.setText("Hieruit blijkt dat je je niet zo goed voelt"); } else
            if(scoreInt <= 20){ resultlabel.setText("Hieruit blijkt dat je je niet goed voelt"); }

            scoreLabel.setText("Score: " + scorePunten);

            String eigenGevoel = (String) bundle.get("GOED_EIGEN_GEVOEL");
            switch (eigenGevoel) {
                case "Ja":
                    titellabel.setText("Je zei dat je je goed voelt");
                    break;
                case "Nee":
                    titellabel.setText("Je zei dat je je niet zo goed voelt");
                    break;

            }
        }

    }

    public void naarLocatie(View view) {
        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
        startActivity(intent);
    }

    public void naarResultaten(View view) {
        Intent intent = new Intent(getApplicationContext(), listActivity.class);
        startActivity(intent);
    }

    public void returnTop(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
