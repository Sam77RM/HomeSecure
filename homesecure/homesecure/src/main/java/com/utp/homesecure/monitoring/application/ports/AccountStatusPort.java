package com.utp.homesecure.monitoring.application.ports;

/** Port implemented by an adapter that reads the User module's account status. */
public interface AccountStatusPort {
    boolean existsAndIsActive(String userAccountId);
}
