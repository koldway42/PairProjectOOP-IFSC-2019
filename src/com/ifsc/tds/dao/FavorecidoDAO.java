package com.ifsc.tds.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ifsc.tds.entity.Favorecido;
import com.ifsc.tds.entity.TipoConta;

public class FavorecidoDAO implements DAO<Favorecido> {

	@Override
	public Favorecido get(Long id) {
		Favorecido favorecido = null;
		String sql = "select * from favorecido where id = ?";

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

				favorecido = new Favorecido();
				// Recupera o id do banco e atribui ele ao objeto
				favorecido.setId(rset.getInt("id"));

				// Recupera o nome do banco e atribui ele ao objeto
				favorecido.setNome(rset.getString("nome"));

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
		return favorecido;
	}

	@Override
	public List<Favorecido> getAll() {
		List<Favorecido> favorecidos = new ArrayList<Favorecido>();

		String sql = "select * from favorecido";
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

				Favorecido favorecido = new Favorecido();
				// Recupera o id do banco e atribui ele ao objeto
				favorecido.setId(rset.getInt("id"));

				// Recupera o nome do banco e atribui ele ao objeto
				favorecido.setNome(rset.getString("nome"));

				// Adiciono o contato recuperado, a lista de contatos
				favorecidos.add(favorecido);
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
		return favorecidos;
	}

	@Override
	public int save(Favorecido favorecido) {
		String sql = "insert into favorecido (nome)" + " values (?)";

		Connection conexao = null;
		PreparedStatement stm = null;

		try {
			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);

			stm.setString(1, favorecido.getNome());

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
	public boolean update(Favorecido favorecido, String[] params) {
		String sql = "update favorecido set nome = ? " + "where id = ?";

		Connection conexao = null;
		PreparedStatement stm = null;

		try {
			// Recupera uma conexão com o banco
			conexao = new Conexao().getConnection();

			// Cria uma instância de PreparedStatment, classe usada para executar a operação
			// SQL (query)
			stm = conexao.prepareStatement(sql);

			stm.setString(1, favorecido.getNome());
			stm.setInt(2, favorecido.getId());

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
	public boolean delete(Favorecido favorecido) {
		String sql = "delete from favorecido where id = ?";

		// Recupera uma conexão com o banco
		Connection conexao = null;
		// Cria uma instância de PreparedStatment, classe usada para executar a operação
		// SQL (query)
		PreparedStatement stm = null;

		try {
			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			stm.setInt(1, favorecido.getId());
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
