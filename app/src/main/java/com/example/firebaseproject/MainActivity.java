package com.example.firebaseproject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "FirestoreExample";
    private FirebaseFirestore db;
    private EditText integerEditText;
    private Button submitButton;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        integerEditText = findViewById(R.id.editText);
        submitButton = findViewById(R.id.button);
        resultTextView = findViewById(R.id.textViewInstruction);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the integer entered by the user
                String integerStr = integerEditText.getText().toString();

                try {
                    int integer = Integer.parseInt(integerStr);

                    // Create a data model instance with the integer
                    Count data = new Count(integer);

                    // Add data to Firestore
                    db.collection("numbers") // Replace with your collection name
                            .add(data)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    // Data added successfully
                                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());

                                    // Update the resultTextView to show a success message
                                    resultTextView.setText("Integer " + integer + " added successfully to Firestore.");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Error adding data
                                    Log.w(TAG, "Error adding document", e);

                                    // Update the resultTextView to show an error message
                                    resultTextView.setText("Error adding integer to Firestore.");
                                }
                            });
                } catch (NumberFormatException e) {
                    // Handle invalid input (non-integer)
                    resultTextView.setText("Invalid input. Please enter a valid integer.");
                }
            }
        });
    }
}