package com.carebridge.backend.needsManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carebridge.backend.needsManagement.entity.NeedItem;

public interface NeedItemRepo extends JpaRepository<NeedItem, Long>{
    
}
