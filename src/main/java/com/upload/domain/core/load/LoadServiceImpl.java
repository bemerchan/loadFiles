package com.upload.domain.core.load;

import com.upload.infraestructure.entity.Load;
import com.upload.infraestructure.entity.LoadData;
import com.upload.infraestructure.repository.LoadDataRepository;
import com.upload.infraestructure.repository.LoadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LoadServiceImpl implements LoadService {

    @Autowired
    LoadRepository loadRepository;

    @Autowired
    LoadDataRepository loadDataRepository;

    @Override
    public Load createLoad(String fileName, String status) {
        Load load = new Load();
        load.setStartDate(LocalDateTime.now());
        load.setFileName(fileName);
        load.setStatus(status);
        return loadRepository.save(load);
    }

    @Override
    public Load updateStatusLoad(Load load, String status) {
        if(!"PROCESADO_CON_ERRORES".equals(load.getStatus())) {
            load.setStatus(status);
        }
        load.setEndDate(LocalDateTime.now());
        return loadRepository.save(load);
    }

    @Override
    public Load saveData(Load load, List<LoadData> data) {
        if(!data.isEmpty()) {
            loadDataRepository.saveAll(data);
        }
        return load;
    }
}
