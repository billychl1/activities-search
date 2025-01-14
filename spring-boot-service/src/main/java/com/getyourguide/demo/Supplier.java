package com.getyourguide.demo;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Supplier {
    private Long id;
    private String name;
    private String address;
    private String zip;
    private String city;
    private String country;
}
