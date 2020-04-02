package br.com.gft.istemaleilao.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class LeilaoTest {
	
	@Test
	public void deveReceberUmLance() throws Exception {
		Leilao leilao = new Leilao("Macbook Pro");
		assertEquals(0, leilao.getLances().size());
		
		leilao.propoe(new Lance(new Usuario("Steve Jobs"), 2000.0));
		
		assertEquals(1, leilao.getLances().size());
		assertEquals(2000.0, leilao.getLances().get(0).getValor(), 0.0001);
		 	
	}
	
	@Test
	public void deveReceberVariosLances() throws Exception {
		Leilao leilao = new Leilao("Macbook Pro");
		assertEquals(0, leilao.getLances().size());
		
		leilao.propoe(new Lance(new Usuario("Steve Jobs"), 2000.0));
		leilao.propoe(new Lance(new Usuario("Steve Wozniak"), 3000.0));

		
		assertEquals(2, leilao.getLances().size());
		assertEquals(2000.0, leilao.getLances().get(0).getValor(), 0.0001);
		assertEquals(3000.0, leilao.getLances().get(1).getValor(), 0.0001);

		 	
	}
	
	@Test
	public void naoDeveAcceitarDoisLancesSeguidosDoMesmoUsuario() throws Exception {
		Leilao leilao = new Leilao("Macbook Pro");
		Usuario steve = new Usuario ("Steve Jovs");
		
		leilao.propoe(new Lance(steve, 2000.0));
		leilao.propoe(new Lance(steve, 3000.0));
		
		assertEquals(1, leilao.getLances().size());		
		assertEquals(2000.0, leilao.getLances().get(0).getValor(), 0.0001);

	}
	@Test
	public void naoDeveAcceitarMaisDoQueCincoLancesDeUmMesmoUsuario() throws Exception {
		Leilao leilao = new Leilao("Macbook Pro");
		Usuario steve = new Usuario ("Steve Jobs");
		Usuario bill = new Usuario ("Bill Gates");

		
		leilao.propoe(new Lance(steve, 2000.0));
		leilao.propoe(new Lance(bill, 3000.0));
		leilao.propoe(new Lance(steve, 4000.0));
		leilao.propoe(new Lance(bill, 5000.0));
		leilao.propoe(new Lance(steve, 6000.0));
		leilao.propoe(new Lance(bill, 7000.0));
		leilao.propoe(new Lance(steve, 8000.0));
		leilao.propoe(new Lance(bill, 9000.0));
		leilao.propoe(new Lance(steve, 10000.0));
		leilao.propoe(new Lance(bill, 11000.0));
		
		//deve ser ignorado
		leilao.propoe(new Lance(steve, 12000.0));
		
		assertEquals(10, leilao.getLances().size());		
		assertEquals(11000.0, leilao.getLances().get(9).getValor(), 0.0001);
		
	}

}
