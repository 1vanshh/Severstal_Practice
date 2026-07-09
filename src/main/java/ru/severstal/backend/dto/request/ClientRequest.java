package ru.severstal.backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientRequest {

    @NotBlank(message = "Client name is required")
    @Size(max = 100, message = "Client name must be less than or equal to 100 characters")
    private String name;

    @NotBlank(message = "Client email is required")
    @Email(message = "Client email must be valid")
    @Size(max = 100, message = "Client email must be less than or equal to 100 characters")
    private String email;

    @Size(max = 20, message = "Client phone must be less than or equal to 20 characters")
    private String phone;
}
