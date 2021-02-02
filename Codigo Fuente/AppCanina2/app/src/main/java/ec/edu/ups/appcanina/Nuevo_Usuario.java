package ec.edu.ups.appcanina;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.util.concurrent.UncheckedTimeoutException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Nuevo_Usuario extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String usuario, contrasenia, confirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo__usuario);
        crearUsuario();
    }

    public void crearUsuario(){
        EditText txtusuario = (EditText)findViewById(R.id.txtNuevoUsuario);
        EditText txtcontrasenia = (EditText)findViewById(R.id.txtNuevaContrasenia);
        EditText txtconfirmar = (EditText)findViewById(R.id.txtConfirmarContrasenia);
        Button btnCrearUsuario = (Button)findViewById(R.id.btnCrearUsuario);
        btnCrearUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = txtusuario.getText().toString();
                contrasenia = txtconfirmar.getText().toString();
                confirmar = txtcontrasenia.getText().toString();
                if(contrasenia.equals(confirmar)){
                    ingresarBD();
                    Toast.makeText(Nuevo_Usuario.this, "Usuario Creado Safisfactoriamente",Toast.LENGTH_LONG).show();
                    Intent sesiIntenton = new Intent(v.getContext(), Iniciar_Sesion.class);
                    startActivityForResult(sesiIntenton,0);
                }else{
                    Toast.makeText(Nuevo_Usuario.this, "Contraseñas No Coiniciden",Toast.LENGTH_LONG).show();
                    txtconfirmar.setText("");
                    txtcontrasenia.setText("");
                }
            }
        });
    }

    public void ingresarBD(){
        int numero = (int)(Math.random()*100000+1);
        Map<String, Object> datosUsuario = new HashMap<>();
        datosUsuario.put("id",numero);
        datosUsuario.put("usuario", usuario);
        datosUsuario.put("contrasenia", contrasenia);
        db.collection("Administrador")
                .add(datosUsuario).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d("TAG", "Usuario Ingresado con ID: " + documentReference.getId());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("Error: ", e);
            }
        });
    }
}