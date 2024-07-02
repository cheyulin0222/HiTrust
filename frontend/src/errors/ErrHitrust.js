const { BaseError } = require('.');

const ErrHitrustCode = {
    BUSINESS_001: 'BUSINESS_001',
    BUSINESS_002: 'BUSINESS_002',
    BUSINESS_003: 'BUSINESS_003',
    BUSINESS_004: 'BUSINESS_004',
    BUSINESS_005: 'BUSINESS_005',
    BUSINESS_006: 'BUSINESS_006',
    BUSINESS_007: 'BUSINESS_007',
    REQUEST_001: 'REQUEST_001',
    DATABASE_001: 'DATABASE_001',
    AUTHORITY_001: 'AUTHORITY_001',
    SYSTEM_001: 'SYSTEM_001',
};
module.exports.ErrHitrustCode = ErrHitrustCode;

module.exports.ErrHitrust = class ErrHitrust extends BaseError {
    constructor(msg, code) {
        super();
        const { message, status } = this.getCodeError(code, msg);
        this.statusCode = status;
        this.message = message;
        this.code = code;
    }

    getCodeError(code, message) {
        switch (code) {
            case ErrHitrustCode.BUSINESS_001:
                return { message, status: 403 };
            case ErrHitrustCode.BUSINESS_002:
                return { message, status: 403 };
            case ErrHitrustCode.BUSINESS_003:
                return { message, status: 403 };
            case ErrHitrustCode.BUSINESS_004:
                return { message, status: 400 };
            case ErrHitrustCode.BUSINESS_005:
                return { message, status: 400 };
            case ErrHitrustCode.BUSINESS_006:
                return { message, status: 403 };
            case ErrHitrustCode.BUSINESS_007:
                return { message, status: 403 };
            case ErrHitrustCode.REQUEST_001:
                return { message, status: 500 };
            case ErrHitrustCode.DATABASE_001:
                return { message, status: 500 };
            case ErrHitrustCode.AUTHORITY_001:
                return { message, status: 401 };
            case ErrHitrustCode.SYSTEM_001:
                return { message, status: 500 };
            default:
                return { message, status: 500 };
        }
    }
};
