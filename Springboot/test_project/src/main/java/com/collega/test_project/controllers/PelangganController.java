package com.collega.test_project.controllers;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.collega.test_project.dto.ApiResponse;
import com.collega.test_project.models.Pelanggan;
import com.collega.test_project.services.PelangganService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@CrossOrigin(origins = "http://localhost:5500, http://127.0.0.1:5500")
@RequestMapping("/pelanggan")
public class PelangganController {
    @Autowired
    private PelangganService pelangganService;

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<Pelanggan>>> getAllPelanggan() {
        List<Pelanggan> data = pelangganService.getAllPelanggan();
        if (data == null || data.size() == 0) {            
            ApiResponse<List<Pelanggan>> response = new ApiResponse<>();
            response.setTotal(data.size());
            response.setMessage("Pelanggan tidak ditemukan");
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setStatus(HttpStatus.NOT_FOUND.toString());
            response.setData(null);

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }else{
            Collections.sort(data, new Comparator<Pelanggan>() {
                @Override
                public int compare(Pelanggan p1, Pelanggan p2){
                    return Integer.compare(p1.getIdPelanggan(), p2.getIdPelanggan());
                }
            });
            
            ApiResponse<List<Pelanggan>> response = new ApiResponse<>();
            response.setTotal(data.size());
            response.setMessage("Pelanggan ditemukan");
            response.setStatusCode(HttpStatus.OK.value());
            response.setStatus(HttpStatus.OK.toString());
            response.setData(data);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @PostMapping("/add")
    public Pelanggan createPelanggan(@RequestBody Pelanggan entity) {
        // return entity;
        return pelangganService.createPelanggan(entity);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Pelanggan> getPelangganById(@PathVariable Integer id) {
        Optional<Pelanggan> pelanggan = pelangganService.getPelangganById(id);

        return pelanggan.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<Pelanggan> updatePelanggan(@PathVariable Integer id, @RequestBody Pelanggan entity) {
        try {
            Pelanggan updatedPelanggan = pelangganService.updatePelanggan(id, entity);
            return ResponseEntity.ok(updatedPelanggan);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePelanggan(@PathVariable Integer id){
        pelangganService.deletePelanggan(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/check/{idPelanggan}/{id}")
    public Boolean checkDuplicate(@PathVariable Integer idPelanggan,@PathVariable Integer  id) {
        return pelangganService.checkIdPelanggan(idPelanggan, id);
    }
    
}
