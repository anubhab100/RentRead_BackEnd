package com.crio.RentRead.repository;

import com.crio.RentRead.entity.Rental;
import com.crio.RentRead.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface RentalRepository extends JpaRepository<Rental, Long> {

    Optional<Rental> findByUserAndBookId(User user, Long bookId);

    List<Rental> findByUserAndActive(User user, boolean active);
}
