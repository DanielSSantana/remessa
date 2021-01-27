package controler;

import java.awt.event.ItemEvent;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Arquivo;
import model.Conexao;
import model.Itens;
import model.Notas;

public class Processamento {
	
	public void processo(String notaCompra, String notaVenda, String NVendedor) throws SQLException, IOException {
	
	Itens produto = new Itens();
	ArrayList<Itens> item = new ArrayList<>();
	
	ResultSet resul = produto.buscaProdutoSaida(notaVenda);
			
	while (resul.next()){
	
       Itens novoItem = new Itens();
       novoItem.setProduto(resul.getString("produto"));
       novoItem.setQuantidade(resul.getDouble("quantidade"));
       novoItem.settotal(resul.getDouble("precounitario"));
       item.add(novoItem);
        
   }

	if(notaCompra.length() > 2)
	{
		Itens produtoEntrada = new Itens();
		ArrayList<Itens> itemE = new ArrayList<>();
		
		ResultSet resulE = produtoEntrada.buscaProdutoEntrada(notaCompra, NVendedor);
				
		while (resulE.next()){
		
	       Itens novoItem = new Itens();
	       novoItem.setProduto(resulE.getString("produto"));
	       novoItem.setQuantidade(resulE.getDouble("quantidade"));
	       novoItem.settotal(resulE.getDouble("precounitario"));
	       itemE.add(novoItem);
	        
	   }
		
		
		int linha = 0;
		int linha1 = 0;
		while(linha1 < item.size() ) {
			String produtoS = item.get(linha1).getProduto();
			
			//Armazena em bigdecimal convertentdo de double para precisão
			String precoS = "" + item.get(linha1).gettotal();
				
			while(linha < itemE.size() ) {
				String produtoE = itemE.get(linha).getProduto();
				
				//Armazena em bigdecimal convertentdo de double para precisão
				String precoE = "" +itemE.get(linha).gettotal();

				//Processa quantiades produto
				if(produtoS.intern() == produtoE.intern() && precoS.intern() == precoE.intern()) {
					double valor1, valor2;
					Double valorE = 0.000;
					valor1 = item.get(linha1).getQuantidade();
					valor2 = itemE.get(linha).getQuantidade();
									
					if(valor2 > 0.000) {
						item.get(linha1).setQuantidade(valor1 - valor2);	
						valorE = valor2 - valor1;
					}
					
					
					if(valorE <= 0.000) {
						itemE.get(linha).setQuantidade(0.000000);
					}else {
						itemE.get(linha).setQuantidade(valorE);
					}
					
				}
				linha++;
				
			
			}
			linha = 0;
			linha1++;
		}
	}
	
	Arquivo arq = new Arquivo();
	
	arq.gravaArquivo(item);
	
  }

	public void baixarDocumento(String nDoc)
	{
		Notas nota = new Notas();
		try {
			nota.baixaNota(nDoc);
		} catch (IOException e) {
			//ainda não implementado
		} catch (SQLException e) {
			//ainda não implementado
		}
	}
	
}
