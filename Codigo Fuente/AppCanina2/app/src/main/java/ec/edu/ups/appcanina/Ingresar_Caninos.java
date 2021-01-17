package ec.edu.ups.appcanina;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ButtonBarLayout;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class Ingresar_Caninos extends AppCompatActivity {


    private String sexo, nombre, raza, descripcion, direccion;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar__caninos);
        ingresarCaninos();

    }

    public void ingresarCaninos(){
        EditText txtNombre = (EditText)findViewById(R.id.txtNombre);
        EditText txtRaza = (EditText)findViewById(R.id.txtRaza);
        EditText txtDescripcion = (EditText)findViewById(R.id.txtDescripcion);
        EditText txtDireccion = (EditText)findViewById(R.id.txtDireccion);
        CheckBox btnMacho = (CheckBox) findViewById(R.id.btnMacho);
        CheckBox btnHembra = (CheckBox)findViewById(R.id.btnHembra);
        FloatingActionButton agregar = (FloatingActionButton) findViewById(R.id.btnAgregar3);
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre = txtNombre.getText().toString();
                raza = txtRaza.getText().toString();
                descripcion = txtDescripcion.getText().toString();
                direccion = txtDireccion.getText().toString();

                if(btnMacho.isChecked()){
                    sexo = "Macho";
                    Toast.makeText(Ingresar_Caninos.this, "Mecho",Toast.LENGTH_LONG).show();
                }else{
                    if (btnHembra.isChecked()) {
                        sexo = "Hembra";
                        Toast.makeText(Ingresar_Caninos.this, "Hembra",Toast.LENGTH_LONG).show();
                    }
                }
                añadirBaseDatos();
            }
        });
    }
    public void añadirBaseDatos(){
        Map<String, Object> datosCanino = new HashMap<>();
        datosCanino.put("nombre", nombre);
        datosCanino.put("raza", raza);
        datosCanino.put("sexo", sexo);
        datosCanino.put("direccion", direccion);
        datosCanino.put("descripcion", descripcion);

        db.collection("Perros")
                .add(datosCanino).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d("TAG", "Canino Ingresado con ID: " + documentReference.getId());
                Toast.makeText(Ingresar_Caninos.this, "Ingresado Correctamente",Toast.LENGTH_LONG).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("Error: ", e);
                Toast.makeText(Ingresar_Caninos.this, "No se Ingreso el Canino",Toast.LENGTH_LONG).show();
            }
        });
    }
}