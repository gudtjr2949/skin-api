package com.personal.skin_api.payment.repository.entity.impuid;

import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.common.exception.payment.ImpUidErrorCode;

public class ImpUidLengthStrategy implements ImpUidValidationStrategy {

    public static final int IMP_UID_LENGTH = 16;

    @Override
    public void validate(final String impUid) {
        if (impUid.length() != IMP_UID_LENGTH)
            throw new RestApiException(ImpUidErrorCode.INVALID_IMP_UID_LENGTH);
    }
}
