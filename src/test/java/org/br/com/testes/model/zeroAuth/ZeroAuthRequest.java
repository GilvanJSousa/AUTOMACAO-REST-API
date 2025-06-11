package org.br.com.testes.model.zeroAuth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZeroAuthRequest {
    private String cardToken;
    private String cardType;
    private String cardNumber;
    private String holder;
    private String expirationDate;
    private String securityCode;
    private String saveCard;
    private String brand;
} 