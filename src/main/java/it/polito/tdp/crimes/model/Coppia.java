package it.polito.tdp.crimes.model;

public class Coppia {
	
	private String offenseType1;
	private String offenseType2;
	private Integer peso;
	
	public Coppia(String offenseType1, String offenseType2, Integer peso) {
		super();
		this.offenseType1 = offenseType1;
		this.offenseType2 = offenseType2;
		this.peso = peso;
	}

	public String getOffenseType1() {
		return offenseType1;
	}

	public void setOffenseType1(String offenseType1) {
		this.offenseType1 = offenseType1;
	}

	public String getOffenseType2() {
		return offenseType2;
	}

	public void setOffenseType2(String offenseType2) {
		this.offenseType2 = offenseType2;
	}

	public Integer getPeso() {
		return peso;
	}

	public void setPeso(Integer peso) {
		this.peso = peso;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((offenseType1 == null) ? 0 : offenseType1.hashCode());
		result = prime * result + ((offenseType2 == null) ? 0 : offenseType2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coppia other = (Coppia) obj;
		if (offenseType1 == null) {
			if (other.offenseType1 != null)
				return false;
		} else if (!offenseType1.equals(other.offenseType1))
			return false;
		if (offenseType2 == null) {
			if (other.offenseType2 != null)
				return false;
		} else if (!offenseType2.equals(other.offenseType2))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.offenseType1 + " " + this.offenseType2 + " " + this.peso;
	}
	
	
	
}
