package com.msa.rental.application.inputport;

import com.msa.rental.application.outputport.RentalCardOutputPort;
import com.msa.rental.application.usecase.ClearOverdueUsecase;
import com.msa.rental.domain.model.RentalCard;
import com.msa.rental.framework.web.dto.ClearOverdueInputDTO;
import com.msa.rental.framework.web.dto.RentalResultOutputDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ClearOverdueInputPort implements ClearOverdueUsecase {
    final RentalCardOutputPort rentalCardOutputPort;

    @Override
    public RentalResultOutputDTO clearOverdue(ClearOverdueInputDTO clearOverdueInputDto) {
        RentalCard loadRentalCard = rentalCardOutputPort.loadRentalCard(clearOverdueInputDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 카드가 존재하지 않습니다."));

        loadRentalCard.makeAvailableRental(clearOverdueInputDto.getPoint());

        loadRentalCard = rentalCardOutputPort.save(loadRentalCard);

        return RentalResultOutputDTO.mapToDTO(loadRentalCard);
    }
}
