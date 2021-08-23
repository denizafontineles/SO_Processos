package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JOptionPane;

public class RedesController {

	public RedesController() {
		super();
	}
	
//	1) O primeiro, chamado os, que identifica e retorna o nome do Sistema Operacional (Fazê-lo privado)
	
	private String os() {
		return System.getProperty("os.name");
	}

	public void getIp() {
		try {
			
//			2) O segundo, chamado ip, que verifica o Sistema Operacional e, de acordo com o S.O., faz a chamada de configuração de IP.
			String os = os();
			String process = os.contains("Windows") ? "ipconfig" : "ifconfig";
			Process p;

			if (os.contains("Windows")) {
				p = Runtime.getRuntime().exec(process);
			} else {
				p = Runtime.getRuntime().exec(new String[] { "/bin/bash", "-c", process });
			}

//			A leitura do processo chamado deve verificar cada linha e, imprimir, apenas, o nome do adaptador de rede e o IPv4, portanto, adaptadores sem IPv4 não devem ser mostrados
			InputStream fluxo = p.getInputStream();
			InputStreamReader leitor = new InputStreamReader(fluxo);
			BufferedReader buffer = new BufferedReader(leitor);
			String linha = buffer.readLine();
			StringBuffer ip = new StringBuffer();

			while (linha != null) {
				if (os.contains("Windows")) {
					ip.append(ipWindows(linha));
				} else {
					ip.append(ipLinux(linha));
				}

				linha = buffer.readLine();
			}

			buffer.close();
			leitor.close();
			fluxo.close();
			
			JOptionPane.showMessageDialog(null, ip);
		} catch (IOException error) {
			error.printStackTrace();
		}
	}

	public void getPing() {
		try {
			JOptionPane.showMessageDialog(null, "Aguarde", null, JOptionPane.INFORMATION_MESSAGE);

//			3) O terceiro, chamado ping, que verifica o Sistema Operacional e, de acordo com o S.O. e, de acordo com o S.O., faz a chamada de ping em IPv4 com 10 iterações
			String os = os();
			String process = os.contains("Windows") ? "ping -4 -n 10 www.google.com.br"
					: "ping -4 -c 10 www.google.com.br";
			Process p;

			if (os.contains("Windows")) {
				p = Runtime.getRuntime().exec(process);
			} else {
				p = Runtime.getRuntime().exec(new String[] { "/bin/bash", "-c", process });
			}

			InputStream fluxo = p.getInputStream();
			InputStreamReader leitor = new InputStreamReader(fluxo);
			BufferedReader buffer = new BufferedReader(leitor);
			String linha = buffer.readLine();
			StringBuffer ping = new StringBuffer();

			while (linha != null) {
				if (os.contains("Windows")) {
					ping.append(pingWindows(linha));
				} else {
					ping.append(pingLinux(linha));
				}

				linha = buffer.readLine();
			}

			buffer.close();
			leitor.close();
			fluxo.close();
			
			JOptionPane.showMessageDialog(null, ping);
		} catch (IOException error) {
			error.printStackTrace();
		}
	}

	// tratativa
	private String ipWindows(String linha) {
		String adapterName = "";
		String adapterIPv4 = "";
		StringBuffer ip = new StringBuffer();

		if (linha.contains("Ethernet")) {
			adapterName = linha.split("Adaptador Ethernet")[1].split(":")[0];
			ip.append("Nome do adaptador: " + adapterName + "\n");
		}

		if (linha.contains("IPv4")) {
			adapterIPv4 = linha.split(":")[1];
			ip.append("IPv4 do adaptador: " + adapterIPv4 + "\n");
		}
		
		return ip.toString();
	}

	private String ipLinux(String linha) {
		String adapterName = "";
		String adapterIPv4 = "";
		StringBuffer ip = new StringBuffer();

		if (linha.contains("flags")) {
			adapterName = linha.split(":")[0];
			ip.append("Nome do adaptador: " + adapterName + "\n");
		}

		if (linha.contains("inet ")) {
			adapterIPv4 = linha.split("inet")[1].split("netmask")[0].split(" ")[1];
			ip.append("IPv4 do adaptador: " + adapterIPv4 + "\n");
			ip.append("\n");
		}
		
		return ip.toString();
	}

	private String pingWindows(String linha) {
		String pingMedio = "";
		StringBuffer ping = new StringBuffer();
		
		if (linha.contains("dia")) {
			pingMedio = linha.split(",")[2].split("=")[1];
			ping.append("Ping Médio: " + pingMedio);
		}
		
		return ping.toString();
	}

	private String pingLinux(String linha) {
		String pingMedio = "";
		StringBuffer ping = new StringBuffer();
		
		if (linha.contains("avg")) {
			pingMedio = linha.split("=")[1].split("/")[1];
			ping.append("Ping Médio: " + pingMedio + "ms");
		}
		
		return ping.toString();
	}
}
