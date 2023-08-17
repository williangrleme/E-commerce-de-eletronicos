package com.projeto.util;

import java.util.Objects;

public class ProcessamentoDados {
	
	public static final String RELATORIO = "reports/";
	public static final String SUFIXO_RELATORIO_COMPILADO = ".jasper";
	public static final String SUFIXO_RELATORIO_JRXML = ".jrxml";
	
	public static final Integer INCLUIR_REGISTRO =  0;
	public static final Integer ALTERAR_REGISTRO =  1;
	public static final Integer EXCLUIR_REGISTRO =  2;
	public static final Integer CONSULTAR_REGISTRO = 3;
	
	public static boolean campoDigitacao(String texto) {	//criado apenas uma vez 
		if(Objects.isNull(texto)) {
			return true;
		}
		
		if("".equals(texto.trim())) {//o trim tira espaco em branco e pa
			return true;
		}
			
		return false;
	}
	
	public static Integer converteParaInteiro(String texto) {
		return Integer.parseInt(texto);
	}

}
