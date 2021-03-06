package ec.edu.ups.appcanina;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ButtonBarLayout;

import android.content.Intent;
import android.net.Uri;
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
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import ec.edu.ups.appcanina.modelo.Caninos;
import io.grpc.Context;

public class Ingresar_Caninos extends AppCompatActivity {


    private String sexo, nombre, raza, descripcion, direccion, nombreFoto;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
    Uri file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar__caninos);
        ingresarCaninos();
        seleccionarFoto();
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
                }else{
                    if (btnHembra.isChecked()) {
                        sexo = "Hembra";
                    }
                }
                Caninos c = new Caninos();
                c.setNombre(nombre);
                c.setRaza(raza);
                c.setSexo(sexo);
                c.setDireccion(direccion);
                c.setDescripcion(descripcion);
                c.setFoto(nombreFoto);
                añadirBaseDatos();
                subirFoto();
            }
        });
    }
    public void añadirBaseDatos(){
        EditText txtNombre = (EditText)findViewById(R.id.txtNombre);
        EditText txtRaza = (EditText)findViewById(R.id.txtRaza);
        EditText txtDescripcion = (EditText)findViewById(R.id.txtDescripcion);
        EditText txtDireccion = (EditText)findViewById(R.id.txtDireccion);
        int numero = (int)(Math.random()*100000+1);
        Map<String, Object> datosCanino = new HashMap<>();
        datosCanino.put("id", numero);
        datosCanino.put("nombre", nombre);
        datosCanino.put("raza", raza);
        datosCanino.put("sexo", sexo);
        datosCanino.put("direccion", direccion);
        datosCanino.put("descripcion", descripcion);
        datosCanino.put("foto", nombreFoto);

        db.collection("Perros")
                .add(datosCanino).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d("TAG", "Canino Ingresado con ID: " + documentReference.getId());
                Toast.makeText(Ingresar_Caninos.this, "Ingresado Correctamente",Toast.LENGTH_LONG).show();
                txtDireccion.setText("");
                txtDescripcion.setText("");
                txtNombre.setText("");
                txtRaza.setText("");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("Error: ", e);
                Toast.makeText(Ingresar_Caninos.this, "No se Ingreso el Canino",Toast.LENGTH_LONG).show();
            }
        });
    }
    public  void subirFoto(){
        StorageReference riversRef = mStorageRef.child(nombreFoto);
        riversRef.putFile(file)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });

    }
    public void seleccionarFoto(){
        Button subir = (Button)findViewById(R.id.btnFoto);
        subir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.startPickImageActivity(Ingresar_Caninos.this);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        file = CropImage.getPickImageResultUri(this,data);
        File url = new File(file.getPath());
        nombreFoto = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        Toast.makeText(Ingresar_Caninos.this, "Imagen Seleccionada",Toast.LENGTH_LONG).show();
    }
}