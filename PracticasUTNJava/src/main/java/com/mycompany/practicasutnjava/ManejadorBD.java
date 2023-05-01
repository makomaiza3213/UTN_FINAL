/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.practicasutnjava;
/**
 *
 * @author makom
 */
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ManejadorBD {
	public int agregarMateria(String nombre, String materias_correlativas) throws SQLException {
		Conexion conexion = new Conexion();
		Connection conectar = conexion.establecerConexion();
		PreparedStatement pstmt = conectar.prepareStatement("INSERT INTO Materias (nombre,correlativas) VALUES (?,?)");
		pstmt.setString(1, nombre);
		pstmt.setString(2, materias_correlativas);
		int filasAfectadas = pstmt.executeUpdate();
		conectar.close();
		return filasAfectadas;
	}

	public int agregarAlumno(String nombre, String legajo, String materias_aprobadas) throws SQLException {
		Conexion conexion = new Conexion();
		Connection conectar = conexion.establecerConexion();
		Statement stmt = conectar.createStatement();
		String consulta = "INSERT INTO Alumnos (nombre, legajo, materias_aprobadas) VALUES"
				+ " ('" + nombre + "', '" + legajo + "', '" + materias_aprobadas + "')";
		int filasAfectadas = stmt.executeUpdate(consulta);
		conectar.close();
		return filasAfectadas;
	}

	public boolean existeMateria(String nombre) throws SQLException {
		Conexion conexion = new Conexion();
		Connection conectar = conexion.establecerConexion();
		Statement stmt = conectar.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT EXISTS(SELECT * FROM Materias WHERE nombre = \"" + nombre + "\")");
		rs.next();
		boolean existe = rs.getBoolean(1);
		conectar.close();
		return existe;
	}
	
	public boolean existeAlumno(Object[] datos) throws SQLException {
		// establecer conexión
		Conexion conexion = new Conexion();
		// creamos una variable de tipo Connection para poder cerrar la conexión luego
		// de haber operado
		Connection conectar = conexion.establecerConexion();
		// operamos con la base de datos
		Statement stmt = conectar.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT EXISTS(SELECT * FROM Alumnos WHERE nombre = \"" + (String) datos[0]
				+ "\" AND legajo = \"" + (String) datos[1] + "\")");
		rs.next();
		boolean existe = rs.getBoolean(1);
		// cerramos la conexión
		conectar.close();
		return existe;
	}

	public HashMap<String, ArrayList<String>> deserializarJson(String aBuscar, String nombreColumnaDeserializar,
			String nombreTabla, String nombreColumnaCondicion)
			throws SQLException, JsonProcessingException, IOException {
		Connection conectar = null;
		Conexion conexion = new Conexion();
		// creo el objeto objectMapper para poder serializar o deserializar objetos JSON
		ObjectMapper objectMapper = new ObjectMapper();
		// invocamos a la funcion para que permita que un unico valor se trate como a
		// una matriz
		objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		HashMap<String, ArrayList<String>> datosHash = new HashMap<>();
		conectar = conexion.establecerConexion();
		Statement stmt = conexion.conectar.createStatement();
		// tomo la materia que se ingresó y su lista de correlativas
		ResultSet rs = stmt.executeQuery(
				"SELECT * FROM " + nombreTabla + " WHERE " + nombreColumnaCondicion + " = \"" + aBuscar + "\"");
		if (rs.next()) {
			// cargamos en jsonText el json de la BD usando el nombre de la columna
			String jsonText = objectMapper.writeValueAsString(rs.getString(nombreColumnaDeserializar));
			@SuppressWarnings("unchecked")
			ArrayList<String> nombresMaterias = objectMapper.readValue(jsonText, ArrayList.class);
			datosHash.put(aBuscar, nombresMaterias);
		}
		conectar.close();
		return datosHash;
	}
}