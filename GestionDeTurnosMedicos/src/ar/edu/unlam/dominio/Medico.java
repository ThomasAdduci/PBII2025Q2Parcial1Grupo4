package ar.edu.unlam.dominio;

public class Medico extends Persona{
	
	private Especialidad tipoEspecialidad;
	private static Integer proximoId = 0;
	private Integer codigoMedico;

	public Medico(String nombre, String apellido, Integer edad, Integer dni,Especialidad tipoEspecialidad) {
		super(nombre, apellido, edad, dni);
		this.tipoEspecialidad = tipoEspecialidad;
		this.codigoMedico= ++proximoId;
	}

	public Especialidad getTipoEspecialidad() {
		return tipoEspecialidad;
	}

	
	public Integer getCodigoMedico() {
		return codigoMedico;
	}

	@Override
	public String toString() {
		return "Médico: "+ getNombreCompleto() + ". Especialidad " + tipoEspecialidad + ". Codigo del medico:  " + this.codigoMedico ; 
	}

	
	
	
}
