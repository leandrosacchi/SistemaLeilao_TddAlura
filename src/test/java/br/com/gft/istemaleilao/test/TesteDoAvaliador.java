package br.com.gft.istemaleilao.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.gft.istemaleilao.domain.Lance;
import br.com.gft.istemaleilao.domain.Leilao;
import br.com.gft.istemaleilao.domain.Usuario;
import br.com.gft.istemaleilao.service.Avaliador;

public class TesteDoAvaliador {
	
	@Test
	public void deveEntenderLancesEmOrdemCrescente() {
		
		//cenario
		Usuario joao = new Usuario("Joao");
		Usuario jose = new Usuario("José");
		Usuario maria = new Usuario("Maria");

		Leilao leilao = new Leilao("Playstation 4 Novo");
		
		leilao.propoe(new Lance(joao,250.0));
		leilao.propoe(new Lance(jose,300.0));
		leilao.propoe(new Lance(maria,400.0));
		
		//acao
		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);
		
		//validacao
		double maiorEsperado = 400;
		double menorEsperado = 250;
		
		assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.00001);
		assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.00001);
	
	}

}
