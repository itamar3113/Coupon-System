package com.jb.Couponsystem.rest.common;

public class ClientSession {

    private final long clientId;
    private long lastAccessMillis;
    //true for company, false for customer.
    private final boolean isCompany;

    private ClientSession(long clientId, long lastAccessMillis, boolean isCompany) {
        this.clientId = clientId;
        this.lastAccessMillis = lastAccessMillis;
        this.isCompany = isCompany;
    }

    public static ClientSession of(long clientId, boolean isCompany) {
        return new ClientSession(clientId,System.currentTimeMillis(),isCompany);
    }

    public long getClientId() {
        return clientId;
    }

    public long getLastAccessMillis() {
        return lastAccessMillis;
    }

    public boolean isCompany() {
        return isCompany;
    }

    public ClientSession access() {
        lastAccessMillis = System.currentTimeMillis();
        return this;
    }
}
