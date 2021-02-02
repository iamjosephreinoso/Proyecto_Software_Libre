package ec.edu.ups.appcanina.modelo;

import android.net.Uri;

public class Caninos {

    private String nombre;
    private String raza;
    private String sexo;
    private String descripcion;
    private String direccion;
    private String foto;
    private int id;

    public Caninos(String nombre, String raza, String sexo, String direccion, String descripcion,  String foto) {
        this.nombre = nombre;
        this.raza = raza;
        this.sexo = sexo;
        this.direccion = direccion;
        this.descripcion = descripcion;
        this.foto = foto;
    }

    public Caninos(){

    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getFoto() {
        return foto;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
