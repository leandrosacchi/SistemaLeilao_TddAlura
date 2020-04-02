package br.com.gft.istemaleilao.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.gft.istemaleilao.builder.CriadorDeLeilao;
import br.com.gft.istemaleilao.domain.Lance;
import br.com.gft.istemaleilao.domain.Leilao;
import br.com.gft.istemaleilao.domain.Usuario;

public class AvaliadorTest {

	private Avaliador leiloeiro;
	private Usuario joao;
	private Usuario jose;
	private Usuario maria;

	@Before
	public void setup() {
		this.leiloeiro = new Avaliador();
		this.joao = new Usuario("Joao");
		this.jose = new Usuario("José");
		this.maria = new Usuario("Maria");
	}

	@Test
	public void deveEntenderLancesEmOrdemCrescente() {

		// cenario
		Leilao leilao = new CriadorDeLeilao().para("Playstation 4 Novo").lance(joao, 250.0).lance(jose, 300.0)
				.lance(maria, 400.0).constroi();

		// acao
		leiloeiro.avalia(leilao);

		// validacao
//		assertEquals(400.0, leiloeiro.getMaiorLance(), 0.00001);
		assertThat(leiloeiro.getMaiorLance(), equalTo(400.0));
//		assertEquals(250.0, leiloeiro.getMenorLance(), 0.00001);
		assertThat(leiloeiro.getMenorLance(), equalTo(250.0));


	}

	@Test
	public void deveEntenderLancesEmOrdemDecrescente() {

		// cenario
		Leilao leilao = new CriadorDeLeilao().para("Playstation 4 Novo").lance(joao, 3000.0).lance(jose, 2000.0)
				.lance(maria, 1000.0).constroi();

		// acao
		leiloeiro.avalia(leilao);

		// validacao
	//	assertEquals(3000.0, leiloeiro.getMaiorLance(), 0.00001);
		assertThat(leiloeiro.getMaiorLance(), equalTo(3000.0));
	//	assertEquals(1000.0, leiloeiro.getMenorLance(), 0.00001);
		assertThat(leiloeiro.getMenorLance(), equalTo(1000.0));


	}

	@Test
	public void deveEntenderLeilaoComApenasUmLance() {

		// cenario
		Leilao leilao = new CriadorDeLeilao().para("Playstation 4 Novo").lance(joao, 1000.0).constroi();

		// acao
		leiloeiro.avalia(leilao);

		// validacao
//		assertEquals(1000.0, leiloeiro.getMaiorLance(), 0.00001);
		assertThat(leiloeiro.getMaiorLance(), equalTo(1000.0));
//		assertEquals(1000.0, leiloeiro.getMenorLance(), 0.00001);
		assertThat(leiloeiro.getMenorLance(), equalTo(1000.0));


	}

	@Test
	public void deveEncontrarOsTresMaioresLances() {
		// cenario
		Leilao leilao = new CriadorDeLeilao().para("Paystation 4 Novo").lance(joao, 100.0).lance(maria, 200.0)
				.lance(joao, 300.0).lance(maria, 400.0).constroi();

		// acao
		leiloeiro.avalia(leilao);

		// validacao
		List<Lance> maiores = leiloeiro.getTresMaiores();
		assertEquals(3, maiores.size());
		assertThat(maiores, hasItems(
				new Lance(maria, 400.0),
				new Lance(joao, 300.0),
				new Lance(maria, 200.0)
				));
		
//		assertEquals(400.0, maiores.get(0).getValor(), 0.0001);
//		assertEquals(300.0, maiores.get(1).getValor(), 0.0001);
//		assertEquals(200.0, maiores.get(2).getValor(), 0.0001);

	}

	@Test(expected = RuntimeException.class)
	public void naoDeveAvaliarLeiloesSemNenhumLanceDado() throws Exception {
		// cenario
		Leilao leilao = new CriadorDeLeilao().para("Playstation 4 novo").constroi();
		
		// acao
		leiloeiro.avalia(leilao);
				
	}
}
