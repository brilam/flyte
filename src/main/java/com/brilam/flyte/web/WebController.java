package com.brilam.flyte.web;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.thymeleaf.ITemplateEngine;

public interface WebController {
  public void process(
      HttpServletRequest request, HttpServletResponse response,
      ServletContext servletContext, ITemplateEngine templateEngine)
      throws Exception;
  }
