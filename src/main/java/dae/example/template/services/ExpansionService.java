package dae.example.template.services;

import dae.example.template.entities.Data;
import dae.example.template.repos.ExpansionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ExpansionService {

    @Autowired
    private ExpansionRepo expansionRepo;

    public List<Data> findAll() {
        return expansionRepo.findAll();
    }

    public Data findById(final Long id) {
        return expansionRepo.findById(id).orElseThrow();
    }
}
