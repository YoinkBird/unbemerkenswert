package com.merkmal.apigate;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import org.springframework.data.jpa.repository.JpaRepository;


@RepositoryRestResource(collectionResourceRel = "notebooks_all", path = "notebooks_all")
public interface DemoRepository extends JpaRepository<Demo, Long> {

// List<Demo> findByContent(@Param("name") String name);

}
