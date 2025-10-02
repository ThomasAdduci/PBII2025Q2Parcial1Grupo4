package ar.edu.unlam.dominio;

public enum Plan { 
NO_POSEE(400), 
JUVENTUD(200), 
ESTANDAR(100), 
COBERTURA_TOTAL(0); 
Integer cantidadCubierta; 

Plan (Integer cantidadCubierta)

{ this.cantidadCubierta=cantidadCubierta; 
} 

public int getValorCubiertoPorPlan() {
    return cantidadCubierta;
}

}