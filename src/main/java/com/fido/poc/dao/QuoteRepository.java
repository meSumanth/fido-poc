package com.fido.poc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fido.poc.entity.Quote;

public interface QuoteRepository extends JpaRepository<Quote, Integer> {

  @Query(nativeQuery = true, value = "SELECT id,quote,author FROM quotes ORDER BY RANDOM() LIMIT 1")
  Quote findRandomQuote();
}
