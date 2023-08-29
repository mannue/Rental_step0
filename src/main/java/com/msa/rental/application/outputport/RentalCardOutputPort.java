package com.msa.rental.application.outputport;

import com.msa.rental.domain.model.RentalCard;

import java.util.Optional;


public interface RentalCardOutputPort {
    public Optional<RentalCard> loadRentalCard(final String userId);
    public RentalCard save(RentalCard rentalCard);
}
