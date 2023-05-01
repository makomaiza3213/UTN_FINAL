/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.practicasutnjava;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author makom
 */
class Materia {

    private String nombre;
    private ArrayList<String> correlativas;

    public Materia(String nombre) {
        this.nombre = nombre;
    }

    public Materia(String nombre, ArrayList<String> correlativas) {
        this.nombre = nombre;
        this.correlativas = correlativas;
    }

    public boolean puedeCursar(Alumno alumno) {
        boolean cumple = false;
        if (this.correlativas.size() == 0 && (alumno.getMateriasAprobadas().containsAll(CURSOINGRESO))) {
            cumple = true;
        } else if (alumno.getMateriasAprobadas().containsAll(CURSOINGRESO)) {
            for (int i = 0; i < this.correlativas.size(); i++) {
                if (esAprobada(this.correlativas.get(i), alumno.getMateriasAprobadas())) {
                    cumple = true;
                    break;
                }
            }
        }
        return cumple;
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
        return correlativas;
    }
    
    
}
