package ar.edu.unlam.dominio;

public class Paciente extends Persona {
private Plan tipoPlan;
	public Paciente(String nombre, String apellido, Integer edad, Integer dni,Plan tipoPlan) {
		super(nombre, apellido, edad, dni);
		this.tipoPlan=tipoPlan;
	}

	public Plan getPlan() {
		return this.tipoPlan;
	}
}
