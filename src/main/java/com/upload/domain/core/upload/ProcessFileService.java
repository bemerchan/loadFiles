package com.upload.domain.core.upload;

import com.upload.application.api.model.FileRow;
import com.upload.application.api.model.ItemResponse;
import com.upload.application.api.model.UserResponse;
import com.upload.domain.core.FileUtil;
import com.upload.domain.core.external.MercadoLibreService;
import com.upload.domain.core.load.LoadService;
import com.upload.infraestructure.entity.Load;
import com.upload.infraestructure.entity.LoadData;
import org.beanio.BeanReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProcessFileService {

    @Autowired
    MercadoLibreService mercadoLibreService;

    @Autowired
    LoadService loadService;

    @Autowired
    LoadDataServices loadDataServices;
    List<LoadData> listData;
    Map<Long, String> mapUsers = new HashMap<>();
    @Value("${config.process.search.data.size}")
    private int processDataSize;
    @Value("${config.process.save.data.size}")
    private int processSaveSize;

    @Async
    public void processFile(MultipartFile multipartFile, Load load)  {
        try {
            listData = new ArrayList<>();
            BeanReader data = FileUtil.readData(multipartFile);
            Object record;
            readData(load, data);
        } catch (Exception e) {
            load.setError(e.getMessage());
            loadService.updateStatusLoad(load, "ERROR");
        }
    }

    private void readData(Load load, BeanReader data) {
        Object record;
        List<FileRow> rows = new ArrayList<>();
        while ((record = data.read()) != null) {
            if("row".equals(data.getRecordName())) {
                FileRow row = (FileRow) record;
                if(!"id".equals(row.getId())) {
                    rows.add(row);
                    if(rows.size() == processDataSize) {
                        processData(rows, load);
                        rows.clear();
                    }
                    if(listData.size() >= processSaveSize) {
                        saveData(load);
                    }
                }

            }
        }
        if(!rows.isEmpty()) {
            processData(rows, load);
            saveData(load);
        }
        loadService.updateStatusLoad(load, "EXITOSO");
    }

    private void saveData(Load load) {
        loadService.saveData(load, listData);
        listData.clear();
    }

    private void processData(List<FileRow> rows, Load load) {
        List<String> ids = rows.stream().map(row -> row.getSite() + row.getId()).collect(Collectors.toList());
        List<ItemResponse> items = mercadoLibreService.findItems(ids);
        this.loadUsers(items);
        rows.forEach(row -> {
            ItemResponse itemResponse = items.stream().filter(item ->
                            HttpStatus.OK.value() == item.getCode() &&
                                    item.getBody() != null &&
                                    (row.getSite() + row.getId()).equals(item.getBody().getId()))
                    .findFirst().orElse(null);
            createLoadData(itemResponse, row, load);
        });
    }

    private void loadUsers(List<ItemResponse> items) {
       List<Long> ids = new ArrayList<>();
       items.stream().filter(item -> HttpStatus.OK.value() == item.getCode()).collect(Collectors.toList()).forEach(
            itemResponse -> ids.add(itemResponse.getBody().getSeller_id())
       );
       ids.removeAll(mapUsers.keySet());
       List<UserResponse> userResponses = mercadoLibreService.getUsersByIds(ids);
        userResponses.stream().filter(userResponse -> HttpStatus.OK.value() == userResponse.getCode())
                .forEach(userResponse -> mapUsers.put(userResponse.getBody().getId(), userResponse.getBody().getNickname()));
    }

    private void createLoadData(ItemResponse item, FileRow row, Load load) {
        if(item != null) {
            ItemResponse.ItemBody body = item.getBody();
            LoadData data = new LoadData();
            data.setSite(row.getSite());
            data.setPrice(body.getPrice());
            data.setStartTime(body.getStart_time());
            data.setName(loadDataServices.getCategoryBySiteAndId(row.getSite(), body.getCategory_id()));
            data.setDescription(loadDataServices.getCurrency(body.getCurrency_id()));
            data.setNickname(getUserNickName(body.getSeller_id()));
            data.setLoadId(load.getId());
            data.setRecordId(body.getId());
            listData.add(data);
        }
    }

    private String getUserNickName(Long userId) {
        return mapUsers.get(userId);
    }
}
