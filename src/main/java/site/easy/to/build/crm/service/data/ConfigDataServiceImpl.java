package site.easy.to.build.crm.service.data;

import org.springframework.stereotype.Service;
import site.easy.to.build.crm.repository.ConfigDataRepository;

@Service
public class ConfigDataServiceImpl implements ConfigDataService{
    private ConfigDataRepository configDataRepository;

    public ConfigDataServiceImpl(ConfigDataRepository configDataRepository){
        this.configDataRepository = configDataRepository;
    }

    @Override
    public String resetData(){
       return this.configDataRepository.resetDataBase();
    }
}
