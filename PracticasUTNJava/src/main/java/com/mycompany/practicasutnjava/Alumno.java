/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.practicasutnjava;

import java.util.ArrayList;

class Alumno {

    private String nombre;
    private String legajo;
    private ArrayList<String> materiasAprobadas = new ArrayList<String>();

    public Alumno() {
    }

    public Alumno(String nombre) {
        this.nombre = nombre;
    }

    public Alumno(String nombre, String legajo, ArrayList<String> materiasAprobadas) {
        this.nombre = nombre;
        this.legajo = legajo;
        this.materiasAprobadas = materiasAprobadas;
    }

    

    public ArrayList<String> getMateriasAprobadas() {
        return this.materiasAprobadas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}