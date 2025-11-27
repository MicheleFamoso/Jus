package com.Michele.Jus.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class RicettaPageDto {
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    List<RicettaClientDto> ricette;
}
