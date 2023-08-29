package com.msa.rental.framework.web.dto;

import com.msa.rental.domain.model.vo.ReturnItem;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ReturnItemOutputDTO {
    private String itemNo;
    private String itemtitle;
    private LocalDate returnDate;


    public static ReturnItemOutputDTO mapToDTO(final ReturnItem returnItem) {
        return ReturnItemOutputDTO.builder()
                .itemNo(returnItem.getItem().getItem().getNo())
                .itemtitle(returnItem.getItem().getItem().getTitle())
                .returnDate(returnItem.getReturnDate())
                .build();
    }
}
