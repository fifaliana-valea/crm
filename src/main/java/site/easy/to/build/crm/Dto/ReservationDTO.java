package site.easy.to.build.crm.Dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ReservationDTO {
    private String ref;
    private String espace;
    private String client;
    private LocalDate date;
    private LocalTime heureDebut;
    private int duree;
    private List<String> option;

    public ReservationDTO(String ref, String espace, String client,
                          LocalDate date, LocalTime heureDebut,
                          int duree, List<String> option) {
        this.ref = ref;
        this.espace = espace;
        this.client = client;
        this.date = date;
        this.heureDebut = heureDebut;
        this.duree = duree;
        this.option = option;
    }

    // Getters
    public String getRef() {
        return ref;
    }

    public String getEspace() {
        return espace;
    }

    public String getClient() {
        return client;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getHeureDebut() {
        return heureDebut;
    }

    public int getDuree() {
        return duree;
    }

    public List<String> getOption() {
        return option;
    }

    // Setters
    public void setRef(String ref) {
        this.ref = ref;
    }

    public void setEspace(String espace) {
        this.espace = espace;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setHeureDebut(LocalTime heureDebut) {
        this.heureDebut = heureDebut;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public void setOption(List<String> option) {
        this.option = option;
    }

    @Override
    public String toString() {
        return "ReservationDTO{" +
                "ref='" + ref + '\'' +
                ", espace='" + espace + '\'' +
                ", client='" + client + '\'' +
                ", date=" + date +
                ", heureDebut=" + heureDebut +
                ", duree=" + duree +
                ", option=" + option +
                '}';
    }
}

