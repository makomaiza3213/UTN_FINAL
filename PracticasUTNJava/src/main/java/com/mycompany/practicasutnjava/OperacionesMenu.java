/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.practicasutnjava;

/**
 *
 * @author makom
 */
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;

public class OperacionesMenu {

    public void menuPrincipal() throws SQLException, IOException {
        // Creo el objeto Manejar para operar en la bd
        ManejadorBD manejador = new ManejadorBD();
        // resultados
        Object[] datosIngresados = null;
        // Menu inicial
        Object[] opciones = {"Realizar una inscripcion", "Agregar Alumnos/Materias"};
        int seleccion = JOptionPane.showOptionDialog(null, "Elija una opción", "Menú", JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);
        if (seleccion == 0) {
            this.realizarInscripcion(datosIngresados, manejador);
        } else {
            // otro menú de opciones para seleccionar entre agregar un alumno o una materia
            Object[] opciones2 = {"Agregar Alumno", "Agregar Materia"};
            seleccion = JOptionPane.showOptionDialog(null, "Elija una opción", "Menú", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, null, opciones2, opciones2[0]);
            if (seleccion == 0) {
                datosIngresados = this.menuIngresarDatosAlumno();
                if (datosIngresados != null) {
                    if (manejador.existeAlumno(datosIngresados) == false) {
                        int exito = manejador.agregarAlumno((String) datosIngresados[0], (String) datosIngresados[1],
                                (String) datosIngresados[2]);
                        if (exito == 1) {
                            JOptionPane.showMessageDialog(null,
                                    "El alumno " + (String) datosIngresados[0] + " ha sido cargado con exito");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "El alumno ya se encuentra cargado.", null,
                                JOptionPane.WARNING_MESSAGE);
                    }
                }
            } else {
                datosIngresados = this.menuIngresarDatosMateria(manejador);
                if (datosIngresados != null) {
                    if (!manejador.existeMateria((String) datosIngresados[0])) {
                        int exito = manejador.agregarMateria((String) datosIngresados[0], (String) datosIngresados[1]);
                        if (exito == 1) {
                            JOptionPane.showMessageDialog(null, "La materia " + (String) datosIngresados[0] + " ah sido agregada con exito");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "La materia " + (String) datosIngresados[0] + "ya se encuentra cargada");
                    }
                }
            }
        }
    }

    public Object[] menuIngresarDatosAlumno() {
        // Creamos el panel grafico
        JPanel panel = new JPanel();
        // añadimos texto al panel
        panel.add(new JLabel("Nombre:"));
        // añadimos un inputText al panel
        JTextField nombreField = new JTextField(10);
        panel.add(nombreField);
        panel.add(new JLabel("Legajo:"));
        JTextField legajoField = new JTextField(10);
        panel.add(legajoField);
        panel.add(new JLabel("Cantidad materias aprobadas:"));
        int valorPorDefault = 0; // Valor por defecto
        int min = 0; // Valor mínimo permitido
        int max = 50; // Valor máximo permitido
        int valorDePaso = 1; // Incremento/decremento con cada flecha
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(valorPorDefault, min, max, valorDePaso));
        panel.add(spinner);

        // Mostramos el cuadro de diálogo con el panel personalizado
        JOptionPane.showOptionDialog(null, panel, "Ingrese los datos", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, new String[]{"Aceptar", "Cancelar"}, "Aceptar");
        // verificar que el legajo posea 5 dígitos numéricos
        if (this.legajoValido(legajoField.getText())) {
            ArrayList<String> materias = new ArrayList<String>();
            if ((int) spinner.getValue() != 0) {
                // menú para ingresar el nombre de todas las materias aprobadas que tenga el
                // alumno
                materias = this.ingresarNombresMaterias((Integer) spinner.getValue());
            }
            // convierto la lista de materias ingresadas al formato JSON con GSON para
            // mandarlo a la BD
            String materiasJson = this.convertirAJson(materias);
            // obtenemos los valores ingresados
            Object[] datos = {nombreField.getText(), legajoField.getText(), materiasJson};
            return datos;
        } else {
            JOptionPane.showMessageDialog(null, "Legajo invalido");
            return null;
        }
    }

    public Object[] menuIngresarDatosMateria(ManejadorBD manejador) throws SQLException { // falta este
        JPanel panel = new JPanel();
        panel.add(new JLabel("Nombre:"));
        JTextField nombreField = new JTextField(10);
        panel.add(nombreField);
        panel.add(new JLabel("Cantidad de correlativas:"));
        int valorPorDefault = 0;
        int min = 0;
        int max = 50;
        int valorDePaso = 1;
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(valorPorDefault, min, max, valorDePaso));
        panel.add(spinner);
        JOptionPane.showOptionDialog(null, panel, "Ingrese los datos", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, new String[]{"Aceptar", "Cancelar"}, "Aceptar");
        if (!manejador.existeMateria(nombreField.getText())) {
            ArrayList<String> materias = new ArrayList<String>();
            if ((int) spinner.getValue() != 0) {
                materias = this.ingresarNombresMaterias((Integer) spinner.getValue());
            }
            String materiasJson = this.convertirAJson(materias);
            Object[] datos = {nombreField.getText(), materiasJson};
            return datos;
        } else {
            JOptionPane.showMessageDialog(null, "La materia ya se encuentra cargada");
            return null;
        }

    }

    public String convertirAJson(ArrayList<String> materias) {
        String materiasJson = new Gson().toJson(materias);
        System.out.println(materiasJson);
        return materiasJson;
    }

    public ArrayList<String> ingresarNombresMaterias(int cant) {
        // ordeno la estructura del panel para que sea como una matriz 
        JPanel panel = new JPanel(new GridLayout(cant, 2));
        ArrayList<String> materias = new ArrayList<String>();
        for (int i = 0; i < cant; i++) {
            panel.add(new JLabel("Nombre:"));
            JTextField nombreField = new JTextField(20);
            panel.add(nombreField);
        }
        int opcion = JOptionPane.showOptionDialog(null, panel, "Ingrese las materias", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, new String[]{"Aceptar", "Cancelar"}, "Aceptar");

        if (opcion == JOptionPane.OK_OPTION) {
            // tomo los componentes del panel, es decir los nombres de las materias
            // ingresados
            Component[] componentes = panel.getComponents();
            for (Component componente : componentes) {
                // verifico que cada componente sea un JTextField para poder extraer el dato
                // contenido ingresado en el
                if (componente instanceof JTextField) {
                    String valor = ((JTextField) componente).getText();
                    materias.add(valor);
                }
            }
            return materias;
        } else {
            return null;
        }
    }

    public Object[] menuIngresarDatosInscripcion() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Nombre:"));
        JTextField nombreField = new JTextField(10);
        panel.add(nombreField);
        panel.add(new JLabel("Legajo:"));
        JTextField legajoField = new JTextField(10);
        panel.add(legajoField);
        panel.add(new JLabel("Materia:"));
        JTextField materiaField = new JTextField(10);
        panel.add(materiaField);

        // Mostramos el cuadro de diálogo con el panel personalizado
        int opcion = JOptionPane.showOptionDialog(null, panel, "Ingrese los datos", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null, new String[]{"Aceptar", "Cancelar"}, "Aceptar");

        // Si se hace clic en Aceptar, obtenemos los valores ingresados
        if (opcion == JOptionPane.OK_OPTION) {
            Object[] datos = {nombreField.getText(), legajoField.getText(), materiaField.getText()};
            return datos;
        }
        return null;
    }

    private boolean legajoValido(String legajo) {
        String regex = "[0-9]{5}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(legajo);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    public void realizarInscripcion(Object[] datosIngresados, ManejadorBD manejador)
            throws JsonProcessingException, HeadlessException, SQLException, IOException {
        // formulario para ingresar nombre legajo y el nombre de la materia
        datosIngresados = this.menuIngresarDatosInscripcion();
        // verificar el legajo ingresado, y realizar proceso de inscripcion
        if (legajoValido((String) datosIngresados[1])) {
            // si el legajo es valido, busco en la bd si existe el alumno
            if (manejador.existeMateria((String) datosIngresados[2])) {
                if (manejador.existeAlumno(datosIngresados)) {
                    // traigo de la bd la lista de mat aprob y la lista de correl de la materia en
                    // cuestion
                    HashMap<String, ArrayList<String>> materiasCorrelativas = manejador
                            .deserializarJson((String) datosIngresados[2], "correlativas", "Materias", "nombre");
                    HashMap<String, ArrayList<String>> materiasAprobadas = manejador
                            .deserializarJson((String) datosIngresados[1], "materias_aprobadas", "Alumnos", "legajo");
                    Alumno alumno = new Alumno((String) datosIngresados[0], (String) datosIngresados[1],
                            materiasAprobadas.get((String) datosIngresados[1]));
                    Materia materia = new Materia((String) datosIngresados[2],
                            materiasCorrelativas.get((String) datosIngresados[2]));
                    LocalDate ahora = LocalDate.now();
                    Inscripcion inscripcion = new Inscripcion(alumno, materia, ahora);
                    // si la materia no tiene correlativas y no la ah aprobada aún, la inscripcion es exitosa
                    if (!materiasAprobadas.isEmpty() && materiasCorrelativas.isEmpty()) {
                        if (inscripcion.getMateria().noCursaLaMateria(alumno)) {
                            inscripcion.setAprobada(true);
                            JOptionPane.showMessageDialog(null, "Inscripción realizada con exito");
                        } else {
                            inscripcion.setAprobada(false);
                            JOptionPane.showMessageDialog(null, "Inscripcion rechazada");
                        }
                    } else if (!materiasAprobadas.isEmpty() && !materiasCorrelativas.isEmpty()) {
                        if (inscripcion.getMateria().puedeCursar(alumno)) {
                            inscripcion.setAprobada(true);
                            JOptionPane.showMessageDialog(null, "Inscripción realizada con exito");
                        } else {
                            inscripcion.setAprobada(false);
                            JOptionPane.showMessageDialog(null, "Inscripción rechazada");
                        }
                    }
                } else {
                    // inscripcion rechazada
                    JOptionPane.showMessageDialog(null, "Inscripción rechazada, el alumno ingresado no existe.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Inscripción rechazada, la materia ingresada no existe.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "LEGAJO INVÁLIDO.");
        }
    }
}
