package com.msa.rental.application.inputport;

import com.msa.rental.application.outputport.RentalCardOutputPort;
import com.msa.rental.application.usecase.RentItemUsecase;
import com.msa.rental.domain.model.RentalCard;
import com.msa.rental.domain.model.vo.IDName;
import com.msa.rental.domain.model.vo.Item;
import com.msa.rental.framework.web.dto.RentalCardOutputDTO;
import com.msa.rental.framework.web.dto.UserItemInputDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RentItemInputPort implements RentItemUsecase {
    private final RentalCardOutputPort rentalCardOutputPort;
    @Override
    public RentalCardOutputDTO rentItem(UserItemInputDTO returnDto) {
        RentalCard rentalCard = this.rentalCardOutputPort.loadRentalCard(returnDto.getUserId())
                .orElse(RentalCard.createRentalCard(new IDName(returnDto.getUserId(), returnDto.getUserName())));

        rentalCard.rentItem(new Item(returnDto.getItemId(), returnDto.getItemTitle()));

        rentalCard = rentalCardOutputPort.save(rentalCard);

        return RentalCardOutputDTO.mapToDTO(rentalCard);
    }
}
