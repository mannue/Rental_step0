package com.msa.rental.framework.web.dto;

import com.msa.rental.domain.model.RentalCard;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RentalCardOutputDTO {
    private String rentalCardId;
    private String memberId;
    private String memberName;
    private String rentStatus;
    private Long totalLateFee;
    private Long totalRentalCount;
    private Long totalReturnCount;
    private Long totalOverduedCount;

    public static RentalCardOutputDTO mapToDTO(final RentalCard rental) {
        return RentalCardOutputDTO.builder()
                .rentalCardId(rental.getRentalCardId().getNo())
                .memberId(rental.getMember().getId())
                .memberName(rental.getMember().getName())
                .rentStatus(rental.getRentStatus().name())
                .totalLateFee(rental.getTotalLateFee().getPoint())
                .totalRentalCount(rental.getTotalRentalItemCount())
                .totalReturnCount(rental.getTotalReturnItemCount())
                .totalOverduedCount(rental.getTotalOverdueRentalItemCount()).build();
    }
}
