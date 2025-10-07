package ar.edu.unlam.dominio;

public class Paciente extends Persona {
	
	private Plan plan;

	public Paciente(String nombre, String apellido, Integer edad, Integer dni, Plan plan) {
		super(nombre, apellido, edad, dni);
		this.plan = plan;
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public Plan getPlan() {
		return this.tipoPlan;
	}
}
