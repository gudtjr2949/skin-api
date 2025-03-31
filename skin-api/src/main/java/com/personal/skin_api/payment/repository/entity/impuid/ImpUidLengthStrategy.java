package com.personal.skin_api.payment.repository.entity.impuid;

import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.common.exception.payment.ImpUidErrorCode;

public class ImpUidLengthStrategy implements ImpUidValidationStrategy {

    @Override
    public void validate(final String impUid) {
        if (impUid.length() != 17)
            throw new RestApiException(ImpUidErrorCode.INVALID_IMP_UID_LENGTH);
    }
}
