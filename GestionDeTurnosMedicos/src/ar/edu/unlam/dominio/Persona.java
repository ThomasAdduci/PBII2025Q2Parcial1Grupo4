package ar.edu.unlam.dominio;

import java.util.Objects;

public abstract class Persona {

	private String nombre;
	private String apellido;
	private Integer edad;
	private Integer dni;
	
	
	public Persona(String nombre, String apellido, Integer edad, Integer dni) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.edad = edad;
		this.dni = dni;
	}


	public String getNombre() {
		return nombre;
	}
	
	public String getNombreCompleto() {
		return nombre + " " + apellido;
	}


	public Integer getEdad() {
		return edad;
	}


	public Integer getDni() {
		return dni;
	}


	@Override
	public int hashCode() {
		return Objects.hash(dni);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Persona other = (Persona) obj;
		return Objects.equals(dni, other.dni);
	}
	
	
	
	
	
}
