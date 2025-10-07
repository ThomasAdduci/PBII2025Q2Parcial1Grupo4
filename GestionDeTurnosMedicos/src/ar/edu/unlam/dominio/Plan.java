package ar.edu.unlam.dominio;

public enum Plan {

	NO_POSEE(400, "1- No posee"),
	JUVENTUD(200, "2 -Plan Juventud"),
	ESTANDAR(100, "3 -plan Estandar"),
	COBERTURA_TOTAL(0, "4- Plan Cobertura Total");
	
	Integer copago;
	String mensaje;
	
	Plan(Integer cantidadCubierta,String mensaje) {
		this.copago = cantidadCubierta;
		this.mensaje = mensaje;
	}
	
	public Integer getCopago() {
		return copago;
	}

	public String getMensaje() {
		return mensaje;
	}
	
	
	
	
}
