package ar.edu.unlam.menu;

public enum Menum {
	ALTA_PACIENTE ("1 - Darse de alta como paciente"),
	RESERVAR_TURNO ("2 - Reservar un turno"),
	CANCELAR_TURNO("3 - Cancelar Turno"),
	SALIR ("4 - Salir");
	
	
	private String mensaje;
	
	Menum (String mensaje) {
		this.mensaje = mensaje;
	}

	
	public  String obtenerMensaje() {
		return this.mensaje;
	}
}
