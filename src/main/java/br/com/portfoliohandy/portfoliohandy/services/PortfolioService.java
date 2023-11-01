package br.com.portfoliohandy.portfoliohandy.services;

import java.util.Objects;
import java.util.Optional;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.portfoliohandy.portfoliohandy.model.EmailMessage;
import br.com.portfoliohandy.portfoliohandy.model.PortfolioModel;
import br.com.portfoliohandy.portfoliohandy.repository.PortfolioRepository;
import br.com.portfoliohandy.portfoliohandy.view.PortfolioDto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

@Service
public class PortfolioService {

	@Autowired
	private PortfolioRepository portfolioRepository;
	
	@Autowired
	private EnviarEmailService enviarEmailService;
	
	public PortfolioDto saveMessage(PortfolioModel portfolioModel) throws ParseException {
		
		String meuEmail = "milliance23@gmail.com";
		
		PortfolioDto portfolio = new PortfolioDto();
		if (portfolioModel.getNome() == null || portfolioModel.getEmail() == null || portfolioModel.getAssunto() == null || portfolioModel.getMensagem() == null) {
			portfolio.setSuccess(Boolean.FALSE);
			portfolio.setMessage("Os campos Nome, E-mail, Assunto e Mensagem são obrigatórios.");
            return portfolio;
        }
		
		portfolioModel.setDtLimite(LocalDate.now());
		
		portfolioRepository.save(portfolioModel);
		
		this.enviarEmailService.enviar(meuEmail, EmailMessage.createTitle(portfolioModel), EmailMessage.messageToNewUser(portfolioModel));
		
		portfolio.setId(portfolioModel.getId());
		portfolio.setNome(portfolioModel.getNome());
		
		portfolio.setSuccess(Boolean.TRUE);
		
		return portfolio;
	}
}
