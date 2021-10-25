package com.example.springcinemawebapp.service;

import com.example.springcinemawebapp.dto.TicketDTO;
import com.example.springcinemawebapp.model.MovieSession;
import com.example.springcinemawebapp.model.Ticket;
import com.example.springcinemawebapp.model.User;
import com.example.springcinemawebapp.repository.TicketRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@AllArgsConstructor
public class TicketService {
    private final TicketRepository repository;

    public List<Ticket> getAllTicketsOfSession(long sessionId) {
        return repository.findAllBySession_IdEquals(sessionId);
    }

    public void addAllTickets(List<TicketDTO> ticketDTOS, MovieSession session, User buyer) {
        List<Ticket> tickets = ticketDTOS.stream().map(ticketDTO -> Ticket
                .builder()
                .rowNumber(ticketDTO.getRowNumber())
                .seatNumber(ticketDTO.getSeatNumber())
                .session(session)
                .buyer(buyer)
                .build()).collect(Collectors.toList());
        repository.saveAll(tickets);
    }
}
