package com.msa.rental.application.usecase;

import com.msa.rental.framework.web.dto.ClearOverdueInputDTO;
import com.msa.rental.framework.web.dto.RentalResultOutputDTO;

public interface ClearOverdueUsecase {
    public RentalResultOutputDTO clearOverdue(final ClearOverdueInputDTO clearOverdueInputDto);
}
