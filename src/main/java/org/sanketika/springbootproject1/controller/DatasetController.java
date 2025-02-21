package org.sanketika.springbootproject1.controller;

import org.sanketika.springbootproject1.entity.Dataset;
import org.sanketika.springbootproject1.entity.Status;
import org.sanketika.springbootproject1.repository.DatasetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/datasetapis")
public class DatasetController {

    @Autowired
    private DatasetRepository datasetRepository;


    @GetMapping("/getall")
    public List<Dataset> getAllDataset() {
        List<Dataset> DatasetList = datasetRepository.findAll();
        if (DatasetList.isEmpty()) {
            return Collections.emptyList();
        } else {
            return DatasetList;
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<String> getDataset(@PathVariable String id) {
        if (datasetRepository.findById(id).isPresent()) {
            return ResponseEntity.ok(datasetRepository.getById(id).getId());
        } else {
            return ResponseEntity.badRequest().body("RECORD IS NOT FOUND");
        }
    }

    @GetMapping("/getbystatus")
    public ResponseEntity<?> getStatus(@RequestParam(name = "status", required = false) String status) {
        try {
            if (status == null || status.isEmpty()) {
                return ResponseEntity.badRequest().body("status parameter is required");
            }
            if (!status.equalsIgnoreCase("LIVE") && !status.equalsIgnoreCase("Draft") && !status.equalsIgnoreCase("RETRIEVED")) {
                return ResponseEntity.badRequest().body("invalid status provided");
            }
            List<Dataset> dataset = datasetRepository.findByStatus(status);
            return ResponseEntity.ok(dataset);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occured");
        }


    }
}


