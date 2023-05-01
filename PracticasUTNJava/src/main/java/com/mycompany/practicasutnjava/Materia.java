/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.practicasutnjava;
/**
 *
 * @author makom
 */
import java.util.ArrayList;
import java.util.List;

class Materia {

    private String nombre;
    private ArrayList<String> correlativas = new ArrayList<String>();

    public Materia(String nombre) {
        this.nombre = nombre;
    }

    public Materia(String nombre, ArrayList<String> correlativas) {
        this.nombre = nombre;
        this.correlativas = correlativas;
    }

    public boolean puedeCursar(Alumno alumno) {
        boolean cumple = false;
        if ((noCursaLaMateria(alumno)) && (aproboLasCorrelativas(alumno))) {
            cumple = true;
        }
        return cumple;
    }

    public boolean aproboLasCorrelativas(Alumno alumno) {
        if (alumno.getMateriasAprobadas().containsAll(this.getCorrelativas())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean noCursaLaMateria(Alumno alumno) {
        if (!alumno.getMateriasAprobadas().contains(this.getNombre())) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean esAprobada(Materia correlativa, List<Materia> materiasAprobadas) {
        return materiasAprobadas.contains(correlativa);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCorrelativas(ArrayList<String> correlativas) {
        this.correlativas = correlativas;
    }

    public ArrayList<String> getCorrelativas() {
        return this.correlativas;
    }

}
