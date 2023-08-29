package com.msa.rental.domain.model;

import com.msa.rental.domain.model.vo.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class RentalCard {
    @EmbeddedId
    private RentalCardNO rentalCardId;

    @Embedded
    private IDName member;
    @Enumerated(EnumType.STRING)
    private RentStatus rentStatus;
    @Embedded
    private LateFee totalLateFee;
    @ElementCollection
    private final List<RentItem> rentItemList = new ArrayList<>();
    @ElementCollection
    private final List<ReturnItem> returnItemList = new ArrayList<>();

    public RentalCard(final RentalCardNO rentalCardId, final IDName idName) {
        this(rentalCardId, idName, RentStatus.RENT_AVAILABLE, LateFee.createLateFee());
    }

    public RentalCard(final RentalCardNO rentalCardId, final IDName idName, final RentStatus rentStatus, final LateFee totalLateFee) {
        this.rentalCardId = rentalCardId;
        this.member = idName;
        this.rentStatus = rentStatus;
        this.totalLateFee = totalLateFee;
    }

    public static RentalCard sample() {
        return new RentalCard(RentalCardNO.sample(), IDName.sample(), com.msa.rental.domain.model.vo.RentStatus.RENT_AVAILABLE, LateFee.sample());
    }

    public static RentalCard createRentalCard(final IDName creator) {
        return new RentalCard(RentalCardNO.createRentalCardNo(), creator);
    }

    public RentalCard rentItem(final Item item) {
        checkRentalAvailable();
        this.addRentItem(RentItem.createRentItem(item));
        return this;
    }

    public RentalCard returnItem(final Item item, final LocalDate returnDate) {
        final RentItem rentItem = this.rentItemList.stream()
                .filter(obj -> obj.isSameItem(item))
                .findFirst().orElseThrow(() -> new IllegalStateException("대여한 이력이 없습니다."));

        calculateLateFee(rentItem, returnDate);
        this.returnItemList.add(ReturnItem.createReturnItem(rentItem));
        this.rentItemList.remove(rentItem);
        return this;
    }

    public RentalCard overdueItem(final Item item) {
        final RentItem overdueItem = this.rentItemList.stream()
                .filter(obj -> obj.isSameItem(item))
                .findFirst().orElseThrow(() -> new IllegalStateException("대여된 정보가 없습니다."));
        overdueItem.updateOverdue();
        this.rentStatus = RentStatus.RENT_UNAVAILABLE;

        //ToDo 연체를 억지로 만들기 - 실제로는 필요없는 코드
        overdueItem.setOverdueDate(LocalDate.now().minusDays(1L));
        return this;
    }

    public long makeAvailableRental(final long point) {
        if (!this.rentItemList.isEmpty()) {
            throw new IllegalStateException("모든 도서가 반납되어야 정지를 해제 할 수 있습니다.");
        }
        if (!this.totalLateFee.isEnough(point)) {
            throw new IllegalArgumentException("해당 포인트로 연체를 해제할 수 없습니다.");
        }
        this.totalLateFee = this.totalLateFee.removePoint(point);
        this.rentStatus = RentStatus.RENT_AVAILABLE;
        return this.totalLateFee.getPoint();
    }

    public void addRentItem(final RentItem rentItem) {
        this.rentItemList.add(rentItem);
    }

    public void removeRentItem(final RentItem rentItem) {
        this.rentItemList.remove(rentItem);
    }

    public void addReturnItem(final ReturnItem returnItem) {
        this.returnItemList.add(returnItem);
    }

    public long getTotalRentalItemCount() {
        return this.rentItemList.size();
    }

    public long getTotalReturnItemCount() {
        return this.returnItemList.size();
    }

    public long getTotalOverdueRentalItemCount() {
        return this.rentItemList.stream().filter(RentItem::isOverdued).count();
    }

    private void calculateLateFee(RentItem rentItem, LocalDate returnDate) {
        if (!rentItem.isOverdueDateFrom(returnDate)) return;
        System.out.println("연체 기간 발생!!!");
        final int point = rentItem.checkOverduePeriodFrom(returnDate) * 10;
        this.totalLateFee = this.totalLateFee.addPoint(point);
    }
    private void checkRentalAvailable() {
        if (Objects.equals(rentStatus, RentStatus.RENT_UNAVAILABLE)) {
            throw new IllegalStateException("대여불가 상태입니다.");
        }
        if (this.rentItemList.size() > 5) {
            throw new IllegalStateException("이미 5권을 대여했습니다.");
        }
    }
}
