package br.com.gft.istemaleilao.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import br.com.gft.istemaleilao.builder.CriadorDeLeilao;
import br.com.gft.istemaleilao.dao.LeilaoDao;
import br.com.gft.istemaleilao.dao.PagamentoDao;
import br.com.gft.istemaleilao.domain.Leilao;
import br.com.gft.istemaleilao.domain.Pagamento;
import br.com.gft.istemaleilao.domain.Usuario;

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

}
