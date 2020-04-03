package br.com.gft.istemaleilao.service;

import java.util.Calendar;
import java.util.List;

import br.com.gft.istemaleilao.dao.LeilaoDao;
import br.com.gft.istemaleilao.dao.PagamentoDao;
import br.com.gft.istemaleilao.domain.Leilao;
import br.com.gft.istemaleilao.domain.Pagamento;
import br.com.gft.istemaleilao.watch.Relogio;
import br.com.gft.istemaleilao.watch.RelogioDoSistema;

public class GeradorDePagamento {
	
	private final LeilaoDao leiloes;
	private final Avaliador avaliador;
	private final PagamentoDao pagamentos;
	private final Relogio relogio;

	public GeradorDePagamento(LeilaoDao leiloes, PagamentoDao pagamentos, Avaliador avaliador, Relogio relogio){
		this.leiloes = leiloes;
		this.avaliador = avaliador;
		this.pagamentos=pagamentos;
		this.relogio = relogio;
	}
	
	public GeradorDePagamento(LeilaoDao leiloes, PagamentoDao pagamentos, Avaliador avaliador){
		this (leiloes, pagamentos, avaliador, new RelogioDoSistema());
	}
	
	public void gera() {
		List<Leilao> leiloesEncerrados = this.leiloes.encerrados();
		
		for(Leilao leilao : leiloesEncerrados) {
			this.avaliador.avalia(leilao);
			
			Pagamento novoPagamento = new Pagamento(avaliador.getMaiorLance(), primeiroDiaUtil());
			this.pagamentos.salva(novoPagamento);
		}
	}

	private Calendar primeiroDiaUtil() {
		Calendar data = relogio.hoje();
		int diaDaSemana = data.get(Calendar.DAY_OF_WEEK);
		
		if (diaDaSemana == Calendar.SATURDAY) data.add(Calendar.DAY_OF_MONTH, 2);
		else if (diaDaSemana == Calendar.SUNDAY) data.add(Calendar.DAY_OF_MONTH, 1);
		return data;
	}

}
