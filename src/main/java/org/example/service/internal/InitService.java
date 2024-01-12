package org.example.service.internal;

import org.example.configuration.InitialConfigurations;
import org.example.constants.GlobalConstants;

import java.io.File;

public class InitService {

    public InitService() {
    }

    public void InitiateServices() {
        InitialConfigurations.initiateServer();
        createBaseDirectory();
    }

    private void createBaseDirectory() {
        File file = new File(GlobalConstants.BASE_DIRECTORY);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
}
