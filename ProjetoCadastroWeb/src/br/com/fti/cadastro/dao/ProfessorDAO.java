package br.com.fti.cadastro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import br.com.fti.cadastro.controller.UtilController;
import br.com.fti.cadastro.model.Aluno;
import br.com.fti.cadastro.model.Funcionario;
import br.com.fti.cadastro.model.Pessoa;
import br.com.fti.cadastro.model.Professor;

@Repository
public class ProfessorDAO {
	
	private final Connection con;
	private int codigoAtual;
	
	public ProfessorDAO(DataSource dataSource) {
		try {
			this.con = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void cadastrarFuncionario(Funcionario funcionario) {
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("INSERT INTO funcionarios (nome, cpf, sexo, datanascimento, endereço, cargo, disciplina, salario, vale_alimentacao, vale_refeicao, vale_transporte,"
				+ " telefone, email, numero_filhos, ativo)");
		sql.append("VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		try {
			con.setAutoCommit(false);

			PreparedStatement stmt = con.prepareStatement(sql.toString());	

			java.sql.Date d = new java.sql.Date(funcionario.getDataNascimento().getTime());
			
			stmt.setString(1, String.valueOf(funcionario.getNome()));
			stmt.setString(2, String.valueOf(funcionario.getCpf()));
			stmt.setString(3, String.valueOf(funcionario.getSexo()));
			stmt.setDate(4, d);
			stmt.setString(5, String.valueOf(funcionario.getEndereco()));
			stmt.setString(6, String.valueOf(funcionario.getCargo()));
			
			if (funcionario instanceof Professor){
				stmt.setString(7, String.valueOf(((Professor)funcionario).getDisciplina()));
			} else {
				stmt.setNull(7, Types.NULL);
			}
			
			stmt.setBigDecimal(8, funcionario.getSalario());
			stmt.setBigDecimal(9, funcionario.getValeAlimentacao());
			stmt.setBigDecimal(10, funcionario.getValeRefeicao());
			stmt.setBigDecimal(11, funcionario.getValeTransporte());
			stmt.setString(12, String.valueOf(funcionario.getTelefone()));
			stmt.setString(13, String.valueOf(funcionario.getEmail()));
			stmt.setInt(14, Integer.valueOf(funcionario.getFilhos()));
			stmt.setInt(15, 1);
			
			stmt.execute();
			con.commit();
			
			String sqlLastInsert = "SELECT LAST_INSERT_ID()";
			
			stmt.close();
			
			stmt = con.prepareStatement(sqlLastInsert);
			
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				funcionario.setCadastro(rs.getLong(1));
				registrarFilhos(funcionario);
				stmt.close();
			}

			stmt.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void registrarFilhos(Funcionario funcionario) {
		String sql = "INSERT INTO filhos (fk_codigo, nome, datanascimento) VALUES (?, ?, ?); ";

		try {
			con.setAutoCommit(false);

			PreparedStatement stmt = con.prepareStatement(sql);
			
			for (Pessoa filho : funcionario.getListaFilhos()) {
				java.sql.Date d = new java.sql.Date(filho.getDataNascimento().getTime());
				
				stmt.setLong(1, funcionario.getCadastro());
				stmt.setString(2, String.valueOf(filho.getNome()));
				stmt.setDate(3, d);
				
				stmt.addBatch();
			}
			
			stmt.executeBatch();
			
			con.commit();

			stmt.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Aluno consultarPorCodigo(Long cadastro){
		String sql = "SELECT nome, cpf, sexo, datanascimento, endereço, curso, telefone, email "
				+ "FROM alunos WHERE ativo = 1 AND matricula = ? "
				+ "ORDER BY matricula ASC";
		
		Aluno aluno = new Aluno();
		
		try {
			PreparedStatement stmt = con.prepareStatement(sql.toString());
			
			stmt.setLong(1, cadastro);
			
			ResultSet rs = stmt.executeQuery();
	
			if (rs.next()) {				
				aluno.setMatricula(cadastro);
				aluno.setNome(rs.getString(1));
				aluno.setCpf(rs.getString(2));
				aluno.setSexo(rs.getString(3));
				aluno.setDataNascimento(new Date(rs.getTimestamp("datanascimento").getTime()));
				aluno.setEndereco(rs.getString(5));
				aluno.setCurso(rs.getString(6));
				aluno.setTelefone(rs.getString(7));
				aluno.setEmail(rs.getString(8));

			}
			
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return aluno;
	}
	
	public ArrayList<Funcionario> listaFuncionario() {

		ArrayList<Funcionario> listaFunc = new ArrayList<Funcionario>();
		
		String sql = "SELECT FUNC.codigo, FUNC.nome, FUNC.cpf, FUNC.sexo, FUNC.datanascimento, FUNC.endereço, FUNC.cargo, FUNC.disciplina, FUNC.salario, "
				+ "FUNC.vale_alimentacao, FUNC.vale_refeicao, FUNC.vale_transporte, FUNC.telefone, FUNC.email, FUNC.numero_filhos, FILHO.nome, FILHO.datanascimento"
				+ " FROM funcionarios AS FUNC LEFT JOIN filhos AS FILHO ON FUNC.codigo = FILHO.fk_codigo "
				+ "WHERE FUNC.ativo = 1 ORDER BY codigo ASC";

		try {
			PreparedStatement stmt = con.prepareStatement(sql.toString());

			ResultSet rs = stmt.executeQuery();
			
			Funcionario func = new Funcionario();
			ArrayList<Pessoa> arrayFilhos = new ArrayList<Pessoa>();
			codigoAtual = 0;
			
			while (rs.next()) {
				if (codigoAtual == rs.getInt(1)) {
					Pessoa filho = new Pessoa();
					filho.setNome(rs.getString(16));
					filho.setDataNascimento(new Date(rs.getTimestamp("FILHO.datanascimento").getTime()));
					arrayFilhos.add(filho);
				} else {
					if (rs.getRow() != 1) {
						func.setListaFilhos(arrayFilhos);
						listaFunc.add(func);
					}
					codigoAtual = rs.getInt(1);
					
					if (rs.getString(7).equals("Professor")){func = new Professor();} else {func = new Funcionario();}
					
					func.setCadastro(rs.getLong(1));
					func.setNome(rs.getString(2));
					func.setCpf(rs.getString(3));
					func.setSexo(rs.getString(4));
					func.setDataNascimento(new Date(rs.getTimestamp("FUNC.datanascimento").getTime()));
					func.setEndereco(rs.getString(6));
					func.setCargo(rs.getString(7));
					
					if (func instanceof Professor){
						((Professor) func).setDisciplina(rs.getString(8));
					}
					
					func.setSalario(rs.getBigDecimal(9).toString());
					func.setValeAlimentacao(rs.getBigDecimal(10).toString());
					func.setValeRefeicao(rs.getBigDecimal(11).toString());
					func.setValeTransporte(rs.getBigDecimal(12).toString());
					func.setTelefone(rs.getString(13));
					func.setEmail(rs.getString(14));
					func.setFilhos(rs.getInt(15));
					
					arrayFilhos = new ArrayList<Pessoa>();
					
					if (func.getFilhos() > 0) {
						Pessoa filho = new Pessoa();
						filho.setNome(rs.getString(16));
						filho.setDataNascimento(new Date(rs.getTimestamp("FILHO.datanascimento").getTime()));
						arrayFilhos.add(filho);
					}
				}
			}
			rs.beforeFirst();
			if (rs.next()) {
				func.setListaFilhos(arrayFilhos);
				listaFunc.add(func);
			}
			
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return listaFunc;
	}
	
	public void alterarFuncionario(Funcionario func) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("UPDATE alunos SET nome = ?, cpf = ?, sexo = ?, datanascimento = ?, endereço = ?, curso = ?, telefone = ?, email = ?");
		sql.append("WHERE matricula = ?;");

		try {
			con.setAutoCommit(false);

			PreparedStatement stmt = con.prepareStatement(sql.toString());
			
			java.sql.Date d = new java.sql.Date(func.getDataNascimento().getTime());
			
			stmt.setString(1, String.valueOf(func.getNome()));
			stmt.setString(2, String.valueOf(func.getCpf()));
			stmt.setString(3, String.valueOf(func.getSexo()));
			stmt.setDate(4, d);
			stmt.setString(5, String.valueOf(func.getEndereco()));
			stmt.setString(6, String.valueOf(func.getCargo()));
			stmt.setString(7, String.valueOf(func.getTelefone()));
			stmt.setString(8, String.valueOf(func.getEmail()));
			stmt.setLong(9, Long.valueOf(func.getCadastro()));
			
			stmt.execute();
			con.commit();
			
			stmt.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}	
	
	public void inativarAluno(Long matricula) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("UPDATE alunos SET ativo = ? ");
		sql.append("WHERE matricula = ?;");
		
		try {
			con.setAutoCommit(false);
			
			PreparedStatement stmt = con.prepareStatement(sql.toString());
			
			stmt.setInt(1, 0);
			stmt.setLong(2, matricula);
			
			stmt.execute();
			con.commit();
			
			stmt.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}	
}
