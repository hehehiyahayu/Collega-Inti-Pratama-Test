package com.collega.test_project.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Pelanggan", schema = "public")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Pelanggan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "id_pelanggan", unique = true)
    private Integer idPelanggan;

    @Column(name = "nama")
    private String Nama;

    @Column(name = "alamat")
    private String Alamat;

    @Column(name = "jenis_kelamin")
    private String JenisKelamin;

    @Column(name = "umur")
    private Integer Umur;

    @Column(name = "pekerjaan")
    private String Pekerjaan;

    @Column(name = "penghasilan")
    private Integer Penghasilan;
}
