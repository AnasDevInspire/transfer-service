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
			EmployeeDTO employee = employeeClient.getEmployeeById(transfer.getEmployeeId());
			AccountDTO employeeAccount = accountClient.getAccount(employee.getAccountId(), "EMPLOYEE");
			AccountDTO companyAccount = accountClient.getAccount(1L, "COMPANY");
			double amount = transfer.getAmount();

			if (companyAccount.getBalance() < amount) {
				throw new RuntimeException("Insufficient company balance !");
			}

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
