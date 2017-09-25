package manualweb.model;

import javax.persistence.*;

@Entity
@Table(name = "manuals")
public class Manual {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "MANUAL_BRAND")
    private String brand;

    @Column(name = "MANUAL_PART_NO")
    private String partNo;

    @Column(name = "MANUAL_DOC_TYPE")
    private String docType;

    @Column(name = "MANUAL_PART_CATEGORY")
    private String category;

    @Column(name = "MANUAL_PART_DESCRIPTION")
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPartNo() {
        return partNo;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Manual{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", partNo='" + partNo + '\'' +
                ", docType='" + docType + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
