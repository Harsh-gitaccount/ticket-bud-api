package com.assignment.ticket_bud.service;


	import com.assignment.ticket_bud.model.Receipt;
	import com.assignment.ticket_bud.model.User;
	import org.springframework.stereotype.Service;

	import java.util.*;

	@Service
	public class TicketService {
	    private Map<String, Receipt> receipts = new HashMap<>();
	    private Map<String, User> users = new HashMap<>();
	    private Map<String, List<String>> sections = new HashMap<>();

	    public TicketService() {
	        sections.put("A", new ArrayList<>());
	        sections.put("B", new ArrayList<>());
	    }

	    public Receipt purchaseTicket(User user, String section) {
	        if (!sections.containsKey(section) || sections.get(section).size() >= 10) {
	            throw new IllegalArgumentException("Invalid or full section");
	        }

	        String seat = section + (sections.get(section).size() + 1);
	        user.setSeat(seat);
	        users.put(user.getEmail(), user);
	        sections.get(section).add(seat);

	        Receipt receipt = new Receipt("London", "France", user, 20.0);
	        receipts.put(user.getEmail(), receipt);
	        return receipt;
	    }

	    public Receipt getReceipt(String email) {
	        return receipts.get(email);
	    }

	    public List<User> getUsersBySection(String section) {
	        if (!sections.containsKey(section)) {
	            throw new IllegalArgumentException("Invalid section");
	        }

	        List<User> sectionUsers = new ArrayList<>();
	        for (String seat : sections.get(section)) {
	            for (User user : users.values()) {
	                if (user.getSeat().equals(seat)) {
	                    sectionUsers.add(user);
	                }
	            }
	        }
	        return sectionUsers;
	    }

	    public void removeUser(String email) {
	        User user = users.remove(email);
	        if (user != null) {
	            String seat = user.getSeat();
	            sections.get(seat.substring(0, 1)).remove(seat);
	            receipts.remove(email);
	        }
	    }

	    public void modifySeat(String email, String newSection) {
	        User user = users.get(email);
	        if (user == null) {
	            throw new IllegalArgumentException("User not found");
	        }

	        String currentSeat = user.getSeat();
	        String currentSection = currentSeat.substring(0, 1);
	        if (sections.get(newSection).size() >= 10) {
	            throw new IllegalArgumentException("Section is full");
	        }

	        sections.get(currentSection).remove(currentSeat);
	        String newSeat = newSection + (sections.get(newSection).size() + 1);
	        user.setSeat(newSeat);
	        sections.get(newSection).add(newSeat);
	    }
	}


