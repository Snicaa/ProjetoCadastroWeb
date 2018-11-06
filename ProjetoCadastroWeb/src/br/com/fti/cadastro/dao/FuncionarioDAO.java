package br.com.fti.cadastro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class FuncionarioDAO {
	
	private final Connection con;
	private int codigoAtual;
	
	public FuncionarioDAO(DataSource dataSource) {
		try {
			this.con = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void cadastrarFuncionario(Aluno aluno) {
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("INSERT INTO alunos (nome, cpf, sexo, datanascimento, endereço, curso, telefone, email, ativo)");
		sql.append("VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");

		try {
			con.setAutoCommit(false);

			PreparedStatement stmt = con.prepareStatement(sql.toString());	

			java.sql.Date d = new java.sql.Date(aluno.getDataNascimento().getTime());
			
			stmt.setString(1, String.valueOf(aluno.getNome()));
			stmt.setString(2, String.valueOf(aluno.getCpf()));
			stmt.setString(3, aluno.getSexo());
			stmt.setDate(4, d);
			stmt.setString(5, String.valueOf(aluno.getEndereco()));
			stmt.setString(6, String.valueOf(aluno.getCurso()));
			stmt.setString(7, String.valueOf(aluno.getTelefone()));
			stmt.setString(8, String.valueOf(aluno.getEmail()));
			stmt.setLong(9, 1);
			
			stmt.execute();
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
				aluno.setDataNascimento(UtilController.sdf.format(new Date(rs.getTimestamp("datanascimento").getTime())));
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
					filho.setDataNascimento(UtilController.sdf.format(new Date(rs.getTimestamp("FILHO.datanascimento").getTime())));
					arrayFilhos.add(filho);
				} else {
					if (rs.getRow() != 1) {
						func.setListaFilhos(arrayFilhos);
						listaFunc.add(func);
					}
					codigoAtual = rs.getInt(1);
					
					if (rs.getString(7).equals("Professor")){func = new Professor();} else {func = new Funcionario();}
					
					func.setCadastro(rs.getString(1));
					func.setNome(rs.getString(2));
					func.setCpf(rs.getString(3));
					func.setSexo(rs.getString(4));
					func.setDataNascimento(UtilController.sdf.format(new Date(rs.getTimestamp("FUNC.datanascimento").getTime())));
					func.setEndereco(rs.getString(6));
					func.setCargo(rs.getString(7));
					
					if (func instanceof Professor){
						((Professor) func).setDisciplina(rs.getString(8));
					}
					
					func.setSalario(rs.getDouble(9));
					func.setValeAlimentacao(rs.getDouble(10));
					func.setValeRefeicao(rs.getDouble(11));
					func.setValeTransporte(rs.getDouble(12));
					func.setTelefone(rs.getString(13));
					func.setEmail(rs.getString(14));
					func.setFilhos(rs.getInt(15));
					
					arrayFilhos = new ArrayList<Pessoa>();
					
					if (func.getFilhos() > 0) {
						Pessoa filho = new Pessoa();
						filho.setNome(rs.getString(16));
						filho.setDataNascimento(UtilController.sdf.format(new Date(rs.getTimestamp("FILHO.datanascimento").getTime())));
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
	
	public void alterarAluno(Aluno aluno) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("UPDATE alunos SET nome = ?, cpf = ?, sexo = ?, datanascimento = ?, endereço = ?, curso = ?, telefone = ?, email = ?");
		sql.append("WHERE matricula = ?;");

		try {
			con.setAutoCommit(false);

			PreparedStatement stmt = con.prepareStatement(sql.toString());
			
			java.sql.Date d = new java.sql.Date(aluno.getDataNascimento().getTime());
			
			stmt.setString(1, String.valueOf(aluno.getNome()));
			stmt.setString(2, String.valueOf(aluno.getCpf()));
			stmt.setString(3, String.valueOf(aluno.getSexo()));
			stmt.setDate(4, d);
			stmt.setString(5, String.valueOf(aluno.getEndereco()));
			stmt.setString(6, String.valueOf(aluno.getCurso()));
			stmt.setString(7, String.valueOf(aluno.getTelefone()));
			stmt.setString(8, String.valueOf(aluno.getEmail()));
			stmt.setLong(9, Long.valueOf(aluno.getMatricula()));
			
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
