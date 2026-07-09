package ru.severstal.backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record ClientRequest (

    @NotBlank(message = "Client name is required")
    @Size(max = 100, message = "Client name must be less than or equal to 100 characters")
    String name,

    @NotBlank(message = "Client email is required")
    @Email(message = "Client email must be valid")
    @Size(max = 100, message = "Client email must be less than or equal to 100 characters")
    String email,

    @Size(max = 20, message = "Client phone must be less than or equal to 20 characters")
    String phone
) {}
