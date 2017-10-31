package com.example.kwaliteitvanleven;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class listActivity extends AppCompatActivity {

    private ListView mListView;
    List<String> listItems = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("Antwoorden").child("User123");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        mListView = (ListView) findViewById(R.id.mListView);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Hier nog toevoeging doen van meerder datums
                String value = dataSnapshot.child("test1").child("31-10-2017").child("Totale_punten").getValue(String.class);
                listItems.add(value);

                if(listItems.size() == 1)
                {
                    adapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,listItems);
                    mListView.setAdapter(adapter);
                }
                else if(listItems.size() > 1)
                {
                    adapter.notifyDataSetChanged();
                }

                Toast.makeText(listActivity.this, value, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        /*String[] listItems = {"Kip", "Kaas", "Boter", "Test","Kip", "Kaas", "Boter", "Test",
        "Kip", "Kaas", "Boter", "Test","Kip", "Kaas", "Boter", "Test",
                "Kip", "Kaas", "Boter", "Test","Kip", "Kaas", "Boter", "Test"};*/
       /* mListView = (ListView) findViewById(R.id.list_view);
        String[] arr = listItems.toArray(new String[listItems.size()]);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arr);
        mListView.setAdapter(adapter);*/
    }
}
