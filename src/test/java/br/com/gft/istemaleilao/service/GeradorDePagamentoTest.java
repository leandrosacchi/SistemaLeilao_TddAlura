package br.com.gft.istemaleilao.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Calendar;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import br.com.gft.istemaleilao.builder.CriadorDeLeilao;
import br.com.gft.istemaleilao.dao.LeilaoDao;
import br.com.gft.istemaleilao.dao.PagamentoDao;
import br.com.gft.istemaleilao.domain.Leilao;
import br.com.gft.istemaleilao.domain.Pagamento;
import br.com.gft.istemaleilao.domain.Usuario;
import br.com.gft.istemaleilao.watch.Relogio;

public class GeradorDePagamentoTest {
	
	@Test
	public void deveGerarPagamentoParaUmLeilaoEncerrado() throws Exception {
		LeilaoDao leiloes = mock(LeilaoDao.class);
		PagamentoDao pagamentos = mock (PagamentoDao.class);
		Avaliador avaliador = mock(Avaliador.class);
		
		Leilao leilao = new CriadorDeLeilao().para("Playstation 4")
				.lance(new Usuario("José da Silva"), 2000.0)
				.lance(new Usuario("Maria Pereira"), 2500.0)
				.constroi();
		
		when(leiloes.encerrados()).thenReturn(Arrays.asList(leilao));
		when(avaliador.getMaiorLance()).thenReturn(2500.0);
		
		GeradorDePagamento gerador = new GeradorDePagamento(leiloes, pagamentos, avaliador);
		gerador.gera();
		
		ArgumentCaptor<Pagamento> argumento = ArgumentCaptor.forClass(Pagamento.class);
		verify(pagamentos).salva(argumento.capture());
		 
		Pagamento pagamentoGerado = argumento.getValue();
		
		assertEquals(2500.0, pagamentoGerado.getValor(), 0.00001);
	}
	
	@Test
	public void deveEmpurrarParaOProximoDiaUtil() throws Exception {
		LeilaoDao leiloes = mock(LeilaoDao.class);
		PagamentoDao pagamentos = mock (PagamentoDao.class);
		Relogio relogio = mock(Relogio.class);
		
		Leilao leilao = new CriadorDeLeilao().para("Playstation 4")
				.lance(new Usuario("José da Silva"), 2000.0)
				.lance(new Usuario("Maria Pereira"), 2500.0)
				.constroi();
		
		when(leiloes.encerrados()).thenReturn(Arrays.asList(leilao));
		
		Calendar sabado = Calendar.getInstance();
		sabado.set(2020, Calendar.APRIL, 4);
		
		when(relogio.hoje()).thenReturn(sabado);
		
		GeradorDePagamento gerador = new GeradorDePagamento(leiloes, pagamentos, new Avaliador(), relogio);
		gerador.gera();
		
		ArgumentCaptor<Pagamento> argumento = ArgumentCaptor.forClass(Pagamento.class);
		verify(pagamentos).salva(argumento.capture());
		 
		Pagamento pagamentoGerado = argumento.getValue();
		
		assertEquals(Calendar.MONDAY, pagamentoGerado.getData().get(Calendar.DAY_OF_WEEK));
		assertEquals(6, pagamentoGerado.getData().get(Calendar.DAY_OF_MONTH));
				
	}

}
