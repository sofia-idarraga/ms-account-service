package com.bank.multimodule.repository.repositories;


import com.bank.multimodule.repository.enitites.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    List<AccountEntity> findByClientNit(Long clientNit);
}
