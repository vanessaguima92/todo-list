package br.com.viceri.todolist.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HttpExceptionCustomResponseDTO {
    private String error;
    private String message;
}
