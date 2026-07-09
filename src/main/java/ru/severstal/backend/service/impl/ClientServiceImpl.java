package ru.severstal.backend.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.severstal.backend.dto.request.ClientRequest;
import ru.severstal.backend.dto.response.ClientResponse;
import ru.severstal.backend.entity.Client;
import ru.severstal.backend.exception.NotFoundException;
import ru.severstal.backend.repository.ClientRepository;
import ru.severstal.backend.service.ClientService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public ClientResponse create(ClientRequest request) {
        Client client = new Client();
        client.setName(request.name());
        client.setEmail(request.email());
        client.setPhoneNumber(request.phone());

        Client savedClient = clientRepository.save(client);

        return mapToResponse(savedClient);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientResponse> getAll() {
        return clientRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ClientResponse getById(Long id) {
        Client client = getClientEntityById(id);
        return mapToResponse(client);
    }

    @Override
    public ClientResponse update(Long id, ClientRequest request) {
        Client client = getClientEntityById(id);

        client.setName(request.name());
        client.setEmail(request.email());
        client.setPhoneNumber(request.phone());

        Client updatedClient = clientRepository.save(client);

        return mapToResponse(updatedClient);
    }

    @Override
    public void delete(Long id) {
        Client client = getClientEntityById(id);
        clientRepository.delete(client);
    }

    private Client getClientEntityById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Client not found with id: " + id));
    }

    private ClientResponse mapToResponse(Client client) {
        return ClientResponse.builder()
                .id(client.getId())
                .name(client.getName())
                .email(client.getEmail())
                .phone(client.getPhoneNumber())
                .build();
    }
}
