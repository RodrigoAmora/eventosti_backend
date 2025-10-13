package br.com.rodrigoamora.eventosti.dto.response;

public record UsuarioResponseDTO(

        Long id,
        String nome,
        String login,
        String hasError

) {}
