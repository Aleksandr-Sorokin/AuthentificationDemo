package ru.sorokin.service.client;

import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import ru.sorokin.entity.ClientEntity;
import ru.sorokin.exceptions.model.LoginException;
import ru.sorokin.exceptions.model.RegistrationException;
import ru.sorokin.repository.ClientRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultClientService implements ClientService {
    private final ClientRepository clientRepository;

    @Override
    public void register(String clientId, String clientSecret) {
        if (clientRepository.findById(clientId).isPresent()) {
            throw new RegistrationException(String.format("Пользователь с id %s зарегистрирован", clientId));
        }
        String hash = BCrypt.hashpw(clientSecret, BCrypt.gensalt());
        clientRepository.save(new ClientEntity(clientId, hash));
    }

    @Override
    public void checkCredentials(String clientId, String clientSecret) {
        ClientEntity clientEntity = checkClientId(clientId);
        if (!BCrypt.checkpw(clientSecret, clientEntity.getHash())) {
            throw new LoginException("Ключ не соответствует");
        }
    }

    @Override
    public ClientEntity checkClientId(String clientId) {
        Optional<ClientEntity> optionalClientEntity = clientRepository.findById(clientId);
        if (optionalClientEntity.isEmpty()) {
            throw new LoginException(String.format("Пользователь с id %s не найден", clientId));
        }
        return optionalClientEntity.get();
    }
}
