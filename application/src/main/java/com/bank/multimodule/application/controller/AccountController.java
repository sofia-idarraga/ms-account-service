package com.bank.multimodule.application.controller;


import com.bank.multimodule.model.Account;
import com.bank.multimodule.model.commons.Result;
import com.bank.multimodule.service.AccountUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.bank.multimodule.application.controller.utilities.ControllerUtils.processResult;

@RestController
@RequestMapping("/api/cuentas")
public class AccountController extends AbstractCrudController<Account, Long, AccountUseCase> {


    public AccountController(AccountUseCase accountUseCase) {
        super(accountUseCase);
    }

    @PostMapping("/nuevo")
    public ResponseEntity<Result<Account>> saveAccount(@RequestBody Account client) {
        return processResult(useCase.createAccount(client));
    }

    @PutMapping("/{number}")
    public ResponseEntity<Result<Account>> updateClientInformation(@PathVariable Long number, @RequestBody Account account) {
        account.setNumber(number);
        Result<Account> result = useCase.updateAccountInformation(account);
        return processResult(result);
    }

    @PutMapping("/desactivar/{nit}")
    public ResponseEntity<Result<Boolean>> deactivateClient(@PathVariable String nit) {
        Result<Boolean> result = useCase.deactivateAccounts(Long.parseLong(nit));
        return processResult(result);
    }


}