package org.sanketika.springbootproject1.controller;


import org.sanketika.springbootproject1.entity.Dataset;
import org.sanketika.springbootproject1.entity.Status;
import org.sanketika.springbootproject1.repository.DatasetRepository;
import org.sanketika.springbootproject1.service.DatasetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.*;

@RestController
@RequestMapping("/datasetapis")
public class DatasetController {
    private DatasetService datasetService;

    public DatasetController(DatasetService datasetService) {
        this.datasetService = datasetService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllDataset() {
        return datasetService.getAllDataset();
    }


    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getDatasetById(@PathVariable String id) {
        return datasetService.getById(id);
    }


    @GetMapping("/getByStatus")
    public ResponseEntity<?> getByDatasetStatus(@RequestParam(name = "status", required = false) String status) {
        return datasetService.getByStatus(status);
    }
}



