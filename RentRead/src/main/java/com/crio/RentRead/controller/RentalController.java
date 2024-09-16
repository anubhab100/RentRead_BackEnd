package com.crio.RentRead.controller;

import com.crio.RentRead.dto.RentalDTO;
import com.crio.RentRead.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/rentals")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    // Rent a book (Private, User/Admin)
    @PostMapping("/books/{bookId}/rent")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<String> rentBook(@PathVariable Long bookId) {
        rentalService.rentBook(bookId);
        return ResponseEntity.ok("Book rented successfully.");
    }

    // Return a book (Private, User/Admin)
    @PostMapping("/books/{bookId}/return")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<String> returnBook(@PathVariable Long bookId) {
        rentalService.returnBook(bookId);
        return ResponseEntity.ok("Book returned successfully.");
    }

    // Get all rentals for the authenticated user (Private, User/Admin)
    @GetMapping("/my-rentals")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<RentalDTO>> getMyRentals() {
        List<RentalDTO> rentals = rentalService.getMyRentals();
        return ResponseEntity.ok(rentals);
    }
}
