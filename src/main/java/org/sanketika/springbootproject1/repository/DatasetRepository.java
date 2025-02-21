package org.sanketika.springbootproject1.repository;

import org.sanketika.springbootproject1.entity.Dataset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//here we can perfom all crud operations in it of using jpa repository
public interface DatasetRepository extends JpaRepository<Dataset,String> {

    List<Dataset> findByStatus(String status);
}
