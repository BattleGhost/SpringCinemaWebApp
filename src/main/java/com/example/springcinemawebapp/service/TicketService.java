package com.example.springcinemawebapp.service;

import com.example.springcinemawebapp.model.Ticket;
import com.example.springcinemawebapp.repository.TicketRepository;
import com.example.springcinemawebapp.utils.EncryptionUtils;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.springcinemawebapp.properties.TechnicalConstants.BASE64_ENCRYPT_ROUNDS;

@Log4j2
@Service
@AllArgsConstructor
public class TicketService {
    private final TicketRepository repository;

    public List<Ticket> getAllTicketsOfSession(long id) {
        return repository.findAllBySession_IdEquals(id);
    }

    public List<Ticket> getAllTicketsOfSession(String encryptedId) {
        long id = EncryptionUtils.decryptId(encryptedId, BASE64_ENCRYPT_ROUNDS);
        return getAllTicketsOfSession(id);
    }
}
