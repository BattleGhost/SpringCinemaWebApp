package com.example.springcinemawebapp.controller;

import com.example.springcinemawebapp.dto.TicketDTO;
import com.example.springcinemawebapp.exception.NotEnoughMoneyException;
import com.example.springcinemawebapp.facade.TicketFacade;
import com.example.springcinemawebapp.model.MovieSession;
import com.example.springcinemawebapp.model.Ticket;
import com.example.springcinemawebapp.utils.ValidList;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
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

    private final TicketFacade ticketFacade;

    @GetMapping
    public String getTicketsForSession(@RequestParam(value = "session", required = false) String session, Model model) {
        if (session == null) {
            return "redirect:sessions";
        }
        List<Ticket> purchasedTickets;
        MovieSession movieSession;
        try {
            purchasedTickets = ticketFacade.getAllTicketsOfSession(session);
            movieSession = ticketFacade.getSessionById(session);
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
    public String buyTickets(Authentication authentication, @RequestParam(value = "session") String session,
                             @RequestBody @Valid ValidList<TicketDTO> tickets, BindingResult bindingResult) {
        if (authentication == null || bindingResult.hasErrors()) {
            return "redirect:tickets/failure?validation";
        }
        try {
            ticketFacade.buyTickets(tickets, session, ((User) authentication.getPrincipal()).getUsername());
        } catch (NotEnoughMoneyException e) {
            return "redirect:tickets/failure?money";
        } catch (Exception e) {
            return "redirect:tickets/failure?unknown";
        }
        return "redirect:tickets/success";
    }

    @GetMapping("/success")
    public String getSuccessPage() {
        return "tickets-success";
    }

    @GetMapping("/failure")
    public String getFailurePage() {
        return "tickets-failure";
    }
}
