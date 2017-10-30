package manualweb.dao;

import manualweb.model.Manual;
import manualweb.model.ManualFilter;

import java.util.List;

public interface ManualDao {

    void addManual(Manual manual);
    void updateManual(Manual manual);
    void removeManual(int id);
    Manual getManualById(int id);
    ManualFilter listManuals();

    ManualFilter filterManuals(ManualFilter filter);

    ManualFilter loadManualsFromPage(ManualFilter filter, int pageNo);
}
