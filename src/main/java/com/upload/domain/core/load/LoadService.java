package com.upload.domain.core.load;

import com.upload.infraestructure.entity.Load;
import com.upload.infraestructure.entity.LoadData;

import java.util.List;

public interface LoadService {

    Load createLoad(String fileName, String status);

    Load updateStatusLoad(Load load, String status);

    Load saveData(Load load, List<LoadData> data);

}
