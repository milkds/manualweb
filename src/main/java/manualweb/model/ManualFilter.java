package manualweb.model;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Milk on 12.09.2017.
 */
public class ManualFilter {

    public List<Manual> getManuals() {
        return manuals;
    }

    private List<Manual> manuals;

    public void setManuals(List<Manual> manuals) {
        this.manuals = manuals;
    }

    private Set<String> brands = new TreeSet<String>();
    private Set<String> parts = new TreeSet<String>();
    private Set<String> docTypes = new TreeSet<String>();

    public Set<String> getBrands() {
        return brands;
    }
    public Set<String> getParts() {
        return parts;
    }
    public Set<String> getDocTypes() {
        return docTypes;
    }
    public Set<String> getCategories() {
        return categories;
    }
    private Set<String> categories = new TreeSet<String>();

    private String manualBrand;
    private String manualPart;
    private String manualDoctype;
    private String manualCategory;

    public String getManualPart() {
        return manualPart;
    }
    public void setManualPart(String manualPart) {
        this.manualPart = manualPart;
    }
    public String getManualDoctype() {
        return manualDoctype;
    }
    public void setManualDoctype(String manualDoctype) {
        this.manualDoctype = manualDoctype;
    }
    public String getManualCategory() {
        return manualCategory;
    }
    public void setManualCategory(String manualCategory) {
        this.manualCategory = manualCategory;
    }



    public void filterManuals(){
        if (this.manuals!=null&& this.manuals.size()!=0){
            String brand = this.manualBrand;
           if (brand!=null&&brand.length()!=0){
               Iterator<Manual> iter = this.manuals.iterator();
               while (iter.hasNext()) {
                   if (!iter.next().getBrand().equals(brand)) {
                       iter.remove();
                   }
               }
           }
           String partNo = this.manualPart;
           if (partNo!=null&&partNo.length()!=0){
               Iterator<Manual> iter = this.manuals.iterator();
               while (iter.hasNext()) {
                   if (!iter.next().getPartNo().equals(partNo)) {
                       iter.remove();
                   }
               }
           }
            String docType = this.manualDoctype;
            if (docType!=null&&docType.length()!=0){
                Iterator<Manual> iter = this.manuals.iterator();
                while (iter.hasNext()) {
                    if (!iter.next().getDocType().equals(docType)) {
                        iter.remove();
                    }
                }
            }
            String category = this.manualCategory;
            if (category!=null&&category.length()!=0){
                Iterator<Manual> iter = this.manuals.iterator();
                while (iter.hasNext()) {
                    if (!iter.next().getCategory().equals(category)) {
                        iter.remove();
                    }
                }
            }

        }
    }
    public void populateFilters(){
        if (this.manuals!=null&&this.manuals.size()!=0){
            for (Manual manual: this.manuals){
                brands.add(manual.getBrand());
                parts.add(manual.getPartNo());
                docTypes.add(manual.getDocType());
                categories.add(manual.getCategory());
            }
        }
    }

    public String getManualBrand() {
        return manualBrand;
    }

    public void setManualBrand(String manualBrand) {
        this.manualBrand = manualBrand;
    }

    public void setFilters(ManualFilter filter) {
        this.setManualBrand(filter.getManualBrand());
        this.setManualPart(filter.getManualPart());
        this.setManualDoctype(filter.getManualDoctype());
        this.setManualCategory(filter.getManualCategory());
    }
}
