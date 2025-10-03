package ar.edu.unlam.interfaces;

public class AnalisisSangre implements AccesorioClinico {

	@Override
	public Integer getCosto() {
		return 200;
	}

	@Override
	public String getNombre() {
		return "Analisis de Sangre";
	}

}
