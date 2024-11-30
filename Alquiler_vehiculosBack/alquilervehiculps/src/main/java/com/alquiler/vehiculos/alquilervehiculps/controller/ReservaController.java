package com.alquiler.vehiculos.alquilervehiculps.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alquiler.vehiculos.alquilervehiculps.model.Reserva;
import com.alquiler.vehiculos.alquilervehiculps.model.Vehiculo;
import com.alquiler.vehiculos.alquilervehiculps.service.ReservaService;
import com.alquiler.vehiculos.alquilervehiculps.service.VehiculoService;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {
    private final ReservaService reservaService;
    private final VehiculoService vehiculoService;

    public ReservaController(ReservaService reservaService, VehiculoService vehiculoService) {
        this.reservaService = reservaService;
        this.vehiculoService = vehiculoService;
    }

    @PostMapping
    public ResponseEntity<Reserva> crearReserva(@RequestBody Reserva reserva) {
        // valida el vehiculo disponible
        Optional<Vehiculo> vehiculoOpt = vehiculoService.obtenerPorId(reserva.getVehiculo().getIdVehiculo());
        if (vehiculoOpt.isPresent() && vehiculoOpt.get().isEstado()) {
            // valida fechas (inicio debe ser antes de fin)
            if (reserva.getFechaInicio().isAfter(reserva.getFechaFin())) {
                return ResponseEntity.badRequest().body(null); // fecha de inicio incorrecta
            }

            // crear la reserva
            Reserva nuevaReserva = reservaService.crearOActualizar(reserva);

            // Actualizar el estado del vehículo (opcional)
            Vehiculo vehiculo = vehiculoOpt.get();
            vehiculo.setEstado(false); // el vehiculo ya no está disponible
            vehiculoService.crearOActualizar(vehiculo);

            return ResponseEntity.ok(nuevaReserva);
        } else {
            return ResponseEntity.badRequest().body(null); // vehculo no disponible
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserva> obtenerReserva(@PathVariable int id) {
        return reservaService.obtenerPorId(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Reserva> listarReservas() {
        return reservaService.listarTodas();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable int id) {
        reservaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
