package it.polito.tdp.extflightdelays.model;

public class AeroportoPeso implements Comparable<AeroportoPeso>{
	
	private Integer aeroporto;
	private Double peso;
	public AeroportoPeso(Integer aeroporto, Double peso) {
		super();
		this.aeroporto = aeroporto;
		this.peso = peso;
	}
	public Integer getAeroporto() {
		return aeroporto;
	}
	public Double getPeso() {
		return peso;
	}
	@Override
	public int compareTo(AeroportoPeso o) {
		return peso.compareTo(o.peso);
	}
	

}
