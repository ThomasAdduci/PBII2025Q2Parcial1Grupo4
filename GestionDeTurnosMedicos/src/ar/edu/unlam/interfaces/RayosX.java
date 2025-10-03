package ar.edu.unlam.interfaces;

public class RayosX implements AccesorioClinico {

	@Override
	public Integer getCosto() {
		return 100;
	}

	@Override
	public String getNombre() {
		return "Rayos X";
	}

}
