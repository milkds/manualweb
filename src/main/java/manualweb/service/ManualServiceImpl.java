package manualweb.service;

import manualweb.dao.ManualDao;
import manualweb.dao.ManualDaoImpl;
import manualweb.model.Manual;
import manualweb.model.ManualFilter;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class ManualServiceImpl implements ManualService {
    private ManualDao manualDao;

    @Transactional
    public void addManual(Manual manual) {
        this.manualDao.addManual(manual);
    }

    @Transactional
    public void updateManual(Manual manual) {
        this.manualDao.updateManual(manual);
    }

    @Transactional
    public void removeManual(int id) {
    this.manualDao.removeManual(id);
    }

    @Transactional
    public Manual getManualById(int id) {
        return this.manualDao.getManualById(id);
    }

    @Transactional
    public ManualFilter listManuals() {
      return this.manualDao.listManuals();
    }

    @Transactional
    public ManualFilter filterManuals(ManualFilter filter) {
        return this.manualDao.filterManuals(filter);
    }
    @Transactional
    public ManualFilter loadManualsFromPage(ManualFilter filter, int pageNo) {
        return this.manualDao.loadManualsFromPage(filter, pageNo);
    }


    public void setManualDao(ManualDao manualDao) {
        this.manualDao = manualDao;
    }
}
