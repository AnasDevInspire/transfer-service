package com.inspire.core.transfer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inspire.core.transfer.entity.Transfer;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
}
