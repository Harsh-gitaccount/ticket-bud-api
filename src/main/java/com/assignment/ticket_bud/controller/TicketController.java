package com.assignment.ticket_bud.controller;


	import com.assignment.ticket_bud.model.Receipt;
	import com.assignment.ticket_bud.model.User;
	import com.assignment.ticket_bud.service.TicketService;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.http.ResponseEntity;
	import org.springframework.web.bind.annotation.*;

	import java.util.List;

	@RestController
	@RequestMapping("/tickets")
	public class TicketController {
	    @Autowired
	    private TicketService ticketService;

	    @PostMapping("/purchase")
	    public ResponseEntity<Receipt> purchaseTicket(@RequestBody User user, @RequestParam String section) {
	        Receipt receipt = ticketService.purchaseTicket(user, section);
	        return ResponseEntity.ok(receipt);
	    }

	    @GetMapping("/receipt")
	    public ResponseEntity<Receipt> getReceipt(@RequestParam String email) {
	        Receipt receipt = ticketService.getReceipt(email);
	        return receipt != null ? ResponseEntity.ok(receipt) : ResponseEntity.notFound().build();
	    }

	    @GetMapping("/section/{section}")
	    public ResponseEntity<List<User>> getUsersBySection(@PathVariable String section) {
	        List<User> users = ticketService.getUsersBySection(section);
	        return ResponseEntity.ok(users);
	    }

	    @DeleteMapping("/remove")
	    public ResponseEntity<Void> removeUser(@RequestParam String email) {
	        ticketService.removeUser(email);
	        return ResponseEntity.noContent().build();
	    }

	    @PutMapping("/modify")
	    public ResponseEntity<Void> modifySeat(@RequestParam String email, @RequestParam String newSection) {
	        ticketService.modifySeat(email, newSection);
	        return ResponseEntity.noContent().build();
	    }
	}

