package com.fido.poc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fido.poc.dao.QuoteRepository;
import com.fido.poc.entity.Quote;

/**
 * 
 * 
 */

@RestController
public class QuotesController {

  private final QuoteRepository quoteRepository;

  public QuotesController(QuoteRepository quoteRepository) {
    this.quoteRepository = quoteRepository;
  }

  @GetMapping("/random-quote")
  public Quote randomQuote() {
    Quote result = quoteRepository.findRandomQuote();
    return result;
  }
}
