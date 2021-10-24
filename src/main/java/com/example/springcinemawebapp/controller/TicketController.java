package com.example.springcinemawebapp.controller;

import com.example.springcinemawebapp.dto.TicketDTO;
import com.example.springcinemawebapp.model.MovieSession;
import com.example.springcinemawebapp.model.Ticket;
import com.example.springcinemawebapp.service.MovieSessionService;
import com.example.springcinemawebapp.service.TicketService;
import com.example.springcinemawebapp.utils.ValidList;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.example.springcinemawebapp.properties.TechnicalConstants.*;

@AllArgsConstructor
@Controller
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final MovieSessionService movieSessionService;

    @GetMapping
    public String getTicketsForSession(@RequestParam(value = "session", required = false) String session, Model model) {
        if (session == null) {
            return "redirect:sessions";
        }
        List<Ticket> purchasedTickets;
        MovieSession movieSession;
        try {
            purchasedTickets = ticketService.getAllTicketsOfSession(session);
            movieSession = movieSessionService.getById(session);
        } catch (IllegalArgumentException e) {
            return "redirect:sessions";
        }
        model.addAttribute("rowsNumber", NUMBER_OF_ROWS);
        model.addAttribute("seatsNumber", NUMBER_OF_SEATS_PER_ROW);
        model.addAttribute("purchasedTickets", purchasedTickets);
        model.addAttribute("movieSession", movieSession);
        model.addAttribute("ticketPrice", TICKET_PRICE);

        return "tickets";
    }

    @PostMapping
    public String buyTickets(@RequestParam(value = "session") String session,
                             @RequestBody @Valid ValidList<TicketDTO> tickets, BindingResult bindingResult) {

        return "redirect:tickets/success?session="+session;
    }

}
