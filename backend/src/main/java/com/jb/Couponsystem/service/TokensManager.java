package com.jb.Couponsystem.service;

import com.jb.Couponsystem.rest.common.ClientSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Service
public class TokensManager {

    private static final long DELETE_TOKENS_RATE = 300_000;
    private static final long TOKENS_LIFE = 1_800_000;

    private final Map<String, ClientSession> tokensMap;

    @Autowired
    public TokensManager(@Qualifier("tokens") Map<String, ClientSession> tokensMap) {
        this.tokensMap = tokensMap;
    }

    public ClientSession put(String token, ClientSession clientSession) {
        return tokensMap.put(token, clientSession);
    }

    public <X extends Throwable> ClientSession accessOrElseThrow(String token, Supplier<? extends X> supplier) throws X {
        if (tokensMap.containsKey(token)) {
            return tokensMap.get(token).access();
        }
        throw supplier.get();
    }

    @Scheduled(fixedRate = DELETE_TOKENS_RATE)
    public void deleteExpiredTokens() {
        for (Map.Entry<String, ClientSession> entry : tokensMap.entrySet()) {
            if (System.currentTimeMillis() - entry.getValue().getLastAccessMillis() > TOKENS_LIFE) {
                tokensMap.remove(entry.getKey());
            }
        }
    }
}

