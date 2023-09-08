package be.technifutur.backend.service;

import be.technifutur.backend.models.entity.Studio;

public interface StudioService extends CrudService<Studio,Long>{

    boolean isNameTaken(String name);
}
