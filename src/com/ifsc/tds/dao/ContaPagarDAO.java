package com.ifsc.tds.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ifsc.tds.entity.ContaPagar;
import com.ifsc.tds.entity.Favorecido;
import com.ifsc.tds.entity.TipoConta;
import com.ifsc.tds.entity.Usuario;

public class ContaPagarDAO implements DAO<ContaPagar> {

	private UsuarioDAO usuarioDAO;
	private FavorecidoDAO favorecidoDAO;
	private TipoContaDAO tipoContaDAO;

	public ContaPagarDAO() {
		this.setFavorecidoDAO(new FavorecidoDAO());
		this.setTipoContaDAO(new TipoContaDAO());
		this.setUsuarioDAO(new UsuarioDAO());
	}

	@Override
	public ContaPagar get(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ContaPagar> getAll() {
		List<ContaPagar> contasPagar = new ArrayList<ContaPagar>();

		String sql = "select * from contas_pagar";
		Connection conexao = null;
		PreparedStatement stm = null;

		// Classe que vai recuperar os dados do banco de dados
		ResultSet rset = null;

		try {
			conexao = new Conexao().getConnection();
			stm = conexao.prepareStatement(sql);

			rset = stm.executeQuery();

			// Enquanto existir dados (registros) no banco de dados, recupera
			while (rset.next()) {

				ContaPagar contaPagar = new ContaPagar();
				// Recupera o id do banco e atribui ele ao objeto
				contaPagar.setId(rset.getInt("id"));

				// Recupera a descrição do banco e atribui ele ao objeto
				contaPagar.setDescricao(rset.getString("nome"));

				// Recupera a data de cadastro do banco e atribui ele ao objeto
				contaPagar.setDataCadastro(rset.getDate("data_cadastro"));

				// Recupera a data de vencimento do banco e atribui ele ao objeto
				contaPagar.setDataVencimento(rset.getDate("data_vencimento"));

				// Recupera a data de pagamento do banco e atribui ele ao objeto
				contaPagar.setDataPagamento(rset.getDate("data_pagamento"));

				// Recupera o valor total do banco e atribui ao objeto
				contaPagar.setValorTotal(rset.getDouble("valor_total"));

				// Pesquisa a chave do usuario para atribuir depois o objeto Usuário na conta
				Long id = rset.getLong("usuario_id");
				Usuario usuario = this.getUsuarioDAO().get(id);
				contaPagar.setUsuario(usuario);

				// Pesquisa a chave do favorecido para atribuir depois o objeto Favorecido na
				// conta
				id = rset.getLong("favorecido_id");
				Favorecido favorecido = this.getFavorecidoDAO().get(id);
				contaPagar.setFavorecido(favorecido);

				// Pesquisa a chave do tipo de conta para atribuir depois o objeto Favorecido na
				// conta
				id = rset.getLong("tipo_conta_id");
				TipoConta tipoConta = this.getTipoContaDAO().get(id);
				contaPagar.setTipoConta(tipoConta);

				// Adiciono o contato recuperado, a lista de contatos
				contasPagar.add(contaPagar);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rset != null) {
					rset.close();
				}
				if (stm != null) {
					stm.close();
				}
				if (conexao != null) {
					conexao.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return contasPagar;
	}

	@Override
	public int save(ContaPagar contaPagar) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean update(ContaPagar contaPagar, String[] params) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(ContaPagar contaPagar) {
		// TODO Auto-generated method stub
		return false;
	}

	public UsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}

	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	public FavorecidoDAO getFavorecidoDAO() {
		return favorecidoDAO;
	}

	public void setFavorecidoDAO(FavorecidoDAO favorecidoDAO) {
		this.favorecidoDAO = favorecidoDAO;
	}

	public TipoContaDAO getTipoContaDAO() {
		return tipoContaDAO;
	}

	public void setTipoContaDAO(TipoContaDAO tipoContaDAO) {
		this.tipoContaDAO = tipoContaDAO;
	}

}
