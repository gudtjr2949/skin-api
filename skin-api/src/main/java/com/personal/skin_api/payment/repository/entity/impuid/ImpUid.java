package com.personal.skin_api.payment.repository.entity.impuid;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class ImpUid {

    @Column(name = "IMP_UID")
    private String impUid;

    public ImpUid(final String impUid) {
        validate(impUid);
        this.impUid = impUid;
    }

    private void validate(final String impUid) {
        ImpUidStrategyContext.runStrategy(impUid);
    }

    public String getImpUid() {
        return impUid;
    }
}
