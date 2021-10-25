package com.example.springcinemawebapp.facade;

import com.example.springcinemawebapp.dto.TicketDTO;
import com.example.springcinemawebapp.model.MovieSession;
import com.example.springcinemawebapp.model.Ticket;
import com.example.springcinemawebapp.model.User;
import com.example.springcinemawebapp.service.MovieSessionService;
import com.example.springcinemawebapp.service.TicketService;
import com.example.springcinemawebapp.service.UserService;
import com.example.springcinemawebapp.utils.EncryptionUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.springcinemawebapp.properties.TechnicalConstants.BASE64_ENCRYPT_ROUNDS;
import static com.example.springcinemawebapp.properties.TechnicalConstants.TICKET_PRICE;

@Component
@AllArgsConstructor
public class TicketFacade {
    private final MovieSessionService movieSessionService;
    private final TicketService ticketService;
    private final UserService userService;


    public List<Ticket> getAllTicketsOfSession(String encryptedId) {
        long id = EncryptionUtils.decryptId(encryptedId, BASE64_ENCRYPT_ROUNDS);
        return ticketService.getAllTicketsOfSession(id);
    }

    public MovieSession getSessionById(String encryptedId) {
        long id = EncryptionUtils.decryptId(encryptedId, BASE64_ENCRYPT_ROUNDS);
        return movieSessionService.getById(id);
    }

    @Transactional
    public void buyTickets(List<TicketDTO> ticketDTOS, String encryptedSessionId, String username) {
        long sessionId = EncryptionUtils.decryptId(encryptedSessionId, BASE64_ENCRYPT_ROUNDS);
        MovieSession session = movieSessionService.getById(sessionId);
        User buyer = userService.findByUsername(username);
        long price = (long) TICKET_PRICE * ticketDTOS.size();
        userService.reduceUserBalance(buyer, price);
        ticketService.addAllTickets(ticketDTOS, session, buyer);
    }
}
