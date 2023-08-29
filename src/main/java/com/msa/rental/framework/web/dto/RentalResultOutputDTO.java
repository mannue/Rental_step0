package com.msa.rental.framework.web.dto;

import com.msa.rental.domain.model.RentalCard;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RentalResultOutputDTO {
    private String userId;
    private String userName;
    private long rentedTotalItemCount;
    private long totalLateFee;

    public static RentalResultOutputDTO mapToDTO(final RentalCard rental) {
        return RentalResultOutputDTO.builder()
                .userId(rental.getMember().getId())
                .userName(rental.getMember().getName())
                .rentedTotalItemCount(rental.getTotalRentalItemCount())
                .totalLateFee(rental.getTotalLateFee().getPoint())
                .build();
    }
}
