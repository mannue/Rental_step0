package com.msa.rental.domain.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.time.LocalDate;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnItem {
    @Embedded
    private RentItem item;
    private LocalDate returnDate;

    public ReturnItem(final RentItem item) {
        this(item, LocalDate.now());
    }

    public static ReturnItem createReturnItem(final RentItem item) {
        return new ReturnItem(item);
    }

    public static ReturnItem sample() {
        return ReturnItem.createReturnItem(RentItem.sample());
    }
}
