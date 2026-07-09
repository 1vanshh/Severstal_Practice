package ru.severstal.backend.dto.response;

import lombok.Builder;

@Builder
public record ClientResponse (

    Long id,

    String name,

    String email,

    String phone
) {}