package com.msa.rental.application.inputport;

import com.msa.rental.application.outputport.RentalCardOutputPort;
import com.msa.rental.application.usecase.OverdueItemUsecase;
import com.msa.rental.domain.model.RentalCard;
import com.msa.rental.domain.model.vo.Item;
import com.msa.rental.framework.web.dto.RentalCardOutputDTO;
import com.msa.rental.framework.web.dto.UserItemInputDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OverdueItemInputPort implements OverdueItemUsecase {
    private final RentalCardOutputPort rentalCardOutputPort;
    @Override
    public RentalCardOutputDTO overDueItem(UserItemInputDTO rentalDto) {
        RentalCard rental = rentalCardOutputPort.loadRentalCard(rentalDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 카드가 존재 하지 않습니다."));

        rental.overdueItem(new Item(rentalDto.getItemId(), rentalDto.getItemTitle()));

        rental = rentalCardOutputPort.save(rental);

        return RentalCardOutputDTO.mapToDTO(rental);
    }
}
