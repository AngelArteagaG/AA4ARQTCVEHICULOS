package com.alquiler.vehiculos.alquilervehiculps.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alquiler.vehiculos.alquilervehiculps.model.Vehiculo;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Integer> {
    Vehiculo findByMatricula(String matricula);

}
