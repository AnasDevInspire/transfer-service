package com.inspire.core.transfer.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.inspire.core.transfer.dto.EmployeeDTO;

@FeignClient(name = "employee-service")
public interface EmployeeClient {

    @GetMapping("/employees/{id}")
    EmployeeDTO getEmployeeById(@PathVariable("id") Long id);
}
