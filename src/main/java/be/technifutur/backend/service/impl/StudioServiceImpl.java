package be.technifutur.backend.service.impl;

import be.technifutur.backend.exceptions.ResourceNotFoundException;
import be.technifutur.backend.models.entity.Studio;
import be.technifutur.backend.repository.StudioRepository;
import be.technifutur.backend.service.StudioService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudioServiceImpl implements StudioService {

    private final StudioRepository studioRepository;
    public StudioServiceImpl(StudioRepository studioRepository) {
        this.studioRepository = studioRepository;
    }

    @Override
    public Long add(Studio studio) {
        studio = studioRepository.save(studio);
        return studio.getId();
    }

    @Override
    public List<Studio> getAll() {
        return studioRepository.findAll().stream().toList();
    }

    @Override
    public Studio getOne(Long id) {
        return studioRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Le studio avec l' id " +
                ": " +id+
                " n'a pas été trouvé"));
    }

    @Override
    public void update(Long id, Studio studio) {
        studio.setId(id);
        studioRepository.save(studio);
    }

    @Override
    public void delete(Long id) {
        Studio studio = getOne(id);
        studioRepository.delete(studio);
    }

    @Override
    public boolean isNameTaken(String name) {
        return studioRepository.existsByName(name);
    }
}
