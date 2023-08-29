package com.msa.rental.framework.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ClearOverdueInputDTO {
    private final String userId;
    private final String userName;
    private final int point;
}
