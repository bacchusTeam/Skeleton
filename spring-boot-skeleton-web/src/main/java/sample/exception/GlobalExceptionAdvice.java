package sample.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import sun.reflect.annotation.ExceptionProxy;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionAdvice {

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  protected String handleInternalServerError(Exception e) {
    return e.getMessage();
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    final List<ErrorResponse.FieldError> fieldErrors = getFieldErrors(e.getBindingResult());
    return buildFieldErrors(ErrorCode.INPUT_VALUE_INVALID, fieldErrors);
  }

  @ExceptionHandler(BindException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ErrorResponse handleBindException(BindException e) {
    final List<ErrorResponse.FieldError> fieldErrors = getFieldErrors(e.getBindingResult());
    return buildFieldErrors(ErrorCode.INPUT_VALUE_INVALID, fieldErrors);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ErrorResponse handleDataIntegrityViolationException(DataIntegrityViolationException e) {
    log.error(e.getMessage());
    return buildError(ErrorCode.INPUT_VALUE_INVALID);
  }


  private List<ErrorResponse.FieldError> getFieldErrors(BindingResult bindingResult) {
    final List<FieldError> errors = bindingResult.getFieldErrors();
    return errors.parallelStream()
            .map(error -> ErrorResponse.FieldError.builder()
                    .reason(error.getDefaultMessage())
                    .field(error.getField())
                    .value((String) error.getRejectedValue())
                    .build())
            .collect(Collectors.toList());
  }


  private ErrorResponse buildError(ErrorCode errorCode) {
    return ErrorResponse.builder()
            .code(errorCode.getCode())
            .status(errorCode.getStatus())
            .message(errorCode.getMessage())
            .build();
  }

  private ErrorResponse buildFieldErrors(ErrorCode errorCode, List<ErrorResponse.FieldError> errors) {
    return ErrorResponse.builder()
            .code(errorCode.getCode())
            .status(errorCode.getStatus())
            .message(errorCode.getMessage())
            .errors(errors)
            .build();
  }
}
