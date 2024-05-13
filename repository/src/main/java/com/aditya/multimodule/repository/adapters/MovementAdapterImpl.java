package com.aditya.multimodule.repository.adapters;

import com.aditya.multimodule.model.Account;
import com.aditya.multimodule.model.Movement;
import com.aditya.multimodule.model.MovementType;
import com.aditya.multimodule.model.commons.ErrorCode;
import com.aditya.multimodule.model.commons.Result;
import com.aditya.multimodule.repository.enitites.MovementEntity;
import com.aditya.multimodule.repository.repositories.MovementRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.aditya.multimodule.model.commons.ErrorCode.NOT_FOUND;
import static com.aditya.multimodule.model.commons.ErrorCode.SERVER_ERROR;

@Component
public class MovementAdapterImpl implements MovementAdapter {

    private final MovementRepository repository;
    private final ModelMapper modelMapper;

    public MovementAdapterImpl(MovementRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Result<Movement> save(Movement model) {
        try {
            model.setDate(LocalDate.now());
            return new Result<>(entityToModel(repository.save(modelToEntity(model))));
        } catch (Exception exception) {
            return Result.errorResult(SERVER_ERROR,
                    exception.getMessage());
        }
    }

    @Override
    public Result<Movement> findById(Long id) {
        try {
            Optional<MovementEntity> result = repository.findById(id);
            if (result.isPresent()) {
                return new Result<>(
                        entityToModel(result.get()));
            } else {
                return Result.errorResult(NOT_FOUND,
                        "Movement not found");
            }
        } catch (Exception exception) {
            return Result.errorResult(SERVER_ERROR,
                    exception.getMessage());
        }
    }

    @Override
    public Result<Movement> update(Movement movement) {
        try {
            Optional<MovementEntity> optionalMovement = repository.findById(movement.getId());

            if (optionalMovement.isPresent()) {

                MovementEntity existingMovement = optionalMovement.get();
                existingMovement.setAccount(existingMovement.getAccount());
                existingMovement.setDate(movement.getDate());
                existingMovement.setType(movement.getType().toString());
                existingMovement.setValue(movement.getValue());
                existingMovement.setInitialBalance(movement.getInitialBalance());

                repository.save(existingMovement);

                return new Result<>(entityToModel(existingMovement));
            } else {
                return Result.errorResult(ErrorCode.NOT_FOUND,
                        "Movement with ID " + movement.getId() + " not found.");
            }
        } catch (Exception exception) {

            return Result.errorResult(ErrorCode.SERVER_ERROR,
                    exception.getMessage());
        }
    }

    @Override
    public Result<List<Movement>> findByAccountClientNitAndDateBetween(Long clientNit, LocalDate startDate, LocalDate endDate) {
        try {
            List<MovementEntity> movements = repository.findByAccountClientNitAndDateBetween(clientNit, startDate, endDate);
            return new Result<>(
                    movements.stream()
                            .map(this::entityToModel)
                            .collect(Collectors.toList())
            );
        } catch (Exception exception) {
            return Result.errorResult(ErrorCode.SERVER_ERROR,
                    exception.getMessage());
        }
    }

    @Override
    public Result<List<Movement>> findAll(String direction) {
        try {
            List<MovementEntity> entities = repository.findAll();
            return new Result<>(
                    entities.stream()
                            .map(this::entityToModel)
                            .collect(Collectors.toList())
            );
        } catch (Exception exception) {
            return Result.errorResult(SERVER_ERROR,
                    exception.getMessage());
        }
    }

    @Override
    public Result<List<Movement>> findAll(int pageNumber, int pageSize, String direction) {
        try {
            List<MovementEntity> entities = repository.findAll();
            int fromIndex = pageNumber * pageSize;
            int toIndex = Math.min(fromIndex + pageSize, entities.size());
            List<MovementEntity> subEntities = entities.subList(fromIndex, toIndex);
            return new Result<>(
                    subEntities.stream()
                            .map(this::entityToModel)
                            .collect(Collectors.toList())
            );
        } catch (Exception exception) {
            return Result.errorResult(SERVER_ERROR,
                    exception.getMessage());
        }
    }

    @Override
    @Transactional
    public Result<Boolean> deleteById(Long id) {
        try {
            repository.deleteById(id);
            return new Result<>(true);
        } catch (Exception exception) {
            return new Result<>(false);
        }
    }

    private MovementEntity modelToEntity(Movement model) {
        return modelMapper.map(model, MovementEntity.class);
    }

    private Movement entityToModel(MovementEntity entity) {
        Movement movement = new Movement();
        Account account = modelMapper.map(entity.getAccount(), Account.class);
        account.setNumber(entity.getAccount().getNumber());
        movement.setId(entity.getId());
        movement.setDate(entity.getDate());
        movement.setType(MovementType.valueOf(entity.getType()));
        movement.setValue(entity.getValue());
        movement.setInitialBalance(entity.getInitialBalance());
        movement.setAccount(account);
        return movement;
    }
}