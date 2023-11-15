package br.com.portfoliohandy.portfoliohandy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.portfoliohandy.portfoliohandy.model.PortfolioModel;


public interface PortfolioRepository extends JpaRepository<PortfolioModel, Long>{

	@Query(nativeQuery = true, value = "SELECT * FROM portfolio as por \n" +
            " where por.nome = :nome ")
	PortfolioModel checkName(@Param("nome") String nome);
}
