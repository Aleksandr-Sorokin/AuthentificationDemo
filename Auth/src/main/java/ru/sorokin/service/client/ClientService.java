package ru.sorokin.service.client;

import ru.sorokin.entity.ClientEntity;

public interface ClientService {
    void register(String clientId, String clientSecret);
    void checkCredentials(String clientId, String clientSecret);

    ClientEntity checkClientId(String clientId);
}
