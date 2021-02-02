package ec.edu.ups.appcanina;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import ec.edu.ups.appcanina.modelo.Caninos;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button abrir = (Button)findViewById(R.id.btn_Sesion);
        abrir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent sesiIntenton = new Intent(v.getContext(), Iniciar_Sesion.class);
                startActivityForResult(sesiIntenton,0);
            }
        });

        Button catalogo = (Button)findViewById(R.id.btn_catalogo);
        catalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent sesiIntenton = new Intent(v.getContext(), Catalogo_Canino.class);
                startActivityForResult(sesiIntenton,0);
            }
        });

        FloatingActionButton info = (FloatingActionButton) findViewById(R.id.btn_Info);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sesiIntenton = new Intent(v.getContext(), Informacion.class);
                startActivityForResult(sesiIntenton,0);
            }
        });
    }
}