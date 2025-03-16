package bg.softuni.campingcars.service;

import bg.softuni.campingcars.model.dto.bindingModels.AdminBindingModel;

import java.util.List;

public interface AdminService {

    boolean addAdmin(AdminBindingModel adminBindingModel);

    List<AdminBindingModel> getAllAdmins();

    void removeAdmin(AdminBindingModel adminBindingModel);
}
