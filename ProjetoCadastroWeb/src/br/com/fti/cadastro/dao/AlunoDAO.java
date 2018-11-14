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

@Repository
public class AlunoDAO {
	
	private final Connection con;
	
	public AlunoDAO(DataSource dataSource) {
		try {
			this.con = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void cadastrarAluno(Aluno aluno) {
		
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
	
	public Aluno consultarPorMatricula(Long matricula){
		String sql = "SELECT nome, cpf, sexo, datanascimento, endereço, curso, telefone, email "
				+ "FROM alunos WHERE ativo = 1 AND matricula = ? "
				+ "ORDER BY matricula ASC";
		
		Aluno aluno = new Aluno();
		
		try {
			PreparedStatement stmt = con.prepareStatement(sql.toString());
			
			stmt.setLong(1, matricula);
			
			ResultSet rs = stmt.executeQuery();
	
			if (rs.next()) {				
				aluno.setMatricula(matricula);
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
	
	public ArrayList<Aluno> consultarListaAluno() {

		ArrayList<Aluno> listaAluno = new ArrayList<Aluno>();
		
		String sql = "SELECT matricula, nome, cpf, sexo, datanascimento, endereço, curso, telefone, email "
				+ "FROM alunos WHERE ativo = 1 "
				+ "ORDER BY matricula ASC";

		try {
			PreparedStatement stmt = con.prepareStatement(sql.toString());

			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				Aluno aluno = new Aluno();
				
				aluno.setMatricula(rs.getLong(1));
				aluno.setNome(rs.getString(2));
				aluno.setCpf(rs.getString(3));
				aluno.setSexo(rs.getString(4));
				aluno.setDataNascimento(new Date(rs.getTimestamp("datanascimento").getTime()));
				aluno.setEndereco(rs.getString(6));
				aluno.setCurso(rs.getString(7));
				aluno.setTelefone(rs.getString(8));
				aluno.setEmail(rs.getString(9));
				
				listaAluno.add(aluno);
			}
			
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return listaAluno;
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
