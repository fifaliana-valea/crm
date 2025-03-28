package site.easy.to.build.crm.service.lead;
import java.time.LocalDateTime;
import java.util.List;

import site.easy.to.build.crm.entity.TicketHisto;
import site.easy.to.build.crm.entity.TriggerLeadHisto;
public interface TriggerLeadHistoService {
    public TriggerLeadHisto save(TriggerLeadHisto triggerLeadHisto);

//     public List<TriggerLeadHisto> getTriggerLeadHistoBetweenDates(LocalDateTime startDate, LocalDateTime endDate);

    List<TriggerLeadHisto> getTriggerLeadHistoBetweenDates(LocalDateTime startDate, LocalDateTime endDate);

    public List<TriggerLeadHisto> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    TriggerLeadHisto getById(Integer id);


    List<TriggerLeadHisto> getAll();


    TriggerLeadHisto update(Integer id, TriggerLeadHisto triggerLeadHisto);


    void delete(Integer id);

    public void softDelete(Integer id);
}