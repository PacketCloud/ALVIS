package Cumulus.Bootstrap;

import Cumulus.Managers.ClientManager;
import Cumulus.Util.Logging.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Bootstrapper {

    private static ClientManager clientManager;

    private static boolean WATCHDOG = true;

    public static void main(String[] args) {
        startup();
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
        while (WATCHDOG){
            try{
                if (System.in.available() > 0){
                    clientManager.onConsoleInput(buffer.readLine());
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void startup(){
        Logger.logContext(Bootstrapper.class, "Starting bootstrapper...");
        clientManager = new ClientManager();
    }

    public static void softRestart() {
        Logger.logContext(Bootstrapper.class, "Initiating soft restart...");
        clientManager.onSoftRestart();
        Logger.logContext(Bootstrapper.class, "Soft restart complete.");
    }

    public static void hardRestart() {
        Logger.logContext(Bootstrapper.class, "Initiating hard restart...");
        clientManager.onShutdown();
        clientManager = new ClientManager();
        Logger.logContext(Bootstrapper.class, "Hard restart complete.");
    }

    public static void shutdown() {
        WATCHDOG = false;
        Logger.logContext(Bootstrapper.class, "Shutting down...");
        clientManager.onShutdown();
        Logger.logContext(Bootstrapper.class, "Goodbye.");
        System.exit(0);
    }

}
