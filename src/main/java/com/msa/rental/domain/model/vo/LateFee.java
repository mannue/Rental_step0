package com.msa.rental.domain.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LateFee {
    private long point;

    public LateFee addPoint(final long point) {
        return new LateFee(this.point + point);
    }

    public LateFee removePoint(final long point) {
        if (this.point < point) {
            throw new IllegalStateException("보유한 포인트보다 커서 삭제 할수 없습니다.");
        }
        return new LateFee(Math.subtractExact(this.point, point));
    }

    public static LateFee sample() {
        return LateFee.createLateFee();
    }

    public static LateFee createLateFee() {
        return new LateFee(0);
    }

    public boolean isEnough(long point) {
        return this.point <= point;
    }
}
