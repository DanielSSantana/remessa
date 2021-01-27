package model;

import java.io.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Arquivo {
	public void gravaArquivo(ArrayList<Itens> list) throws IOException {
	String dir = System.getProperty("user.dir");
	FileWriter arq = new FileWriter(dir + "\\dados.txt");  
	PrintWriter gravarArq = new PrintWriter(arq);
	
	int controle = 0;
	while(controle < list.size() ){
		if(list.get(controle).getQuantidade() > 0) {
			gravarArq.println(list.get(controle).getProduto() + ";" + list.get(controle).getQuantidade() + ";" + list.get(controle).gettotal() + ";");
		}

		controle++;
	}
	
	arq.close();
	
	}
	
	public String lerArquivo() throws IOException  {
		String dir = System.getProperty("user.dir");
		FileReader arq = new FileReader(dir + "\\ip.txt");
		BufferedReader lerArq = new BufferedReader(arq);
		String linha = lerArq.readLine();
		return linha;
		
	}
	
}
