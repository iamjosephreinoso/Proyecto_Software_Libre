package ec.edu.ups.appcanina;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Iniciar_Sesion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar__sesion);

        iniciarSesion();

    }
    public void iniciarSesion(){

        Button abrir = (Button)findViewById(R.id.btn_Iniciar);
            abrir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    EditText txtUsuario = (EditText)findViewById(R.id.txtUsuario);
                    EditText txtContrasenia = (EditText)findViewById(R.id.txtContrasenia);
                    String usuario = txtUsuario.getText().toString();
                    System.out.println(usuario);
                    String contrasenia = txtContrasenia.getText().toString();
                    if(usuario.equals("admin") && contrasenia.equals("12345")){
                        Toast.makeText(Iniciar_Sesion.this, "Inicio de Sesión Correcto",Toast.LENGTH_LONG).show();
                        Intent sesiIntenton = new Intent(v.getContext(), Ingresar_Caninos.class);
                        startActivityForResult(sesiIntenton,0);

                    }else{
                        Toast.makeText(Iniciar_Sesion.this, "Usuario o Contraseña Incorrecta",Toast.LENGTH_LONG).show();
                    }

                }
            });
    }
}