package br.com.gft.istemaleilao.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;

import br.com.gft.istemaleilao.builder.CriadorDeLeilao;
import br.com.gft.istemaleilao.dao.LeilaoDao;
import br.com.gft.istemaleilao.domain.Leilao;
import br.com.gft.istemaleilao.email.Carteiro;

public class EncerradorDeLeilaoTest {
	
	@Test
	public void deveEncerrarLeiloesQueComecaramUmaSemanaAntes() throws Exception {
		Calendar antiga = Calendar.getInstance();
		antiga.set(1999, 1, 20);
		
		Leilao leilao1 = new CriadorDeLeilao().para("Tv 4K").naData(antiga).constroi();
		Leilao leilao2 = new CriadorDeLeilao().para("Geladeira").naData(antiga).constroi();
		List<Leilao> leiloesAntigos = Arrays.asList(leilao1, leilao2);
		
		LeilaoDao daoFalso = mock(LeilaoDao.class);
		
		when(daoFalso.correntes()).thenReturn(leiloesAntigos);
		
		Carteiro carteiroFalso = mock(Carteiro.class);
		
		EncerradorDeLeilao encerrador = new EncerradorDeLeilao(daoFalso, carteiroFalso);
				encerrador.encerra();
				
				assertEquals(2, encerrador.getTotalEncerrados());
				assertTrue(leilao1.isEncerrado());
				assertTrue(leilao2 .isEncerrado());	
	}
	
	@Test
	public void deveAtualizarLeiloesEncerrados() throws Exception {
		Calendar antiga = Calendar.getInstance();
		antiga.set(199, 1, 20);
		
		Leilao leilao1 = new CriadorDeLeilao().para("TV 4K").naData(antiga).constroi();
		
		LeilaoDao daoFalso = mock(LeilaoDao.class);
		
		when(daoFalso.correntes()).thenReturn(Arrays.asList(leilao1));
		
		Carteiro carteiroFalso = mock(Carteiro.class);
		
		EncerradorDeLeilao encerrador = new EncerradorDeLeilao(daoFalso, carteiroFalso);
		encerrador.encerra();
		
		verify(daoFalso, times(1)).atualiza(leilao1);
	}
	
	@Test
	public void deveContinuarExecuçãoMesmoQuandoODaoFalha() throws Exception {
		Calendar antiga = Calendar.getInstance();
		antiga.set(199, 1, 20);
		
		Leilao leilao1 = new CriadorDeLeilao().para("TV 4K").naData(antiga).constroi();
		Leilao leilao2 = new CriadorDeLeilao().para("Geladeira").naData(antiga).constroi();
		
		LeilaoDao daoFalso = mock(LeilaoDao.class);
		Carteiro carteiroFalso = mock(Carteiro.class);
		
		when(daoFalso.correntes()).thenReturn(Arrays.asList(leilao1, leilao2));
		doThrow(new RuntimeException()).when(daoFalso).atualiza(leilao1);
		
		EncerradorDeLeilao encerrador = new EncerradorDeLeilao(daoFalso, carteiroFalso);
		encerrador.encerra();
		
		verify(daoFalso).atualiza(leilao2);
		verify(carteiroFalso).envia(leilao2);
		
		verify(carteiroFalso, times(0)).envia(leilao1);

		
		
	}

}
