package com.zoomdata.demo.pages.basePages;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.zoomdata.demo.helpers.EnvironmentPropertiesHandler;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.Proxy.ProxyType;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.logging.Level;

import static com.zoomdata.demo.helpers.EnvironmentPropertiesHandler.*;
import static java.lang.Boolean.parseBoolean;


public class BaseTest {

    private static final Logger LOGGER = Logger.getLogger(BaseTest.class);
    protected WebDriver driver;
    private String remoteWebDriverUrl = null;
    private LoggingPreferences logs;
    private static String browser;
    private static boolean useRemoteWebDriver;
    private EnvironmentPropertiesHandler properties;

    // may be firefox, chrome, opera, ie
    static final String BROWSER_NAME = System.getProperty("browser");

    protected void setUp() {
        final Os os = getOs();
        initializeStaticFields();
        if (!useRemoteWebDriver) {
            initializeDriver("webdriver.chrome.driver", os, os.chromePath);
        }
        initialiseWebDriver();
        WebDriverRunner.setWebDriver(driver); //for selenide purposes
        Configuration.reportsFolder = properties.getEnvProperty(STORE_SCREEN_TO);
        Configuration.screenshots = false;
    }

    private enum Os {

        WINDOWS32("webdriver/", "chromedriver_win32", "IEDriverServer_64", ".exe"),
        WINDOWS64("webdriver/", "chromedriver_win32", "IEDriverServer_32", ".exe"),
        LINUX32("webdriver/", "chromedriver_linux32", null, ""),
        LINUX64("webdriver/", "chromedriver_linux64", null, ""),
        MACOS32("webdriver/", "chromedriver_32", null, "");

        private final String chromePath;
        private final String prefix;
        private final String suffix;
        private final String iePath;

        Os(String prefix, String chromePath, String iePath, String suffix) {
            this.prefix = prefix;
            this.chromePath = chromePath;
            this.suffix = suffix;
            this.iePath = iePath;
        }
    }

    private static Os getOs() {

        if (SystemUtils.IS_OS_WINDOWS) {
            return SystemUtils.OS_ARCH.contains("32") ? Os.WINDOWS32 : Os.WINDOWS64;
        } else if (SystemUtils.IS_OS_LINUX) {
            return SystemUtils.OS_ARCH.contains("i686") || SystemUtils.OS_ARCH.contains("i386") ? Os.LINUX32 : Os.LINUX64;
        } else if (SystemUtils.IS_OS_MAC_OSX || SystemUtils.IS_OS_MAC) {
            return Os.MACOS32;
        }
        throw new IllegalStateException("Unknown OS: " + SystemUtils.OS_NAME);
    }

    private void initializeDriver(String browserSystemVariable, Os os, String browserPath) {
        if (browserPath != null) {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(
                    os.prefix + browserPath + os.suffix);
            if (inputStream == null) {
                throw new IllegalStateException("Cannot locate driver on classpath (missing dependency): "
                        + os.prefix
                        + browserPath + os.suffix);
            }
            try {
                File temp = File.createTempFile(browserPath, os.suffix);
                temp.setExecutable(true);
                FileUtils.copyInputStreamToFile(inputStream, temp);
                System.setProperty(browserSystemVariable, temp.getAbsolutePath());
            } catch (IOException e) {
                final String msg = "Error while copying driver executable";
                LOGGER.error(msg, e);
                throw new RuntimeException(msg, e);
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        final String msg = "Error while closing the input stream";
                        LOGGER.error(msg, e);
                        throw new RuntimeException(msg, e);
                    }
                }
            }
        }
    }

    private void initializeStaticFields() {
        this.properties = getInstance();

        browser = properties.getEnvProperty(BROWSER);
        if (BROWSER_NAME != null) {
            if (Arrays.asList("firefox", "ie", "opera", "chrome").contains(BROWSER_NAME)) {
                browser = BROWSER_NAME;
            }
        }

        String remoteWDProp = properties.getEnvProperty(USE_REMOTE_WEBDRIVER);
        useRemoteWebDriver = remoteWDProp != null && parseBoolean(remoteWDProp);
    }


    protected void initialiseWebDriver() {
        DesiredCapabilities capability = null;
        if (useRemoteWebDriver) {
            if (properties.getEnvProperty(REMOTE_WEBDRIVER_URL) != null) {
                remoteWebDriverUrl = properties.getEnvProperty(REMOTE_WEBDRIVER_URL);
            }

            if (browser.equalsIgnoreCase("firefox")) {
                capability = DesiredCapabilities.firefox();
                addProfileToCapability(capability);
            } else if (browser.equalsIgnoreCase("internetexplorer")) {
                capability = DesiredCapabilities.internetExplorer();
                capability.setCapability("ignoreZoomSetting", true);
            } else if (browser.equalsIgnoreCase("chrome")) {
                capability = DesiredCapabilities.chrome();
            } else if (browser.equalsIgnoreCase("opera")) {
                capability = DesiredCapabilities.opera();
            } else if (browser.equalsIgnoreCase("mobileSafari")) {
                capability = new DesiredCapabilities();
            } else if (browser.equalsIgnoreCase("androidWeb")) {
                capability = DesiredCapabilities.android();
            } else {
                capability = new DesiredCapabilities();
            }
            try {
                LOGGER.info("Initializing remote webdriver with: "
                        + capability.getBrowserName() + ", "
                        + capability.getVersion() + ", "
                        + capability.getPlatform());
                driver = new RemoteWebDriver(new URL(remoteWebDriverUrl), setCapabilities(capability));
                ((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
            } catch (MalformedURLException e) {
                final String msg = "Error while initializing remote webdriver with url: "
                        + remoteWebDriverUrl;
                LOGGER.error(msg, e);
                throw new RuntimeException(msg, e);
            }
        } else if (browser.equals("firefox")) {
            capability = DesiredCapabilities.firefox();
            {
                addProfileToCapability(capability);
                LOGGER.info("Initializing webdriver with " + capability.getBrowserName() + ", "
                        + capability.getVersion()
                        + ", " + capability.getPlatform());
                driver = new FirefoxDriver(setCapabilities(capability));
            }
        } else if (browser.equalsIgnoreCase("chrome")) {
            capability = DesiredCapabilities.chrome();
            LOGGER.info("Initializing webdriver with " + capability.getBrowserName() + ", "
                    + capability.getVersion()
                    + ", " + capability.getPlatform());
            driver = new ChromeDriver(setCapabilities(capability));
        } else if (browser.equalsIgnoreCase("internetexplorer")) {
            Os os = getOs();
            System.setProperty("webdriver.ie.driver", "./src/test/resources/"  + os.prefix + os.iePath + os.suffix);
            capability = DesiredCapabilities.internetExplorer();
            driver = new InternetExplorerDriver(setCapabilities(capability));
        } else {
            final String msg = "No proper browser settings, check environment.properties";
            LOGGER.error(msg);
            {
                throw new RuntimeException(msg);
            }
        }
    }

    private DesiredCapabilities setCapabilities(DesiredCapabilities desiredCapability) {
        if (properties.getEnvProperty(ACCEPT_SSL_CERTS) != null) {
            desiredCapability.setCapability(
                    CapabilityType.ACCEPT_SSL_CERTS,
                    parseBoolean(properties.getEnvProperty(ACCEPT_SSL_CERTS)));
        }
        if (properties.getEnvProperty(EnvironmentPropertiesHandler.BROWSER_NAME) != null) {
            desiredCapability.setCapability(CapabilityType.BROWSER_NAME, properties.getEnvProperty(BROWSER_NAME));
        }
        if (properties.getEnvProperty(ENABLE_PROFILING_CAPABILITY) != null) {
            desiredCapability.setCapability(
                    CapabilityType.ENABLE_PROFILING_CAPABILITY,
                    parseBoolean(properties.getEnvProperty(ENABLE_PROFILING_CAPABILITY)));
        }
        if (properties.getEnvProperty(HAS_NATIVE_EVENTS) != null) {
            desiredCapability.setCapability(
                    CapabilityType.HAS_NATIVE_EVENTS,
                    parseBoolean(properties.getEnvProperty(HAS_NATIVE_EVENTS)));
        }
        if (properties.getEnvProperty(PLATFORM) != null) {
            desiredCapability
                    .setCapability(CapabilityType.PLATFORM,
                            Platform.valueOf(properties.getEnvProperty(PLATFORM)));
        }
        if (properties.getEnvProperty(ROTATABLE) != null) {
            desiredCapability.setCapability(
                    CapabilityType.ROTATABLE,
                    parseBoolean(properties.getEnvProperty(ROTATABLE)));
        }
        if (properties.getEnvProperty(SUPPORTS_ALERTS) != null) {
            desiredCapability.setCapability(
                    CapabilityType.SUPPORTS_ALERTS,
                    parseBoolean(properties.getEnvProperty(SUPPORTS_ALERTS)));
        }
        if (properties.getEnvProperty(SUPPORTS_APPLICATION_CACHE) != null) {
            desiredCapability.setCapability(
                    CapabilityType.SUPPORTS_APPLICATION_CACHE,
                    parseBoolean(properties.getEnvProperty(SUPPORTS_APPLICATION_CACHE)));
        }
        if (properties.getEnvProperty(SUPPORTS_FINDING_BY_CSS) != null) {
            desiredCapability.setCapability(
                    CapabilityType.SUPPORTS_FINDING_BY_CSS,
                    parseBoolean(properties.getEnvProperty(SUPPORTS_FINDING_BY_CSS)));
        }
        if (properties.getEnvProperty(JAVA_SCRIPT_ENABLED) != null) {
            desiredCapability.setCapability(
                    CapabilityType.SUPPORTS_JAVASCRIPT,
                    parseBoolean(properties.getEnvProperty(JAVA_SCRIPT_ENABLED)));
        }
        if (properties.getEnvProperty(SUPPORTS_LOCATION_CONTEXT) != null) {
            desiredCapability.setCapability(
                    CapabilityType.SUPPORTS_LOCATION_CONTEXT,
                    parseBoolean(properties.getEnvProperty(SUPPORTS_LOCATION_CONTEXT)));
        }
        if (properties.getEnvProperty(SUPPORTS_SQL_DATABASE) != null) {
            desiredCapability.setCapability(
                    CapabilityType.SUPPORTS_SQL_DATABASE,
                    parseBoolean(properties.getEnvProperty(SUPPORTS_SQL_DATABASE)));
        }
        if (properties.getEnvProperty(SUPPORTS_WEB_STORAGE) != null) {
            desiredCapability.setCapability(
                    CapabilityType.SUPPORTS_WEB_STORAGE,
                    parseBoolean(properties.getEnvProperty(SUPPORTS_WEB_STORAGE)));
        }
        if (properties.getEnvProperty(TAKES_SCREENSHOT) != null) {
            desiredCapability.setCapability(
                    CapabilityType.TAKES_SCREENSHOT,
                    parseBoolean(properties.getEnvProperty(TAKES_SCREENSHOT)));
        }
        if (properties.getEnvProperty(UNEXPECTED_ALERT_BEHAVIOUR) != null) {
            desiredCapability.setCapability(
                    CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR,
                    UnexpectedAlertBehaviour.fromString(properties.getEnvProperty(UNEXPECTED_ALERT_BEHAVIOUR)));
        }
        if (properties.getEnvProperty(VERSION) != null) {
            desiredCapability.setCapability(CapabilityType.VERSION,
                    properties.getEnvProperty(VERSION));
        }
        if (properties.getEnvProperty(IGNORE_ZOOM_SETTING) != null) {
            desiredCapability.setCapability("ignoreZoomSetting",
                    properties.getEnvProperty(IGNORE_ZOOM_SETTING));
        }
        if (properties.getEnvProperty(IGNORE_PROTECTED_MODE_SETTINGS) != null) {
            desiredCapability.setCapability(
                    "ignoreProtectedModeSettings",
                    properties.getEnvProperty(IGNORE_PROTECTED_MODE_SETTINGS));
            logs = new LoggingPreferences();
        }
        if (properties.getEnvProperty(LOGGING_LEVEL) != null) {
            logs.enable(LogType.DRIVER,
                    Level.parse(properties.getEnvProperty(LOGGING_LEVEL)));
            desiredCapability.setCapability(CapabilityType.LOGGING_PREFS, logs);
        }
        if (properties.getEnvProperty(PROXY_TYPE) != null
                && properties.getEnvProperty(PROXY_ADDRESS) != null) {
            Proxy proxy = new Proxy();
            proxy.setProxyType(ProxyType.valueOf(properties.getEnvProperty(PROXY_TYPE)));
            proxy.setHttpProxy(properties.getEnvProperty(PROXY_ADDRESS));
            desiredCapability.setCapability(CapabilityType.PROXY, proxy);
        }

        return desiredCapability;

    }

    private void addProfileToCapability(DesiredCapabilities desiredCapabilities) {
        FirefoxProfile profile = new FirefoxProfile();
        final boolean allowAuth = StringUtils.equals(
                properties.getEnvProperty(ALLOW_BROWSER_AUTHENTICATION),
                "true");
        final boolean automaticallySave = properties.getEnvProperty(AUTOMATICALLY_SAVE_TO_DISK) != null;
        if (allowAuth) {
            String trustedDomains = StringUtils.defaultString(properties.getEnvProperty(LIST_OF_TRUSTED_DOMAINS_FOR_BROWSER_AUTHENTICATION));
            profile.setPreference("network.http.phishy-userpass-length", 255);
            profile.setPreference("network.automatic-ntlm-auth.trusted-uris", trustedDomains);
        } else if (automaticallySave) {
            profile.setPreference("browser.download.folderList", 2);
            profile.setPreference("browser.download.manager.showWhenStarting", false);
            profile.setPreference("browser.download.dir",
                    properties.getEnvProperty(DOWNLOAD_FILE_TO));
            profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
                    properties.getEnvProperty(AUTOMATICALLY_SAVE_TO_DISK));
        }
        if (allowAuth || automaticallySave) {
            LOGGER.info("Adding profile to " + desiredCapabilities.getBrowserName());
            desiredCapabilities.setCapability(FirefoxDriver.PROFILE, profile);

        }
    }
}
