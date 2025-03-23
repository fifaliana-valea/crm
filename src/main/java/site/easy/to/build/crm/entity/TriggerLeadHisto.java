package site.easy.to.build.crm.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "trigger_lead_histo")
public class TriggerLeadHisto {

    @Id
    @Column(name = "id")
    private int leadId;

    @Column(name = "name")
    @NotBlank(message = "Name is required")
    private String name;

    @Column(name = "status")
    @NotBlank(message = "Status is required")
    @Pattern(regexp = "^(meeting-to-schedule|scheduled|archived|success|assign-to-sales)$", message = "Invalid status")
    private String status;

    @Column(name = "phone")
    private String phone;

    @Column(name = "meeting_id")
    private String meetingId;

    @Column(name = "google_drive")
    private Boolean googleDrive;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User manager;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private User employee;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public TriggerLeadHisto() {
    }

    public int getId() {
        return leadId;
    }

    public void setId(int leadId) {
        this.leadId = leadId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMeetingId() {
        return meetingId;
    }

    public Boolean getGoogleDrive() {
        return googleDrive;
    }

    public void setGoogleDrive(Boolean googleDrive) {
        this.googleDrive = googleDrive;
    }


    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public User getEmployee() {
        return employee;
    }

    public void setEmployee(User employee) {
        this.employee = employee;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}


