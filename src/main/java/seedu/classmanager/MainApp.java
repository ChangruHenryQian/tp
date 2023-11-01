package seedu.classmanager;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.classmanager.commons.core.Config;
import seedu.classmanager.commons.core.LogsCenter;
import seedu.classmanager.commons.core.Version;
import seedu.classmanager.commons.exceptions.DataLoadingException;
import seedu.classmanager.commons.util.ConfigUtil;
import seedu.classmanager.commons.util.StringUtil;
import seedu.classmanager.logic.Logic;
import seedu.classmanager.logic.LogicManager;
import seedu.classmanager.model.ClassManager;
import seedu.classmanager.model.Model;
import seedu.classmanager.model.ModelManager;
import seedu.classmanager.model.ReadOnlyClassManager;
import seedu.classmanager.model.ReadOnlyUserPrefs;
import seedu.classmanager.model.UserPrefs;
import seedu.classmanager.model.util.SampleDataUtil;
import seedu.classmanager.storage.ClassManagerStorage;
import seedu.classmanager.storage.JsonClassManagerStorage;
import seedu.classmanager.storage.JsonUserPrefsStorage;
import seedu.classmanager.storage.Storage;
import seedu.classmanager.storage.StorageManager;
import seedu.classmanager.storage.UserPrefsStorage;
import seedu.classmanager.ui.Ui;
import seedu.classmanager.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 2, 1, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing ClassManager ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());
        initLogging(config);

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        ClassManagerStorage classManagerStorage = new JsonClassManagerStorage(userPrefs.getClassManagerFilePath());
        storage = new StorageManager(classManagerStorage, userPrefsStorage);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s Class Manager and {@code userPrefs}. <br>
     * The data from the sample Class Manager will be used instead if {@code storage}'s Class Manager is not found,
     * or an empty Class Manager will be used instead if errors occur when reading {@code storage}'s Class Manager.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        logger.info("Using data file : " + storage.getClassManagerFilePath());

        Optional<ReadOnlyClassManager> classManagerOptional;
        ReadOnlyClassManager initialData;
        try {
            classManagerOptional = storage.readClassManager();
            if (classManagerOptional.isEmpty()) {
                logger.info("Creating a new data file " + storage.getClassManagerFilePath()
                        + " populated with a sample ClassManager.");
            }
            initialData = classManagerOptional.orElseGet(SampleDataUtil::getSampleClassManager);
        } catch (DataLoadingException e) {
            logger.warning("Data file at " + storage.getClassManagerFilePath() + " could not be loaded."
                    + " Will be starting with an empty ClassManager.");
            initialData = new ClassManager();
        }

        return new ModelManager(initialData, userPrefs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            if (configOptional.isEmpty()) {
                logger.info("Creating new config file " + configFilePathUsed);
            }
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataLoadingException e) {
            logger.warning("Config file at " + configFilePathUsed + " could not be loaded."
                    + " Using default config properties.");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using preference file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            if (prefsOptional.isEmpty()) {
                logger.info("Creating new preference file " + prefsFilePath);
            }
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataLoadingException e) {
            logger.warning("Preference file at " + prefsFilePath + " could not be loaded."
                    + " Using default preferences.");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting ClassManager " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Class Manager ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
