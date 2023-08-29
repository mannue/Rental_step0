package com.msa.rental.application.inputport;

import com.msa.rental.application.outputport.RentalCardOutputPort;
import com.msa.rental.application.usecase.InquiryUsecase;
import com.msa.rental.domain.model.RentalCard;
import com.msa.rental.framework.web.dto.RentItemOutputDTO;
import com.msa.rental.framework.web.dto.RentalCardOutputDTO;
import com.msa.rental.framework.web.dto.ReturnItemOutputDTO;
import com.msa.rental.framework.web.dto.UserInputDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class InquiryInputPort implements InquiryUsecase {
    private final RentalCardOutputPort rentalCardOutputPort;

    @Override
    public RentalCardOutputDTO getRentalCard(UserInputDTO userInputDTO) {
        RentalCard loadedRentalCard = findRentalCard(userInputDTO);
        return RentalCardOutputDTO.mapToDTO(loadedRentalCard);
    }

    @Override
    public List<RentItemOutputDTO> getAllRentItem(UserInputDTO userInputDTO) {
        RentalCard loadedRentalCard = findRentalCard(userInputDTO);
        return loadedRentalCard.getRentItemList().stream()
                .map(RentItemOutputDTO::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReturnItemOutputDTO> getAllReturnItem(UserInputDTO userInputDTO) {
        RentalCard loadedRentalCard = findRentalCard(userInputDTO);
        return loadedRentalCard.getReturnItemList().stream()
                .map(ReturnItemOutputDTO::mapToDTO)
                .collect(Collectors.toList());
    }

    private RentalCard findRentalCard(final UserInputDTO userInputDTO) {
        return rentalCardOutputPort.loadRentalCard(userInputDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 카드가 존재하지 않습니다."));
    }
}
