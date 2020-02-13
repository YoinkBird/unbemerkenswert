package com.merkmal.apigate;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "demo", path = "demo")
public interface DemoRepository extends PagingAndSortingRepository<Demo, Long> {

	List<Demo> findByContent(@Param("name") String name);

}
