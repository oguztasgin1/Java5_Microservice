package com.oguztasgin.dto.request;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FindAllPageRequestDto {
    String direction;
    Integer currentPage;
    int pageSize;
    String sortingParameter;
}
