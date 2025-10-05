package ar.edu.unlam.dominio;

public enum Especialidad {
CARDIOLOGIA("1 - Cardiologia"),
CLINICA("2 - Clinica"), 
DERMATOLOGIA("3 - Dermatología"),
PEDIATRIA("4 - Pediatría"), 
TRAUMATOLOGIA("5 - Traumatología");
	
	
	String mensaje;
	
	Especialidad(String mensaje){
		this.mensaje = mensaje;
	}
	
	public String obtenerMensaje() {
		return this.mensaje;
	}
}
