package model;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Itens {

	private int idproduto;
	private int idNotaFiscal;
	private long idcfop;
	private String cfop;
	private String produto;
	private double quantidade;
	private double total;
	
	
	public long getIdcfop() {
		return idcfop;
	}

	public void setIdcfop(long idcfop) {
		this.idcfop = idcfop;
	}
	
	public String getCfop() {
		return cfop;
	}

	public void setCfop(String cfop) {
		this.cfop = cfop;
	}
	
	public int getIdproduto() {
		return idproduto;
	}

	public void setIdproduto(int idproduto) {
		this.idproduto = idproduto;
	}

	public int getIdNotaFiscal() {
		return idNotaFiscal;
	}

	public void setIdNotaFiscal(int idNotaFiscal) {
		this.idNotaFiscal = idNotaFiscal;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}
	
	public double gettotal() {
		return total;
	}

	public void settotal(double total) {
		this.total = total;
	}


	public ResultSet buscaProdutoSaida(String notaVenda) throws IOException {
	ResultSet dados = Conexao.selectSql(
			"select produto, quantidade, precounitario from notafiscalitem "			
			+ "where idnotafiscal = (select id from notafiscal where tipodocumento = 'S' and status = '3' and numeronotafiscal LIKE '%" + notaVenda +"');");
			
		return dados;
	}
	
	public ResultSet buscaProdutoEntrada(String notaCompra, String NVendedor) throws IOException {
	
		int i = 0;
		String val, numeroConsulta = null;
		while (i < notaCompra.length())
		{
			val = notaCompra.substring(i,i + 1);

			if(val.intern() == (","))
			{
				numeroConsulta = numeroConsulta +  "') or numeronotafiscal LIKE ('%";
				i++;
				continue;
			}
			else
			{
				if(i == 0)
				{
					numeroConsulta =  val;
					i++;
						continue;
				}
				numeroConsulta = numeroConsulta + val;
				i++;
			}
			
		}
	ResultSet dados = Conexao.selectSql(
			"select produto, quantidade, precounitario from notafiscalitem where idnotafiscal in (select id from notafiscal where tipodocumento = 'E' and status = '3' and numeronotafiscal LIKE ('%" + numeroConsulta + "'))");
			
		return dados;
		}


	}

