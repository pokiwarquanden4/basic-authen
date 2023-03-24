package com.example.basicauthen.Response;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class TokenResponse {
    private String token;
}