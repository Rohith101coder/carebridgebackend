package com.carebridge.backend.needsManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carebridge.backend.needsManagement.entity.DeletedItems;

public interface DeletedItemRepo extends JpaRepository<DeletedItems, Long>{
    
}
