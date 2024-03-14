package com.gbtec.interview.email_server.validation;


/**
 * This class is used as validation group in order to avoid problem with PATCH request.
 * POST/creating email require EmailFrom field to be NOT EMPTY, however when making partial updates with PATCH,
 * it makes sense to avoid NOT EMPTY constraints as the client may not update all fields.
 */
public interface ComposeValidation {
}
