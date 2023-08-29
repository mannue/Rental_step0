package com.msa.rental.framework.web.dto;

import com.msa.rental.domain.model.vo.RentItem;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class RentItemOutputDTO {
    private final String itemTitle;
    private final LocalDate rentDate;
    private final boolean overdued;
    private final LocalDate overdueDate;

    public static RentItemOutputDTO mapToDTO(final RentItem rentItem) {
        return RentItemOutputDTO.builder()
                .itemTitle(rentItem.getItem().getTitle())
                .rentDate(rentItem.getRentDate())
                .overdued(rentItem.isOverdued())
                .overdueDate(rentItem.getOverdueDate())
                .build();
    }
}
