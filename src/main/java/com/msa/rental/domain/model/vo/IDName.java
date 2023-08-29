package com.msa.rental.domain.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IDName {
    private String id;
    private String name;

    public static IDName sample() {
        return new IDName("mannue", "eunnam.song");
    }
}
