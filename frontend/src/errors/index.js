class BaseError extends Error {
    constructor(message = 'server error', code = '0') {
        super();
        this.message = message;
        this.code = code;
        this.statusCode = 500;
    }
}

module.exports.BaseError = BaseError;

module.exports.ErrorBadRequest = class ErrorBadRequest extends BaseError {
    constructor(message = 'BadRequest', code = '0') {
        super();
        this.statusCode = 400;
        this.message = message;
        this.code = code;
    }
};

module.exports.ErrorUnauthorized = class ErrorUnauthorized extends BaseError {
    constructor(message = 'Unauthorized', code = '0') {
        super();
        this.statusCode = 401;
        this.message = message;
        this.code = code;
    }
};

module.exports.ErrorNotFound = class ErrorNotFound extends BaseError {
    constructor(message = 'Not Found', code = '0') {
        super();
        this.statusCode = 404;
        this.message = message;
        this.code = code;
    }
};

module.exports.ErrorForbidden = class ErrorForbidden extends BaseError {
    constructor(message = 'Forbidden', code = '0') {
        super();
        this.statusCode = 403;
        this.message = message;
        this.code = code;
    }
};
