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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ec.edu.ups.appcanina.modelo.Administrador;

public class Iniciar_Sesion extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    static ArrayList<Administrador> lista = new ArrayList<Administrador>();
    List<Administrador> listaLimpia;
    String contrasenia,usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar__sesion);
        iniciarSesion();
        nuevoUsuario();
        verificarUsuario();

    }
    public void iniciarSesion(){
                    db.collection("Administrador")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {

                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Log.d("TAG", document.getId() + " => " + document.getData());
                                            Administrador a = new Administrador();
                                            a.setId(Integer.parseInt(document.getData().get("id").toString()));
                                            a.setUsuario(document.getData().get("usuario").toString());
                                            a.setContrasenia(document.getData().get("contrasenia").toString());
                                            lista.add(a);
                                        }
                                    } else {
                                        Log.d("TAG", "Error getting documents: ", task.getException());
                                    }
                                }
                            });

                    System.out.println(lista.size());

    }
    public void nuevoUsuario(){
        Button usuario = (Button)findViewById(R.id.btnNuevoUsuario);
        usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sesiIntenton = new Intent(v.getContext(), Nuevo_Usuario.class);
                startActivityForResult(sesiIntenton,0);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        ArrayList<Administrador> list = getLista();
        listaLimpia = new ArrayList<>();
        Map<Integer, Administrador> hapCanes = new HashMap<Integer, Administrador>(list.size());
        //Aquí está la magia
        for(Administrador p : list) {
            hapCanes.put(p.getId(), p);
        }
        for(Map.Entry<Integer, Administrador> p : hapCanes.entrySet()) {
            listaLimpia.add(p.getValue());
        }

    }

    public ArrayList<Administrador> getLista() {
        return lista;
    }

    public void verificarUsuario(){
        Button abrir = (Button)findViewById(R.id.btn_Iniciar);
        abrir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txtUsuario = (EditText)findViewById(R.id.txtUsuario);
                EditText txtContrasenia = (EditText)findViewById(R.id.txtContrasenia);
                usuario = txtUsuario.getText().toString();
                contrasenia = txtContrasenia.getText().toString();
                boolean bandera = false;
                for (int i=0; i<listaLimpia.size(); i++){
                    System.out.println(listaLimpia.get(i).getUsuario());
                    if(listaLimpia.get(i).getUsuario().equals(usuario) && listaLimpia.get(i).getContrasenia().equals(contrasenia)){
                        bandera = true;
                        Toast.makeText(Iniciar_Sesion.this, "Inicio de Sesión Correcto",Toast.LENGTH_LONG).show();
                        Intent sesiIntenton = new Intent(v.getContext(), Ingresar_Caninos.class);
                        startActivityForResult(sesiIntenton,0);
                    }
                }
                if(bandera==false){
                    Toast.makeText(Iniciar_Sesion.this, "Usuario o Contraseña Incorrecta",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}