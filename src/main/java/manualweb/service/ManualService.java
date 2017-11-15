package manualweb.service;

import manualweb.model.Manual;
import manualweb.model.ManualFilter;
import manualweb.model.UserChoiceKeeper;

import java.util.List;


public interface ManualService {

    void addManual(Manual manual);
    void updateManual(Manual manual);
    void removeManual(int id);
    Manual getManualById(int id);
    ManualFilter listManuals();
    ManualFilter filterManuals(UserChoiceKeeper choice);
    ManualFilter loadManualsFromPage(ManualFilter filter, int pageNo);
}
