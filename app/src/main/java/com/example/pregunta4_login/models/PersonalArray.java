package com.example.pregunta4_login.models;

import java.util.ArrayList;

public class PersonalArray {
    private static PersonalArray instance;
    private ArrayList<Personal> array;

    // Private constructor to prevent instantiation
    private PersonalArray() {
        array = new ArrayList<>();
    }

    // Method to get the single instance of PersonalArray
    public static synchronized PersonalArray getInstance() {
        if (instance == null) {
            instance = new PersonalArray();
        }
        return instance;
    }

    public void saveRegister(Personal registro) {
        array.add(registro);
    }

    public ArrayList<Personal> showData() {
        return array;
    }
    public String foundData(int pos,String name){

     Personal  p=array.get(pos);
    return "Hola "+name+":"+"\n"
            +"Los datos registrados son:"+"\n"
            +"Nombre: "+p.getName()+"\n"
            +"Rol: "+p.getRol()+"\n"
            +"Nacimiento: "+p.getBorn()+"\n"
            +"Fecha de registro: "+p.getInputDate()+"\n"
            +"Estado: "+p.getStatus()+"\n"
            +"Usuario: "+p.getUser()+"\n"
            +"Contraseña: "+p.getPassword();
    }
}
