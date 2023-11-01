package br.com.portfoliohandy.portfoliohandy.model;


public class EmailMessage {

	public static String createTitle(PortfolioModel pessoa) {
		return "Nova mensagem do seu portfolio!";
	}
	
	public static String messageToNewUser(PortfolioModel pessoa) {
		return "Olá Handy, \n"
		+ "Tem uma nova mensagem do seu portfolio. \n"
		+ "Aqui são os dados do usuário. \n\n"
		+ "========================================== \n"
		+ "Nome: " + pessoa.getNome() + "\n"
		+ "E-mail: " + pessoa.getEmail() + "\n"
		+ "Assunto: " + pessoa.getAssunto() + "\n"
		+ "Mensagem: " + pessoa.getMensagem() + "\n"
		+ "========================================== \n";
	}

//	public static String messageToUser(PortfolioModel pessoa) {
//		return "Olá, " + pessoa.getNome() + "! \n"
//		+ "Validar o novo email \n"
//		+ "========================================== \n"
//		+ "Insira o codigo de acesso. \n\n"
//		+ "Codigo: " + pessoa.getCodigo() + "\n"
//		+ "========================================== \n";
//	}
	
	public static String changeEmail(PortfolioModel pessoa) {
		return pessoa.getNome() + " seu email foi alterado!";
	}
}
