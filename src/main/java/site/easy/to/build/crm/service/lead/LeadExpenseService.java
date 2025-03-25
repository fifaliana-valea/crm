package site.easy.to.build.crm.service.lead;
import site.easy.to.build.crm.entity.LeadExpense;
public interface LeadExpenseService {
    public LeadExpense save(LeadExpense leadExpense);
    
    LeadExpense findById(int id);
    public LeadExpense findLatestByTriggerLeadHistoId(Integer triggerLeadHistoId);  
}
