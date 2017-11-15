package manualweb.model;

/**
 * Created by Milk on 15.11.2017.
 */
public class UserChoiceKeeper {
    private String manualBrand;
    private String manualPart;
    private String manualDoctype;
    private String manualCategory;
    private boolean hasFilters = false;

    public String getManualBrand() {
        return manualBrand;
    }
    public String getManualPart() {
        return manualPart;
    }
    public String getManualDoctype() {
        return manualDoctype;
    }
    public String getManualCategory() {
        return manualCategory;
    }
    public void setManualBrand(String manualBrand) {
        this.manualBrand = manualBrand;
    }
    public void setManualPart(String manualPart) {
        this.manualPart = manualPart;
    }
    public void setManualDoctype(String manualDoctype) {
        this.manualDoctype = manualDoctype;
    }
    public void setManualCategory(String manualCategory) {
        this.manualCategory = manualCategory;
    }

    public boolean isHasFilters() {
        return hasFilters;
    }
    public void setHasFilters(boolean hasFilters) {
        this.hasFilters = hasFilters;
    }
}
