package com.crio.RentRead.service;

import com.crio.RentRead.dto.RentalDTO;
import com.crio.RentRead.entity.Book;
import com.crio.RentRead.entity.Rental;
import com.crio.RentRead.entity.User;
import com.crio.RentRead.repository.BookRepository;
import com.crio.RentRead.repository.RentalRepository;
import com.crio.RentRead.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

import java.util.List;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    public void rentBook(Long bookId) {
        User user = getAuthenticatedUser(); // Implement this method
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));

        if (!book.isAvailable()) {
            throw new RuntimeException("Book is not available for rent");
        }

        List<Rental> activeRentals = rentalRepository.findByUserAndActive(user, true);
        if (activeRentals.size() >= 2) {
            throw new RuntimeException("Cannot rent more than 2 books at a time");
        }

        Rental rental = new Rental();
        rental.setUser(user);
        rental.setBook(book);
        rental.setActive(true);
        rentalRepository.save(rental);

        book.setAvailable(false);
        bookRepository.save(book);
    }

    public void returnBook(Long bookId) {
        User user = getAuthenticatedUser();

        // Fetch the rental using user and bookId, this will return a Rental object
        Rental rental = rentalRepository.findByUserAndBookId(user, bookId)
                .orElseThrow(() -> new RuntimeException("Rental not found"));

        // Mark the rental as inactive
        rental.setActive(false);
        rentalRepository.save(rental); // Save the updated rental status

        // Update the book's availability status
        Book book = rental.getBook();
        book.setAvailable(true);
        bookRepository.save(book); // Save the updated book availability
    }

    public List<RentalDTO> getMyRentals() {
        User user = getAuthenticatedUser();
        List<Rental> rentals = rentalRepository.findByUserAndActive(user, true);

        return rentals.stream().map(rental -> {
            String username = user.getUsername(); // Directly from the authenticated user
            String title = rental.getBook().getTitle(); // Get the book title
            return new RentalDTO(user.getId(), rental.getBook().getId(), title, username);
        }).toList();
    }

    private User getAuthenticatedUser() {
        // Fetch the authentication object from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("No authenticated user found");
        }

        // Get the user details from the authentication object
        Object principal = authentication.getPrincipal();

        // Check if the principal is an instance of UserDetails
        if (principal instanceof UserDetails) {
            String email = ((UserDetails) principal).getUsername(); // Get the username (in this case, email)

            // Fetch the User entity from the database using the email
            return userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Authenticated user not found in the database"));
        } else {
            throw new RuntimeException("Principal is not an instance of UserDetails");
        }
    }
}
