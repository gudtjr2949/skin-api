package com.personal.skin_api.payment.repository.entity.impuid;

import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.common.exception.payment.ImpUidErrorCode;

public class ImpUidNullStrategy implements ImpUidValidationStrategy {

    @Override
    public void validate(final String impUid) {
        if (impUid == null || impUid.isEmpty() || impUid.isBlank())
            throw new RestApiException(ImpUidErrorCode.IMP_UID_CAN_NOT_BE_NULL);
    }
}
