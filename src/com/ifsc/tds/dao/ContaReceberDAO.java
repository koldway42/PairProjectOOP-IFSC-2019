package com.ifsc.tds.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ifsc.tds.entity.*;

public class ContaReceberDAO implements DAO<ContaReceber>{

	private UsuarioDAO usuarioDAO;
	private FavorecidoDAO favorecidoDAO;
	private TipoContaDAO tipoContaDAO;
	
	public ContaReceberDAO() {
		this.setFavorecidoDAO(new FavorecidoDAO());
		this.setTipoContaDAO(new TipoContaDAO());
		this.setUsuarioDAO(new UsuarioDAO());
	}
	
	@Override
	public ContaReceber get(Long id) {
		ContaReceber contaReceber = null;
		String sql = "select * from contas_receber where id = ?";
		Connection conexao = null;
		PreparedStatement stm = null;

		// Classe que vai recuperar os dados do banco de dados
		ResultSet rset = null;

		try {
			conexao = new Conexao().getConnection();
			stm = conexao.prepareStatement(sql);
			
			stm.setInt(1, id.intValue());
			
			rset = stm.executeQuery();

			// Enquanto existir dados (registros) no banco de dados, recupera
			while (rset.next()) {

				contaReceber = new ContaReceber();
				// Recupera o id do banco e atribui ele ao objeto
				contaReceber.setId(rset.getInt("id"));

				// Recupera a descrição do banco e atribui ele ao objeto
				contaReceber.setDescricao(rset.getString("descricao"));

				// Recupera a data de cadastro do banco e atribui ele ao objeto
				contaReceber.setDataCadastro(rset.getDate("data_cadastro"));

				// Recupera a data de vencimento do banco e atribui ele ao objeto
				contaReceber.setDataVencimento(rset.getDate("data_vencimento"));

				// Recupera a data de pagamento do banco e atribui ele ao objeto
				contaReceber.setDataPagamento(rset.getDate("data_pagamento"));

				// Recupera o valor total do banco e atribui ao objeto
				contaReceber.setValorTotal(rset.getDouble("valor_total"));

				// Pesquisa a chave do usuario para atribuir depois o objeto Usuário na conta
				id = rset.getLong("usuario_id");
				Usuario usuario = this.getUsuarioDAO().get(id);
				contaReceber.setUsuario(usuario);

				// Pesquisa a chave do favorecido para atribuir depois o objeto Favorecido na
				// conta
				id = rset.getLong("favorecido_id");
				Favorecido favorecido = this.getFavorecidoDAO().get(id);
				contaReceber.setFavorecido(favorecido);

				// Pesquisa a chave do tipo de conta para atribuir depois o objeto Favorecido na
				// conta
				id = rset.getLong("tipo_conta_id");
				TipoConta tipoConta = this.getTipoContaDAO().get(id);
				contaReceber.setTipoConta(tipoConta);
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
		return contaReceber;


	}

	@Override
	public List<ContaReceber> getAll() {
		List<ContaReceber> contasReceber = new ArrayList<ContaReceber>();

		String sql = "select * from contas_receber";
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

				ContaReceber contaReceber = new ContaReceber();
				// Recupera o id do banco e atribui ele ao objeto
				contaReceber.setId(rset.getInt("id"));

				// Recupera a descrição do banco e atribui ele ao objeto
				contaReceber.setDescricao(rset.getString("descricao"));

				// Recupera a data de cadastro do banco e atribui ele ao objeto
				contaReceber.setDataCadastro(rset.getDate("data_cadastro"));

				// Recupera a data de vencimento do banco e atribui ele ao objeto
				contaReceber.setDataVencimento(rset.getDate("data_vencimento"));

				// Recupera a data de pagamento do banco e atribui ele ao objeto
				contaReceber.setDataPagamento(rset.getDate("data_pagamento"));

				// Recupera o valor total do banco e atribui ao objeto
				contaReceber.setValorTotal(rset.getDouble("valor_total"));

				// Pesquisa a chave do usuario para atribuir depois o objeto Usuário na conta
				Long id = rset.getLong("usuario_id");
				Usuario usuario = this.getUsuarioDAO().get(id);
				contaReceber.setUsuario(usuario);

				// Pesquisa a chave do favorecido para atribuir depois o objeto Favorecido na
				// conta
				id = rset.getLong("favorecido_id");
				Favorecido favorecido = this.getFavorecidoDAO().get(id);
				contaReceber.setFavorecido(favorecido);

				// Pesquisa a chave do tipo de conta para atribuir depois o objeto Favorecido na
				// conta
				id = rset.getLong("tipo_conta_id");
				TipoConta tipoConta = this.getTipoContaDAO().get(id);
				contaReceber.setTipoConta(tipoConta);

				// Adiciono o contato recuperado, a lista de contatos
				contasReceber.add(contaReceber);
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
		return contasReceber;

	}

	@Override
	public int save(ContaReceber contaReceber) {
		String sql = "insert into contas_receber"
				+ "(descricao, data_cadastro, data_vencimento, data_pagamento,"
				+ " valor_total, usuario_id, favorecido_id, tipo_conta_id)"
				+ "values(?, ?, ?, ?, ?, ?, ?, ?)";
		Connection conexao = null;
		PreparedStatement stm = null;

		try {
			conexao = new Conexao().getConnection();
			stm = conexao.prepareStatement(sql);
			
			stm.setString(1, contaReceber.getDescricao());
			stm.setDate(2, contaReceber.getDataCadastro());
			stm.setDate(3, contaReceber.getDataVencimento());
			stm.setDate(4, contaReceber.getDataPagamento());
			stm.setDouble(5, contaReceber.getValorTotal());
			Usuario usuario = contaReceber.getUsuario();
			stm.setLong(6, usuario.getId());
			Favorecido favorecido = contaReceber.getFavorecido();
			stm.setLong(7, favorecido.getId());
			TipoConta tipoConta = contaReceber.getTipoConta();
			stm.setLong(8, tipoConta.getId());			
			
			stm.execute();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
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
		return 0;

	}

	@Override
	public boolean update(ContaReceber contaReceber, String[] params) {
		String sql = "update contas_receber set "
				+ "descricao = ?, data_cadastro = ? , data_vencimento = ?, data_pagamento = ?,"
				+ " valor_total = ?, usuario_id = ?, favorecido_id = ?, tipo_conta_id = ? " + 
				"where id = ?";
		Connection conexao = null;
		PreparedStatement stm = null;

		try {
			conexao = new Conexao().getConnection();
			stm = conexao.prepareStatement(sql);
			
			stm.setString(1, contaReceber.getDescricao());
			stm.setDate(2, contaReceber.getDataCadastro());
			stm.setDate(3, contaReceber.getDataVencimento());
			stm.setDate(4, contaReceber.getDataPagamento());
			stm.setDouble(5, contaReceber.getValorTotal());
			Usuario usuario = contaReceber.getUsuario();
			stm.setLong(6, usuario.getId());
			Favorecido favorecido = contaReceber.getFavorecido();
			stm.setLong(7, favorecido.getId());
			TipoConta tipoConta = contaReceber.getTipoConta();
			stm.setLong(8, tipoConta.getId());		
			stm.setInt(9, contaReceber.getId());
			
			stm.execute();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
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
		return false;
	}

	@Override
	public boolean delete(ContaReceber contaReceber) {
		String sql = "delete from contas_receber where id = ?";

		// Recupera uma conexão com o banco
		Connection conexao = null;
		// Cria uma instância de PreparedStatment, classe usada para executar a operação
		// SQL (query)
		PreparedStatement stm = null;

		try {
			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			stm.setInt(1, contaReceber.getId());
			stm.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stm != null) {
					stm.close();
				}
				if (conexao != null) {
					conexao.close();
				}
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
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
