package io.github.krysiel86.mobile.api.exceptions;

@Deprecated
public class InitError {

    private String error;

    public InitError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

}
