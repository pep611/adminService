package com.example.adminservice;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BusRouteModelController {
    private final BusRouteModelRepository repository;

    public BusRouteModelController(BusRouteModelRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public BusRouteModel addRoute(@RequestBody BusRouteModel route) {
        return repository.save(route);
    }

    @DeleteMapping("/{id}")
    public void deleteRoute(@PathVariable Integer id) {
        repository.deleteById(id);
    }

    @GetMapping
    public List<BusRouteModel> getAllRoutes() {
        return repository.findAll();
    }

    @GetMapping("/search")
    public List<BusRouteModel> searchBySourceAndDestination(
            @RequestParam String source,
            @RequestParam String destination) {
        return repository.findBySourceAndDestination(source, destination);
    }

    @Transactional
    @DeleteMapping("/delete-by-route")
    public void deleteBySourceAndDestination(
            @RequestParam String source,
            @RequestParam String destination) {
        repository.deleteBySourceAndDestination(source, destination);
    }

    @PutMapping("/{id}")
    public BusRouteModel updateRoute(@PathVariable Integer id, @RequestBody BusRouteModel updatedRoute) {
        return repository.findById(id)
                .map(route -> {
                    route.setSource(updatedRoute.getSource());
                    route.setDestination(updatedRoute.getDestination());
                    // set other fields as needed
                    return repository.save(route);
                })
                .orElseThrow(() -> new RuntimeException("Route not found with id " + id));
    }

    @GetMapping("/price")
    public List<BigDecimal> getPriceBySourceAndDestination(
            @RequestParam String source,
            @RequestParam String destination) {
        return repository.findBySourceAndDestination(source, destination)
                .stream()
                .map(BusRouteModel::getPrice)
                .toList();
    }
}