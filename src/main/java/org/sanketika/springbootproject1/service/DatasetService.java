package org.sanketika.springbootproject1.service;


import org.sanketika.springbootproject1.entity.Dataset;
import org.sanketika.springbootproject1.entity.Status;
import org.sanketika.springbootproject1.repository.DatasetRepository;
import org.sanketika.springbootproject1.response.DatasetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class DatasetService {
    @Autowired
    private final DatasetRepository datasetRepository;


    public DatasetService(DatasetRepository datasetRepository) {
        this.datasetRepository = datasetRepository;


    }

    public ResponseEntity<?> getAllDataset() {

        List<Dataset> datasetList = datasetRepository.findAll();
        if (datasetList.isEmpty()) {
            return (ResponseEntity.ok(DatasetResponse.createResponse("Success", HttpStatus.OK, "no dataset found", Collections.emptyList())));
        }
        return (ResponseEntity.ok(DatasetResponse.createResponse("Success", HttpStatus.OK, null, datasetList)));
    }

    public ResponseEntity<?> getById(String id) {

        Optional<Dataset> dataset = datasetRepository.findById(id);
        if (dataset.isPresent()) {
            return (ResponseEntity.ok(DatasetResponse.createResponse("Success", HttpStatus.OK, null, datasetRepository.findById(id))));
        } else {
            return (ResponseEntity.ok(DatasetResponse.createResponse("Failure", HttpStatus.NOT_FOUND, "Requested dataset id is not found", null)));
        }


    }

    public ResponseEntity<?> getByStatus(String status) {
        if (status == null||status.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(DatasetResponse.createResponse("failure",HttpStatus.BAD_REQUEST,"status parameter is required",null));
        }

        try {
            Status enumStatus = Status.valueOf(status.toUpperCase());
            List<Dataset> datasetList = datasetRepository.findByStatus(enumStatus);
            return ResponseEntity.ok(DatasetResponse.createResponse(
                    "success", HttpStatus.OK,null ,datasetList));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(DatasetResponse.createResponse(
                    "failure", HttpStatus.BAD_REQUEST, "Invalid status. Allowed: LIVE, DRAFT, RETIRED", null));
        }

    }
    }




