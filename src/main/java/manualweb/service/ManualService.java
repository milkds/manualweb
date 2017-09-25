package manualweb.service;

import manualweb.model.Manual;
import manualweb.model.ManualFilter;

import java.util.List;


public interface ManualService {

    void addManual(Manual manual);
    void updateManual(Manual manual);
    void removeManual(int id);
    Manual getManualById(int id);
    ManualFilter listManuals();
    ManualFilter filterManuals(ManualFilter filter);
}
