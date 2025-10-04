package ar.edu.unlam.dominio;

public enum Plan {

	NO_POSEE(400),
	JUVENTUD(200),
	ESTANDAR(100),
	COBERTURA_TOTAL(0);
	
	Integer copago;
	
	Plan(Integer cantidadCubierta) {
		this.copago = cantidadCubierta;
	}
	
	public Integer getCopago() {
		return copago;
	}
	
}
