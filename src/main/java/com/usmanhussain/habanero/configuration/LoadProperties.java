package com.usmanhussain.habanero.configuration;

import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LoadProperties {

    protected static final org.slf4j.Logger LOG = LoggerFactory.getLogger(LoadProperties.class);

    private static Properties userProps;
    private static String adminUser;
    private static String adminUserPassword;

    private static File baseUserDir = new File(System.getProperty("user.dir"));
    private static File iRunPropertiesFile = new File(baseUserDir + "/SCOR_TestAutomation/src/test/resources/runConfig.properties");
    private static File mRunPropertiesFile = new File(baseUserDir + "/src/test/resources/runConfig.properties");

    static {
        loadUserConfigProps();
    }

    /**
     * Calls loadProps() to load the run config properties
     */
    public static void loadUserConfigProps() {
        userProps = new Properties();
        try {
            LOG.info("Loading the properties as proj root /src:");
            loadProps(userProps, mRunPropertiesFile);
        } catch (IOException e) {
            LOG.info("context", e);
            try {
                LOG.info("No prop file found, trying load the properties as proj root /src:");
                loadProps(userProps, iRunPropertiesFile);
            } catch (IOException e1) {
                LOG.error("IOException", e1);
            }
        }
    }

    /**
     * Loads in the properties file
     *
     * @param props Properties being loaded from the file
     * @param file  File declartion that is being used
     * @throws java.io.IOException IOException is thrown
     */
    public static void loadProps(Properties props, File file) throws IOException {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            props.load(fis);
        } finally {
            if (null != fis) {
                fis.close();
            }
        }
    }

    /**
     * Sets up the following environments from the properties file
     * <p>
     * adminUsername
     * adminPassword
     * </p>
     */
    public static void setUpUsers() {
        LOG.info("SETTING UP THE USERS TO BE USED ");
        adminUser = userProps.getProperty("adminUsername");
        adminUserPassword = userProps.getProperty("adminPassword");
        LOG.info("COMPLETED USER SETUP ");
    }

    public String getAdminUser() {
        return adminUser;
    }

    public String getAdminUserPassword() {
        return adminUserPassword;
    }

}