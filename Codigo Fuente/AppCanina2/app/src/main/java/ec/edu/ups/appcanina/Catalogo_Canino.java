package ec.edu.ups.appcanina;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.LauncherActivity;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import ec.edu.ups.appcanina.modelo.Caninos;

public class Catalogo_Canino extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    static List<Caninos> lista = new ArrayList<Caninos>();
    List<Caninos> lista3= new ArrayList<Caninos>();
    private ListAdapter listAdapter;
    private RecyclerView lista2;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo__canino);
        lista2 = (RecyclerView)findViewById(R.id.listaFinal);
        lista2.setLayoutManager(new LinearLayoutManager(this));
        imprimirDatos();
        regresar();

    }

    public void imprimirDatos() {

      /*  FloatingActionButton b = (FloatingActionButton)findViewById(R.id.btnBuscar);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {*/
                db.collection("Perros")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    Caninos c1 = new Caninos();
                                    lista.remove(c1);
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d("TAG", document.getId() + " => " + document.getData());
                                        Caninos c = new Caninos();
                                        c.setId(Integer.parseInt(document.getData().get("id").toString()));
                                        c.setNombre(document.getData().get("nombre").toString());
                                        c.setRaza(document.getData().get("raza").toString());
                                        c.setSexo(document.getData().get("sexo").toString());
                                        c.setDireccion(document.getData().get("direccion").toString());
                                        c.setDescripcion(document.getData().get("descripcion").toString());
                                        c.setFoto(document.getData().get("foto").toString());
                                        lista.add(c);
                                        //Toast.makeText(Catalogo_Canino.this, "Ingresado Correctamente", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Log.d("TAG", "Error getting documents: ", task.getException());
                                }
                            }
                        });
                System.out.println("Tamaño de la lista es " + lista.size());
                for (int i = 0; i < lista.size(); i++) {
                    System.out.println(lista.get(i).getClass().toString());
                }

            //}

        //});

    }

    @Override
    protected void onStart() {
        super.onStart();
        List<Caninos> list = getLista();
        List<Caninos> listaLimpia = new ArrayList<Caninos>();
        Map<Integer, Caninos> hapCanes = new HashMap<Integer, Caninos>(list.size());
        //Aquí está la magia
        for(Caninos p : list) {
            hapCanes.put(p.getId(), p);
        }
        for(Map.Entry<Integer, Caninos> p : hapCanes.entrySet()) {
            listaLimpia.add(p.getValue());
        }
        listAdapter = new ListAdapter(listaLimpia, this);
        lista2.invalidateItemDecorations();
        lista2.setAdapter(listAdapter);
    }

    public void regresar(){
        FloatingActionButton menu = (FloatingActionButton) findViewById(R.id.btn_menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sesiIntenton = new Intent(v.getContext(), MainActivity.class);
                startActivityForResult(sesiIntenton,0);
            }
        });
    }

    public List<Caninos> getLista() {
        return lista;
    }
}