package manualweb.dao;

import manualweb.model.Manual;
import manualweb.model.PageInfoKeeper;
import manualweb.model.UserChoiceKeeper;

public interface ManualDao {

    void addManual(Manual manual);
    void updateManual(Manual manual);
    void removeManual(int id);
    Manual getManualById(int id);
    PageInfoKeeper listManuals();
    PageInfoKeeper filterManuals(UserChoiceKeeper choice);
    PageInfoKeeper loadManualsFromPage(int pageNo);
}
