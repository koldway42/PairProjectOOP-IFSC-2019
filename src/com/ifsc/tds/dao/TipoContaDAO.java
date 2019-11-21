package com.ifsc.tds.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ifsc.tds.entity.TipoConta;
import com.ifsc.tds.entity.Usuario;

public class TipoContaDAO implements DAO<TipoConta> {

	@Override
	public TipoConta get(Long id) {
		TipoConta tipoConta = null;
		String sql = "select * from tipo_conta where id = ?";

		// Recupera uma conexão com o banco
		Connection conexao = null;
		// Cria uma instância de PreparedStatment, classe usada para executar a operação
		// SQL (query)
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

				tipoConta = new TipoConta();
				// Recupera o id do banco e atribui ele ao objeto
				tipoConta.setId(rset.getInt("id"));

				// Recupera o nome do banco e atribui ele ao objeto
				tipoConta.setNome(rset.getString("nome"));

				// Recupera o login do banco e atribui ele ao objeto
				tipoConta.setStatus(rset.getInt("status"));

			}
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
		return tipoConta;
	}

	@Override
	public List<TipoConta> getAll() {
		List<TipoConta> tiposConta = new ArrayList<TipoConta>();

		String sql = "select * from tipo_conta";
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

				TipoConta tipoConta = new TipoConta();
				// Recupera o id do banco e atribui ele ao objeto
				tipoConta.setId(rset.getInt("id"));

				// Recupera o nome do banco e atribui ele ao objeto
				tipoConta.setNome(rset.getString("nome"));

				// Recupera o login do banco e atribui ele ao objeto
				tipoConta.setStatus(rset.getInt("status"));

				// Adiciono o contato recuperado, a lista de contatos
				tiposConta.add(tipoConta);
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
		return tiposConta;
	}

	@Override
	public int save(TipoConta tipoConta) {
		String sql = "insert into tipo_conta (nome, status)" + " values (?, ?)";

		Connection conexao = null;
		PreparedStatement stm = null;

		try {
			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);

			stm.setString(1, tipoConta.getNome());
			stm.setInt(2, tipoConta.getStatus());

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
				return 1;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	@Override
	public boolean update(TipoConta tipoConta, String[] params) {
		String sql = "update tipo_conta set nome = ?, status = ?" + " where id = ?";

		Connection conexao = null;
		PreparedStatement stm = null;

		try {
			// Recupera uma conexão com o banco
			conexao = new Conexao().getConnection();

			// Cria uma instância de PreparedStatment, classe usada para executar a operação
			// SQL (query)
			stm = conexao.prepareStatement(sql);

			stm.setString(1, tipoConta.getNome());
			stm.setInt(2, tipoConta.getStatus());
			stm.setInt(3, tipoConta.getId());

			// Executa a sql para inserção dos dados
			stm.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Fecha as conexões
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

	@Override
	public boolean delete(TipoConta tipoConta) {
		String sql = "delete from tipo_conta where id = ?";

		// Recupera uma conexão com o banco
		Connection conexao = null;
		// Cria uma instância de PreparedStatment, classe usada para executar a operação
		// SQL (query)
		PreparedStatement stm = null;

		try {
			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			stm.setInt(1, tipoConta.getId());
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

}
