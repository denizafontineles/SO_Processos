package controller;

public class RedesController {
	
	public RedesController(){
		super();
	}
	
	//chama os = identifica e retorna o nome do Sistema Operacional (privado)
	private getOs() {
		String os = System.getProperty("os.name");
		return os;
	}
	
	//chama ip, verifica o Sistema Operacional e, de acordo com o SO, fazer a
	// chamada de configuração de IP.
	
	// A leitura do processo chamado deve verificar cada linha e, imprimir, apenas, o nome do
	//adaptador de rede e o IPv4, portanto, adaptadores sem IPv4 não devem ser mostrados
	
	public void getIp() {
		
		
		
		
	}
	
	
	

}
