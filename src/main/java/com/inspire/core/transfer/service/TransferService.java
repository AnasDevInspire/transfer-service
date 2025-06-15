package com.inspire.core.transfer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inspire.core.transfer.client.AccountClient;
import com.inspire.core.transfer.client.EmployeeClient;
import com.inspire.core.transfer.dto.AccountDTO;
import com.inspire.core.transfer.dto.EmployeeDTO;
import com.inspire.core.transfer.entity.Transfer;
import com.inspire.core.transfer.repository.TransferRepository;

@Service
public class TransferService {

	@Autowired
	private TransferRepository repository;

	@Autowired
	private EmployeeClient employeeClient;

	@Autowired
	private AccountClient accountClient;

	//////////////////////////////
	public Transfer processSalary(Transfer transfer) {
		try {
			// Step 1: Get employee info
			EmployeeDTO employee = employeeClient.getEmployeeById(transfer.getEmployeeId());

			// Step 2: Get employee account
			AccountDTO employeeAccount = accountClient.getAccount(employee.getAccountId());

			// Step 3: Get company account (hardcoded company account ID = 1 for now)
			AccountDTO companyAccount = accountClient.getAccount(1L);

			double amount = transfer.getAmount();

			// Step 4: Check balance
			if (companyAccount.getBalance() < amount) {
				throw new RuntimeException("Insufficient company balance");
			}

			// Step 5: Update balances
			accountClient.updateBalance(1L, companyAccount.getBalance() - amount);
			accountClient.updateBalance(employeeAccount.getId(), employeeAccount.getBalance() + amount);

			transfer.setStatus("SUCCESS");
			transfer.setMessage("Transfer completed.");

		} catch (Exception e) {
			transfer.setStatus("FAILED");
			transfer.setMessage("Transfer failed: " + e.getMessage());
		}

		return repository.save(transfer);
	}

	//////////////////////////////
	public List<Transfer> getAllTransfers() {
		return repository.findAll();
	}
}
