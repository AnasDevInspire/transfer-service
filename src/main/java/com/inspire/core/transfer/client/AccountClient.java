package com.inspire.core.transfer.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.inspire.core.transfer.dto.AccountDTO;

@FeignClient(name = "account-service")
public interface AccountClient {

    @GetMapping("/accounts/{id}/{type}")
    AccountDTO getAccount(@PathVariable("id") Long id,@PathVariable("type") String type);

    @PutMapping("/accounts/{id}/{newBalance}")
    void updateBalance(@PathVariable("id") Long id, @PathVariable double newBalance);
}
