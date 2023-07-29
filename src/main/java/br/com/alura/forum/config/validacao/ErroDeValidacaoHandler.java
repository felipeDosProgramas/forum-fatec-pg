package br.com.alura.forum.config.validacao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErroDeValidacaoHandler {
	@Autowired
	/*
	Ajuda a pegar mensagem de erro
	*/
private MessageSource mesageSource;
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErroDeFormDTO> handle(MethodArgumentNotValidException exception) {
		List<ErroDeFormDTO> dto = new ArrayList<>();
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		
		fieldErrors.forEach(e -> {
			String mensagem = mesageSource.getMessage(e, LocaleContextHolder.getLocale());
			ErroDeFormDTO erro = new ErroDeFormDTO(e.getField(), mensagem);
			dto.add(erro);
		});
		return dto;
	}

}
