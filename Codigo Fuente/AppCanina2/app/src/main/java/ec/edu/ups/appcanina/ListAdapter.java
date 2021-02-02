package ec.edu.ups.appcanina;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import ec.edu.ups.appcanina.modelo.Caninos;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private static List<Caninos> listaCaninos;
    private LayoutInflater mInflater;
    private Context context;



    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();

    public ListAdapter(List<Caninos> items, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.listaCaninos = items;
    }

    @Override
    public int getItemCount() {
        return listaCaninos.size();
    }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_element, null);
        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder,final int position) {
        try {
            holder.bindData(listaCaninos.get(position));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView foto;
        TextView nombre, raza, sexo, descripcion, direccion;

        ViewHolder(View itemView){
            super(itemView);
            foto = itemView.findViewById(R.id.imgFoto);
            nombre = itemView.findViewById(R.id.txtNombreC);
            raza = itemView.findViewById(R.id.txtRazaC);
            sexo = itemView.findViewById(R.id.txtSexoC);
            direccion = itemView.findViewById(R.id.txtDireccionC);
            descripcion = itemView.findViewById(R.id.txtDescripcionC);


        }

        void bindData(final Caninos item) throws IOException {
            nombre.setText("Nombre: "+item.getNombre());
            raza.setText("Raza: "+item.getRaza());
            sexo.setText("Sexo: "+item.getSexo());
            direccion.setText("Dirección: "+item.getDireccion());
            descripcion.setText("Descripción: "+item.getDescripcion());
            StorageReference riversRef = mStorageRef.child(item.getFoto());
            File localFile = File.createTempFile("image", "jpg");
            riversRef.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Uri file = Uri.parse(item.getFoto());
                            Bitmap m = BitmapFactory.decodeFile(localFile.getPath());
                            foto.setImageBitmap(m);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle failed download
                    // ...
                }
            });

        }
    }
}
