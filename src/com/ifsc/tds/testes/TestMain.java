package com.ifsc.tds.testes;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.w3c.dom.UserDataHandler;

import com.ifsc.tds.dao.ContaPagarDAO;
import com.ifsc.tds.dao.ContaReceberDAO;
import com.ifsc.tds.dao.FavorecidoDAO;
import com.ifsc.tds.dao.TipoContaDAO;
import com.ifsc.tds.entity.ContaPagar;
import com.ifsc.tds.entity.ContaReceber;
import com.ifsc.tds.entity.Favorecido;
import com.ifsc.tds.entity.TipoConta;
import com.ifsc.tds.entity.Usuario;

public class TestMain {

	public static void main(String[] args) {
		
		Favorecido favorecido = new Favorecido();
		favorecido.setId(9);
		
		Usuario usuario = new Usuario();
		usuario.setId(39);
		
		TipoConta tipoConta = new TipoConta();
		tipoConta.setId(1);
		
		ContaReceber contaReceber = new ContaReceber();
		
		contaReceber.setId(1);
		contaReceber.setDescricao("RP do LOL que eu roubei");
		contaReceber.setDataCadastro(Date.valueOf(LocalDate.now()));
		contaReceber.setDataVencimento(Date.valueOf(LocalDate.now()));
		contaReceber.setDataPagamento(Date.valueOf(LocalDate.now()));
		contaReceber.setValorTotal(666.99);
		
		contaReceber.setUsuario(usuario);
		contaReceber.setTipoConta(tipoConta);
		contaReceber.setFavorecido(favorecido);
		
		ContaReceberDAO contaReceberDAO = new ContaReceberDAO();
		
		contaReceberDAO.update(contaReceber, args);
		
	}

}
