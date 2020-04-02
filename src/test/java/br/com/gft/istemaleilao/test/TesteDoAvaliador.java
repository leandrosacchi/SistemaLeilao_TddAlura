package br.com.gft.istemaleilao.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

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
	
	@Test
	public void deveEntenderLancesEmOrdemDecrescente() {
		
		//cenario
		Usuario joao = new Usuario("Joao");
		Usuario jose = new Usuario("José");
		Usuario maria = new Usuario("Maria");

		Leilao leilao = new Leilao("Playstation 4 Novo");
		
		leilao.propoe(new Lance(joao,3000.0));
		leilao.propoe(new Lance(jose,2000.0));
		leilao.propoe(new Lance(maria, 1000.0));
		
		//acao
		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);
		
		//validacao
		double maiorEsperado = 3000;
		double menorEsperado = 1000;
		
		assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.00001);
		assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.00001);
	
	}
	
	@Test
	public void deveEntenderLeilaoComApenasUmLance() {
		
		//cenario
		Usuario joao = new Usuario("Joao");
	
		Leilao leilao = new Leilao("Playstation 4 Novo");
		
		leilao.propoe(new Lance(joao,1000.0));
				
		//acao
		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);
		
		//validacao
		assertEquals(1000.0, leiloeiro.getMaiorLance(), 0.00001);
		assertEquals(1000.0, leiloeiro.getMenorLance(), 0.00001);
	
	}

	@Test
	public void deveEncontrarOsTresMaioresLances() {
		
		//cenario
		Usuario joao = new Usuario("Joao");
		Usuario maria = new Usuario("Maria");

	
		Leilao leilao = new Leilao("Playstation 4 Novo");
		
		leilao.propoe(new Lance(joao,100.0));
		leilao.propoe(new Lance(maria,200.0));
		leilao.propoe(new Lance(joao,300.0));
		leilao.propoe(new Lance(maria,400.0));
				
		//acao
		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);
		
		//validacao
		List<Lance> maiores = leiloeiro.getTresMaiores();
		assertEquals(3, maiores.size());
		assertEquals(400.0, maiores.get(0).getValor(), 0.0001);
		assertEquals(300.0, maiores.get(1).getValor(), 0.0001);
		assertEquals(200.0, maiores.get(2).getValor(), 0.0001);

	}
	
}
