package com.crio.RentRead.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalDTO {
    private Long userId;
    private Long bookId;
    private String title;
    private String username;
}
