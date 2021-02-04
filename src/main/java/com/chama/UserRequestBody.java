package com.chama;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestBody {
private String user;
private String email;
private String amount;
private String borrower;
private String lender;
}
