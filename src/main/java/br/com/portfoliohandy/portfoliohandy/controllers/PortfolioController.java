package br.com.portfoliohandy.portfoliohandy.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.ClassPathResource;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.io.FileOutputStream;

import br.com.portfoliohandy.portfoliohandy.model.PortfolioModel;
import br.com.portfoliohandy.portfoliohandy.services.PortfolioService;
import br.com.portfoliohandy.portfoliohandy.view.PortfolioDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/portfolio")
// @CrossOrigin(origins = { "http://localhost:4200", "http://localhost" },
// maxAge = 3600)
@CrossOrigin(origins = { "https://portfolio-handy.netlify.app/" }, maxAge = 3600)

public class PortfolioController {

	@Autowired
	private PortfolioService portfolioService;

	@PostMapping("/saveMessage")
	@Transactional(rollbackFor = Exception.class)
	public PortfolioDto saveMessage(@RequestBody PortfolioModel portfolioModel, HttpServletRequest request)
			throws ParseException {
		PortfolioDto task = portfolioService.saveMessage(portfolioModel);
		return task;
	}

	@PostMapping("/savePicture")
	@Transactional(rollbackFor = Exception.class)
	public PortfolioDto savePicture(@RequestBody PortfolioModel portfolioModel, HttpServletRequest request)
			throws ParseException {
		PortfolioDto task = portfolioService.savePicture(portfolioModel);
		return task;
	}

	@GetMapping("/pegarFoto/{nome}")
	public PortfolioDto pegarFoto(@PathVariable("nome") String nome, HttpServletRequest request) throws ParseException {
		PortfolioDto task = portfolioService.pegarFoto(nome);
		return task;
	}

	@GetMapping("/downloadCurriculo/{idioma}")
	public ResponseEntity<ByteArrayResource> downloadCurriculo(
			@PathVariable("idioma") String idioma,
			HttpServletResponse response) throws IOException {
		String nomeArquivo = obterNomeArquivo(idioma);
		String caminhoCV = "curriculo/" + idioma + "/" + nomeArquivo;

		try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(caminhoCV)) {
			if (inputStream == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}

			byte[] arquivoBytes = IOUtils.toByteArray(inputStream);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_PDF);
			headers.setContentDispositionFormData("attachment", nomeArquivo);

			// Configurando Cache-Control para forçar a revalidaçao
			headers.setCacheControl("no-cache, no-store, must-revalidate");
			headers.setPragma("no-cache");
			headers.setExpires(0);

			PortfolioDto downloadCurriculo = portfolioService.baixarCurriculo(idioma);

			return ResponseEntity.ok().headers(headers).body(new ByteArrayResource(arquivoBytes));
		} catch (IOException e) {
			// Log de erro ou tratamento adequado
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	
	@GetMapping("/downloadCertificado/{idioma}")
	public ResponseEntity<ByteArrayResource> downloadCertificado(
			@PathVariable("idioma") String idioma,
			HttpServletResponse response) throws IOException {
		String nomeArquivo = obterNomeCertificado(idioma);
		String caminhoCER = "certificado/" + idioma + "/" + nomeArquivo;

		try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(caminhoCER)) {
			if (inputStream == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}

			byte[] arquivoBytes = IOUtils.toByteArray(inputStream);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_PDF);
			headers.setContentDispositionFormData("attachment", nomeArquivo);

			// Configurando Cache-Control para forçar a revalidaçao
			headers.setCacheControl("no-cache, no-store, must-revalidate");
			headers.setPragma("no-cache");
			headers.setExpires(0);

			PortfolioDto downloadCertificado = portfolioService.baixarCertificado(idioma);

			return ResponseEntity.ok().headers(headers).body(new ByteArrayResource(arquivoBytes));
		} catch (IOException e) {
			// Log de erro ou tratamento adequado
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	
	private String obterNomeArquivo(String idioma) {
		switch (idioma) {
			case "pt-br":
				return "CV_HANDY_BR.pdf";
			case "en":
				return "CV_HANDY_EN.pdf";
			case "fr":
				return "CV_HANDY_FR.pdf";
			default:
				return "CV_HANDY_BR.pdf";
		}
	}

	
	private String obterNomeCertificado(String idioma) {
		switch (idioma) {
			case "pt-br":
				return "BOOTCAMP_DIO_FINALIZADO.pdf" + "Certificado_Full_Stack.pdf" + "certificado_trilha_rocketseat.pdf";
			case "en":
				return "BOOTCAMP_DIO_FINALIZADO.pdf" + "Certificado_Full_Stack.pdf" + "certificado_trilha_rocketseat.pdf";
			case "fr":
				return "BOOTCAMP_DIO_FINALIZADO.pdf" + "Certificado_Full_Stack.pdf" + "certificado_trilha_rocketseat.pdf";
			default:
				return "BOOTCAMP_DIO_FINALIZADO.pdf" + "Certificado_Full_Stack.pdf" + "certificado_trilha_rocketseat.pdf";
		}
	}
}
