package com.example.breakthrough;

        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;

        import androidx.appcompat.app.AppCompatActivity;

public class levelSelectionActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_selection);

        configureNextButton();
    }
    private void configureNextButton(){
        Button lvl1Btn =(Button) findViewById(R.id.lvl1Btn);
        lvl1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(levelSelectionActivity.this, MainActivity.class));
            }

        });



        Button lvl2Btn =(Button) findViewById(R.id.lvl2Btn);
        lvl2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(levelSelectionActivity.this, MainActivity.class));
            }

        });
    }
}