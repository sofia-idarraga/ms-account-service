package com.aditya.multimodule.repository.adapters;

import com.aditya.multimodule.model.Account;
import com.aditya.multimodule.model.commons.ErrorCode;
import com.aditya.multimodule.model.commons.Result;
import com.aditya.multimodule.model.commons.ResultError;
import com.aditya.multimodule.repository.enitites.AccountEntity;
import com.aditya.multimodule.repository.repositories.AccountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AccountAdapterImpl implements AccountAdapter {

    private final AccountRepository repository;
    private final ModelMapper modelMapper;

    public AccountAdapterImpl(AccountRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Result<Account> save(Account model) {
        try {
            return new Result<>(entityToModel(repository.save(modelToEntity(model))));
        } catch (Exception exception) {
            return new Result<Account>().addError(new ResultError(ErrorCode.SERVER_ERROR,
                    exception.getMessage()));
        }
    }

    @Override
    public Result<Account> findById(Long aLong) {
        try {
            Optional<AccountEntity> result = repository.findById(aLong);
            if (result.isPresent()) {
                return new Result<>(
                        entityToModel(result.get()));
            } else {
                return new Result<Account>().addError(new ResultError(ErrorCode.NOT_FOUND,
                        "Item not found"));
            }
        } catch (Exception exception) {
            return new Result<Account>().addError(new ResultError(ErrorCode.SERVER_ERROR,
                    exception.getMessage()));
        }
    }

    @Override
    public Result<Account> update(Account account) {
        try {
            Optional<AccountEntity> optionalAccount = repository.findById(account.getNumber());

            if (optionalAccount.isPresent()) {

                AccountEntity existingAccount = optionalAccount.get();
                existingAccount.setClientNit(account.getClientNit());
                existingAccount.setType(account.getType().toString());
                existingAccount.setInitialBalance(account.getInitialBalance());
                existingAccount.setBalance(account.getBalance());
                existingAccount.setState(account.getState());

                repository.save(existingAccount);

                return new Result<>(entityToModel(existingAccount));
            } else {
                return new Result<Account>().addError(new ResultError(ErrorCode.NOT_FOUND,
                        "Account with Number " + account.getNumber() + " not found."));
            }
        } catch (Exception exception) {

            return new Result<Account>().addError(new ResultError(ErrorCode.SERVER_ERROR,
                    exception.getMessage()));
        }
    }

    @Override
    public Result<List<Account>> findAll(String direction) {
        try {
            Sort sort = Sort.by(direction.equals("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC, "number");
            return new Result<>(
                    repository.findAll(sort).stream()
                            .map(this::entityToModel).collect(Collectors.toList())
            );
        } catch (Exception exception) {
            return new Result<List<Account>>().addError(new ResultError(ErrorCode.SERVER_ERROR,
                    exception.getMessage()));
        }
    }

    @Override
    public Result<List<Account>> findAll(int pageNumber, int pageSize, String direction) {
        try {
            if (pageNumber <= 0 || pageSize <= 0) {
                return new Result<>((List.of()));
            }
            if (pageNumber == 1) {
                pageNumber = 0;
            }
            Sort sort = Sort.by(direction.equals("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC, "number");
            return new Result<>(
                    repository.findAll(sort)
                            .stream()
                            .skip((long) pageNumber * (long) pageSize)
                            .limit(pageSize)
                            .map(this::entityToModel)
                            .collect(Collectors.toList()));
        } catch (Exception exception) {
            return new Result<List<Account>>().addError(new ResultError(ErrorCode.SERVER_ERROR,
                    exception.getMessage()));
        }
    }

    @Override
    public Result<Boolean> deleteById(Long aLong) {
        try {
            repository.deleteById(aLong);
            return new Result<>(true);
        } catch (Exception exception) {
            return new Result<>(false);
        }
    }
    @Override
    public Result<Boolean> deactivateAccount(Long clientId) {
        try {
            List<AccountEntity> accounts = repository.findByClientNit(clientId);

            if (accounts.isEmpty()) {
                return new Result<Boolean>().addError(new ResultError(ErrorCode.NOT_FOUND, "No accounts found for client with Nit: " + clientId));
            }

            accounts.forEach(account -> account.setState(false));
            repository.saveAll(accounts);
            return new Result<Boolean>(true);
        } catch (Exception exception) {

            return new Result<Boolean>().addError(new ResultError(ErrorCode.SERVER_ERROR,
                    exception.getMessage()));
        }
    }

    private AccountEntity modelToEntity(Account model) {
        return modelMapper.map(model, AccountEntity.class);
    }

    private Account entityToModel(AccountEntity entity) {
        return modelMapper.map(entity, Account.class);
    }


}
