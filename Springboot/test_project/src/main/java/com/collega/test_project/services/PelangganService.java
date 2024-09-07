package com.collega.test_project.services;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.collega.test_project.models.Pelanggan;
import com.collega.test_project.repositories.PelangganRepository;

@Service
public class PelangganService {
    @Autowired
    private PelangganRepository pelangganRepository;

    public Pelanggan createPelanggan(Pelanggan pelanggan){
        // return pelanggan;
        return pelangganRepository.save(pelanggan);
    }

    public List<Pelanggan> getAllPelanggan(){
        return pelangganRepository.findAll();
    }

    public Optional<Pelanggan> getPelangganById(Integer id){
        return pelangganRepository.findById(id);
    }

    public Pelanggan updatePelanggan(Integer id, Pelanggan updatedPelanggan){
        return pelangganRepository.findById(id).map(pelanggan -> {
            // pelanggan.setId(id);
            pelanggan.setIdPelanggan(updatedPelanggan.getIdPelanggan());
            pelanggan.setNama(updatedPelanggan.getNama());
            pelanggan.setAlamat(updatedPelanggan.getAlamat());
            pelanggan.setJenisKelamin(updatedPelanggan.getJenisKelamin());
            pelanggan.setUmur(updatedPelanggan.getUmur());
            pelanggan.setPekerjaan(updatedPelanggan.getPekerjaan());
            pelanggan.setPenghasilan(updatedPelanggan.getPenghasilan());
            return pelangganRepository.save(pelanggan);
        }).orElseThrow(() -> new RuntimeException("Pelanggan tidak ditemukan"));
    }

    public void deletePelanggan(Integer id){
        pelangganRepository.deleteById(id);
    }

    public boolean checkIdPelanggan(Integer idPelanggan, Integer id){
        Pelanggan checkIdPelanggan = pelangganRepository.findByIdPelanggan(idPelanggan).orElse(null);
        Pelanggan checkId = pelangganRepository.findById(id).orElse(null);

        if (checkIdPelanggan == null || (checkIdPelanggan.getId() == checkId.getId() && checkIdPelanggan.getIdPelanggan() == checkId.getIdPelanggan())) {
            return false;
        }else{
            return true;
        }
    }
}
