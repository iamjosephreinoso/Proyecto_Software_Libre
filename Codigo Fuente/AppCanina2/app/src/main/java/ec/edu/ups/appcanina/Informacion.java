package ec.edu.ups.appcanina;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Informacion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion);

        FloatingActionButton menu = (FloatingActionButton)findViewById(R.id.btn_Menu2);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sesiIntenton = new Intent(v.getContext(), MainActivity.class);
                startActivityForResult(sesiIntenton,0);
            }
        });
    }
}