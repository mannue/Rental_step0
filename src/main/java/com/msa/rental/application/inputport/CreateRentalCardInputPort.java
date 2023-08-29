package com.msa.rental.application.inputport;

import com.msa.rental.application.outputport.RentalCardOutputPort;
import com.msa.rental.application.usecase.CreateRentalCardUsecase;
import com.msa.rental.domain.model.RentalCard;
import com.msa.rental.domain.model.vo.IDName;
import com.msa.rental.framework.web.dto.RentalCardOutputDTO;
import com.msa.rental.framework.web.dto.UserInputDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateRentalCardInputPort implements CreateRentalCardUsecase {
    private final RentalCardOutputPort rentalCardOutputPort;
    @Override
    public RentalCardOutputDTO createRentalCard(UserInputDTO userInputDTO) {
        RentalCard rentalCard = RentalCard.createRentalCard(new IDName(userInputDTO.getUserId(), userInputDTO.getUserName()));
        rentalCard = rentalCardOutputPort.save(rentalCard);
        return RentalCardOutputDTO.mapToDTO(rentalCard);
    }
}
