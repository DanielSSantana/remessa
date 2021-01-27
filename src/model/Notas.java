package model;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Notas {

	public void baixaNota(String nDoc) throws IOException, SQLException {

		long idnota = 0;
		
		Conexao.executaAtaulizacao("update movimentoestoque set quantidadeentrada = quantidadesaida where idoriginal = (select id from notafiscal where numeronotafiscal = '"+ nDoc +"');");
		Conexao.executaAtaulizacao("update movimentoestoque set quantidadesaida = '0' where idoriginal = (select id from notafiscal where numeronotafiscal = '"+ nDoc +"');");
		Conexao.executaAtaulizacao("update saldoestoque set quantidade = quantidade + movimentoestoque.quantidadeentrada + movimentoestoque.quantidadeentrada from movimentoestoque " + 
				" where saldoestoque.idproduto in (select idproduto from movimentoestoque where idoriginal = (select id from notafiscal where numeronotafiscal = '"+ nDoc +"'));");
		
		
		//Consulta idnotafiscal e armazena na variavel idnota
		ResultSet resul = Conexao.selectSql("select id from notafiscal where numeronotafiscal = '" + nDoc + "'");
		while (resul.next()){
			idnota = resul.getLong("id");
			
		}
		
		//Cria lista para armazenar os produtos
		ArrayList<Itens> itenConsultado = new ArrayList<>();
		
		resul = Conexao.selectSql("select  idproduto, idcfop, cfop from notafiscalitem where idnotafiscal = '" + idnota + "'");
		while (resul.next()) {
			Itens iten = new Itens();
			iten.setIdproduto(resul.getInt("idproduto"));
			iten.setIdcfop(resul.getLong("idcfop"));
			iten.setCfop(resul.getString("cfop"));
			itenConsultado.add(iten);
		}
		
		//Ajusta dados produtos
		int linha = 0;
		while(linha < itenConsultado.size()) {
			//consuta cfop inverso
			resul = Conexao.selectSql("Select idnaturezaoperacaoinversa from cfop where id = '"+ itenConsultado.get(linha).getIdcfop() +"';");
			
			//muda idcfop no produto
			while(resul.next()) {
				itenConsultado.get(linha).setIdcfop(resul.getLong("idnaturezaoperacaoinversa"));
			}
			
			resul = Conexao.selectSql("select codigo from cfop where id = '"+ itenConsultado.get(linha).getIdcfop() +"'");
			
			
			while(resul.next()) {
				itenConsultado.get(linha).setCfop(resul.getString("codigo"));
			}
			linha++;
		}
		
		//ajusta dados produto no banco
		
		linha = 0;
		while(linha < itenConsultado.size()) {
			Conexao.executaAtaulizacao("update notafiscalitem set idcfop = '"+ itenConsultado.get(linha).getIdcfop() +"'"
					+ ", cfop = '"+ itenConsultado.get(linha).getCfop() +"'"
					+ " where idproduto = '"+ itenConsultado.get(linha).getIdproduto() +"'"
							+ " and idnotafiscal = '"+ idnota +"'");
			linha++;
		}

		
		//atualiza dados do cabeçalho da nota 
		Conexao.executaAtaulizacao("update notafiscal set tipodocumento = 'E', descricaocfop = (select descricao from cfop where id = (select idcfop from notafiscal where numeronotafiscal ='"+ nDoc +"')) where numeronotafiscal = '"+ nDoc +"';");
		Conexao.executaAtaulizacao("update notafiscal set idcfop = (select idnaturezaoperacaoinversa from cfop where id = (select idcfop from notafiscal where numeronotafiscal ='"+ nDoc +"')) where numeronotafiscal = '"+ nDoc +"';");

		
	}
}
