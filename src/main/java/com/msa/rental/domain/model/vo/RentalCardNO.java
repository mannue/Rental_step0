package com.msa.rental.domain.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;


@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalCardNO implements Serializable {
    private static final long serialVersionUID = -3856668825638769693L;
    private String no;

    public static RentalCardNO createRentalCardNo() {
        final UUID uuid = UUID.randomUUID();
        final String year = String.valueOf(LocalDate.now().getYear());
        final String str = year + '-' + uuid;
        return new RentalCardNO(str);
    }

    public static RentalCardNO sample() {
        return RentalCardNO.createRentalCardNo();
    }
}
