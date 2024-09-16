package com.crio.RentRead;

import com.crio.RentRead.dto.RentalDTO;
import com.crio.RentRead.service.RentalService;
import com.crio.RentRead.controller.RentalController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(RentalController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RentalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RentalService rentalService;

    private RentalDTO rentalDTO;

    @BeforeEach
    public void setup() {
        rentalDTO = new RentalDTO();
        rentalDTO.setBookId(1L);
        rentalDTO.setUserId(1L);
        // Set other fields as needed
    }

    // Test for renting a book with user role
    @Test
    @WithMockUser(username = "user@example.com", roles = { "USER" })
    public void testRentBookAsUser() throws Exception {
        Mockito.doNothing().when(rentalService).rentBook(anyLong());

        mockMvc.perform(post("/rentals/books/1/rent")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Expect 200 OK for user
                .andExpect(jsonPath("$").value("Book rented successfully."))
                .andDo(print());
    }

    // Test for returning a book with user role
    @Test
    @WithMockUser(username = "user@example.com", roles = { "USER" })
    public void testReturnBookAsUser() throws Exception {
        Mockito.doNothing().when(rentalService).returnBook(anyLong());

        mockMvc.perform(post("/rentals/books/1/return")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Expect 200 OK for user
                .andExpect(jsonPath("$").value("Book returned successfully."))
                .andDo(print());
    }

    // Test for getting all rentals for the authenticated user
    @Test
    @WithMockUser(username = "user@example.com", roles = { "USER" })
    public void testGetMyRentals() throws Exception {
        List<RentalDTO> rentals = Collections.singletonList(rentalDTO);
        Mockito.when(rentalService.getMyRentals()).thenReturn(rentals);

        mockMvc.perform(MockMvcRequestBuilders.get("/rentals/my-rentals")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Expect 200 OK for authenticated user
                .andExpect(jsonPath("$[0].bookId").value(1L))
                .andDo(print());
    }
}
