/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.practicasutnjava;
/**
 *
 * @author makom
 */
import java.time.LocalDate;

public class Inscripcion {

    private Alumno alumno;
    private Materia materia;
    private LocalDate fecha;
    private boolean aprobada;

    public Inscripcion() {
    }

    public Inscripcion(Alumno alumno, Materia materia, LocalDate fecha) {
        this.alumno = alumno;
        this.materia = materia;
        this.fecha = fecha;
    }

    public boolean aprobada() {
        return this.materia.puedeCursar(this.alumno);
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

	public void setAprobada(boolean aprobada) {
		this.aprobada = aprobada;
	}
}