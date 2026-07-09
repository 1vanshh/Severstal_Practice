package ru.severstal.backend.service;

import java.util.List;

public interface CrudService<Response, Request> {

    Response create(Request request);

    List<Response> getAll();

    Response getById(Long id);

    Response update(Long id, Request request);

    void delete(Long id);
}