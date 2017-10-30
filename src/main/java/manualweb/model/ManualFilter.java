package manualweb.model;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by Milk on 12.09.2017.
 */
public class ManualFilter {

    private List<Manual> manuals;

    private List<String> brands;
    private List<String> parts;
    private List<String> docTypes;
    private List<String> categories;

    private String manualBrand;
    private String manualPart;
    private String manualDoctype;
    private String manualCategory;

    private int currentPage = 0;
    private Long totalQueryResults = 0L;

    private final int MAX_RESULTS = 2;
    private final int MAX_PAGES_ONE_SIDE = 2;

    private boolean hasFilters = false;

    public void setFilters(ManualFilter filter) {
        this.setManualBrand(filter.getManualBrand());
        this.setManualPart(filter.getManualPart());
        this.setManualDoctype(filter.getManualDoctype());
        this.setManualCategory(filter.getManualCategory());
    }

    //Getters and Setters

    public List<Manual> getManuals() {
        return manuals;
    }
    public void setManuals(List<Manual> manuals) {
        this.manuals = manuals;
    }

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

    public List<String> getBrands() {
        return brands;
    }
    public List<String> getParts() {
        return parts;
    }
    public List<String> getDocTypes() {
        return docTypes;
    }
    public List<String> getCategories() {
        return categories;
    }
    public void setBrands(List<String> brands) {
        this.brands = brands;
    }
    public void setParts(List<String> parts) {
        this.parts = parts;
    }
    public void setDocTypes(List<String> docTypes) {
        this.docTypes = docTypes;
    }
    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public int getCurrentPage() {
        return currentPage;
    }
    public Long getTotalQueryResults() {
        return totalQueryResults;
    }
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public void setTotalQueryResults(Long totalQueryResults) {
        this.totalQueryResults = totalQueryResults;
    }

    public int getMaxResults() {
        return MAX_RESULTS;
    }
    public int getMaxPagesOneSide() {
        return MAX_PAGES_ONE_SIDE;
    }

    public boolean isHasFilters() {
        return hasFilters;
    }
    public void setHasFilters(boolean hasFilters) {
        this.hasFilters = hasFilters;
    }
}
