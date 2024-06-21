package com.ladyshopee.api.payload.response;

import com.ladyshopee.api.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtAuthenticationResponse implements Response{
    private String firstName;
    private String lastName;
    private String email;
    private Set<String> roles;
}
