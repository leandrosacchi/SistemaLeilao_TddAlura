package br.com.gft.istemaleilao.watch;

import java.util.Calendar;

public class RelogioDoSistema implements Relogio{

	public Calendar hoje() {
		return Calendar.getInstance();
	}

}
