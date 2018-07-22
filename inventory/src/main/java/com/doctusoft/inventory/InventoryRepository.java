package com.doctusoft.inventory;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, String> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select i from Inventory i where i.id = ?1")
    Inventory findOneAndLock(String id);
	
}
