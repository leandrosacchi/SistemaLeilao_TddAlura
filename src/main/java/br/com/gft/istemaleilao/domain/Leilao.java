package br.com.gft.istemaleilao.domain;

import java.util.ArrayList;
import java.util.List;

public class Leilao {
	
	private String descricao;
	private List<Lance> lances;
	
	public Leilao(String descricao) {
		this.descricao = descricao;
		this.lances = new ArrayList<Lance>();
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Lance> getLances() {
		return lances;
	}

	public void setLances(List<Lance> lances) {
		this.lances = lances;
	}
	
	public void propoe(Lance lance) {
		lances.add(lance);
	}
	
	

}
