package br.com.gft.istemaleilao.service;

import java.util.Calendar;
import java.util.List;

import br.com.gft.istemaleilao.dao.LeilaoDao;
import br.com.gft.istemaleilao.dao.PagamentoDao;
import br.com.gft.istemaleilao.domain.Leilao;
import br.com.gft.istemaleilao.domain.Pagamento;

public class GeradorDePagamento {
	
	private final LeilaoDao leiloes;
	private final Avaliador avaliador;
	private final PagamentoDao pagamentos;

	public GeradorDePagamento(LeilaoDao leiloes, PagamentoDao pagamentos, Avaliador avaliador){
		this.leiloes = leiloes;
		this.avaliador = avaliador;
		this.pagamentos=pagamentos;
	}
	
	public void gera() {
		List<Leilao> leiloesEncerrados = this.leiloes.encerrados();
		
		for(Leilao leilao : leiloesEncerrados) {
			this.avaliador.avalia(leilao);
			
			Pagamento novoPagamento = new Pagamento(avaliador.getMaiorLance(), Calendar.getInstance());
			this.pagamentos.salva(novoPagamento);
		}
	}

}
