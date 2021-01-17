package ec.edu.ups.appcanina;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
    }
}