package med.voll.api.infra.exceptions;

import jakarta.persistence.EntityNotFoundException;
import med.voll.api.domain.ValidacaoException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErros {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity resourceNotFound(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity erroValidacao(MethodArgumentNotValidException exception){
        var erros = exception.getFieldErrors();

        var dadosPersonalizados = erros.stream()
                .map(DadosErroValidacao::new)
                .toList();

        return ResponseEntity.badRequest().body(dadosPersonalizados);
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity tratarErroRegraNegocio(ValidacaoException exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    private record DadosErroValidacao(String campo, String menssagem){
        public DadosErroValidacao(FieldError error){
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
