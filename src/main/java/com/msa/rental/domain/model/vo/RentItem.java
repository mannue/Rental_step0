package com.msa.rental.domain.model.vo;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;


@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Slf4j
public class RentItem {
    @Embedded
    private Item item;
    private LocalDate rentDate;
    private boolean overdued;
    private LocalDate overdueDate;

    public RentItem(Item item) {
        this(item, LocalDate.now(), false, LocalDate.now().plusDays(14L));
    }

    public static RentItem createRentItem(final Item item) {
        return new RentItem(item);
    }


    public static RentItem sample() {
        return RentItem.createRentItem(Item.sample());
    }

    public boolean isSameItem(final Item item) {
        return Objects.equals(this.item, item);
    }

    public boolean isOverdueDateFrom(final LocalDate returnDate) {
        return returnDate.isAfter(this.overdueDate);
    }

    public int checkOverduePeriodFrom(final LocalDate returnDate) {
        final int overduePeriod = Period.between(overdueDate, returnDate).getDays();
        return Math.max(0, overduePeriod);
    }

    public void updateOverdue() {
        this.overdued = true;
    }
}
