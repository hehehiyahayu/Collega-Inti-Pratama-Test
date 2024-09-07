package com.collega.test_project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import com.collega.test_project.models.Pelanggan;

public interface PelangganRepository extends JpaRepository<Pelanggan, Integer>{
    Optional<Pelanggan> findByIdPelanggan(Integer idPelanggan);
}