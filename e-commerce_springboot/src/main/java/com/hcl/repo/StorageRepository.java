package com.hcl.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.entity.Storage;

@Repository
public interface StorageRepository extends JpaRepository<Storage,Integer> 
{

}
