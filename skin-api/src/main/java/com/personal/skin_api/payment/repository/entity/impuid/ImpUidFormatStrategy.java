package com.personal.skin_api.payment.repository.entity.impuid;

import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.common.exception.payment.ImpUidErrorCode;

public class ImpUidFormatStrategy implements ImpUidValidationStrategy {

    @Override
    public void validate(final String impUid) {
        if (!impUid.startsWith("imp_"))
            throw new RestApiException(ImpUidErrorCode.INVALID_IMP_UID_FORMAT);
    }
}
