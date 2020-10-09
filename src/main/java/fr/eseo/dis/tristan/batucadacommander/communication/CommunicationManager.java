package fr.eseo.dis.tristan.batucadacommander.communication;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.eseo.dis.tristan.batucadacommander.R;
import fr.eseo.dis.tristan.batucadacommander.activity.BatucadaActivity;
import fr.eseo.dis.tristan.batucadacommander.communication.enums.SerialComResult;
import fr.eseo.dis.tristan.batucadacommander.communication.enums.SerialInitResult;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Machine;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.objects.Effect;
import fr.eseo.dis.tristan.batucadacommander.lib.usbserial.driver.UsbSerialDriver;
import fr.eseo.dis.tristan.batucadacommander.lib.usbserial.driver.UsbSerialPort;
import fr.eseo.dis.tristan.batucadacommander.lib.usbserial.driver.UsbSerialProber;

public class CommunicationManager {
    private static final String TAG = "COMMUNICATION";
    private static final int MODE_TEST = 100;
    private static final int BAUD_RATE = 115200;
    private static final int DATA_BITS = 8;
    private static final int STOP_BITS = UsbSerialPort.STOPBITS_1;
    private static final int BIT_PARITY = UsbSerialPort.PARITY_NONE;
    private static final int BYTE_START_VALUE = 81;
    private static final int TIMEOUT_WRITE = 10;
    private static final int TIMEOUT = 1;
    private static final int RESPONSE_BUFFER_SIZE = 16;
    private static final int MAX_ATTEMPT = 3;

    private Context context;
    private UsbSerialDriver driver;
    private UsbDeviceConnection connection;
    private boolean initialized;
    private LogListener listener;

    /**
     * Init communication manager with a context
     * @param context The context
     */
    public CommunicationManager(Context context) {
        this.context = context;
    }

    /**
     * Initialize serial usb connection
     * @param context The activity context
     * @return the status of initialisation : SUCCESS if no error, ERROR_ otherwize
     */
    public SerialInitResult initSerial(Activity context) {
        if(BatucadaActivity.DISABLE_SERIAL) {
            //Disable verification => DEMO PURPOSE
            this.initialized = false;
            return SerialInitResult.SUCCESS;
        } else {
            //Working purpose
            SerialInitResult result = this.initialize(context);
            this.initialized = SerialInitResult.SUCCESS.equals(result);

            return result;
        }
    }

    /**
     * Initialize serial usb connection
     * @param context The activity context
     * @return the status of initialisation : SUCCESS if no error, ERROR_ otherwize
     */
    private SerialInitResult initialize(Activity context) {
        UsbManager manager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        List<UsbSerialDriver> availableDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(manager);

        if (availableDrivers.isEmpty()) {
            Log.e(TAG, this.context.getString(R.string.log_communication_error_noconnected));
            return SerialInitResult.ERROR_NO_DEVICE;
        }

        Log.d(TAG, this.context.getString(R.string.log_communication_connecteddevices, availableDrivers.size()));

        // Open a connection to the first available driver.
        this.driver = availableDrivers.get(0);
        UsbDevice device = driver.getDevice();

        Log.d(TAG, this.context.getString(R.string.log_communication_deviceinfos,
                device.getVendorId(), device.getDeviceName()));

        //Open the connection
        this.connection = manager.openDevice(this.driver.getDevice());

        //If permission not set
        if (this.connection == null) {
            this.requestPermission(manager, device, context);
            return SerialInitResult.ERROR_NO_PERMISSION;
        }

        Log.d(TAG, this.context.getString(R.string.log_communication_paramsinfo,
                BAUD_RATE, DATA_BITS, STOP_BITS, TIMEOUT));

        return SerialInitResult.SUCCESS;
    }

    /**
     * Send a message over serial and wait for a response
     * @param message The message to send
     * @return SUCCESS if message is acquited, false otherwise
     */
    private SerialComResult sendDatas(SerialMessage message) {
        if(!this.initialized) {
            return SerialComResult.ERROR_NOT_INITIALIZED;
        }

        //Prepare the port and open it
        UsbSerialPort port = driver.getPorts().get(0);
        SerialComResult result = SerialComResult.SUCCESS;

        try {
            port.open(connection);
            port.setParameters(BAUD_RATE, DATA_BITS, STOP_BITS, BIT_PARITY);

            //Append the BEGGINNING_VALUE to first byte
            byte[] messageB = message.getMessage();

            //Send the message
            int written = port.write(messageB, TIMEOUT_WRITE);

            Log.d(TAG, this.context.getString(R.string.log_communication_writemessage, written));

            //Prepare the buffer and start waiting
            byte[] buffer = new byte[RESPONSE_BUFFER_SIZE];
            int numBytesRead = port.read(buffer, TIMEOUT);

            if(numBytesRead == 0) {
                //No response received
                Log.d(TAG, this.context.getString(R.string.log_communication_noresponse));
                result = SerialComResult.ERROR_MESSAGE_TIMEOUT;
            } else {
                Log.d(TAG, this.context.getString(R.string.log_communication_receivemessage, numBytesRead));
                this.log(TAG, "Reception : " + Arrays.toString(buffer));
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            result = SerialComResult.ERROR_SERIAL_NOT_CONNECTED;
        } finally {
            try {
                port.close();
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    /**
     * Send the effect to the machine
     * @param effect The effect to send
     * @param idGroupe the id of the concerned group
     * @param listMachine the list of the machines in the groupe
     * @param intensity the intensity of effect
     * @return true if the effect was successful received, false otherwise (TIMEOUT or ERROR)
     */
    public SerialComResult sendEffectToMachine(Effect effect, int idGroupe, @NonNull ArrayList<Machine> listMachine, int intensity) {
        int attempt = 0;

        int[] recipient = new int[listMachine.size()];
        for (int i =0; i< listMachine.size(); i++){
            recipient[i] = listMachine.get(i).getAdresse();
        }

        SerialMessage message = SerialMessage.build(idGroupe, recipient, effect, intensity);


        Log.d(TAG, "Serial : " + Arrays.toString(message.getMessage()));

        //Send the message
        SerialComResult result = SerialComResult.ERROR_SERIAL_NOT_CONNECTED;
        while(attempt < MAX_ATTEMPT && !SerialComResult.SUCCESS.equals(result)) {
            result = this.sendDatas(message);
            attempt++;
        }

        switch (result) {
            case SUCCESS:
                break;
            case ERROR_NOT_INITIALIZED:
                Log.w(TAG, this.context.getString(R.string.log_communication_notinit));
                this.log(TAG, this.context.getString(R.string.log_communication_notinit));
                break;
            case ERROR_MESSAGE_TIMEOUT:
             /*   Log.w(TAG, this.context.getString(R.string.log_communication_timeout, machine.getAdresse()));
                this.log(TAG, this.context.getString(R.string.log_communication_timeout, machine.getAdresse()));*/
                break;
            case ERROR_SERIAL_NOT_CONNECTED:
              /*  Log.w(TAG, this.context.getString(R.string.log_communication_error, machine.getAdresse()));
                this.log(TAG, this.context.getString(R.string.log_communication_error, machine.getAdresse()));*/
                break;
        }

        return result;
    }

    /**
     * Test if a machien is connected
     * @param machine The machine
     * @return The result
     */
    public SerialComResult testMachine(@NonNull Machine machine) {
        int attempt = 0;
        SerialMessage message = SerialMessage.build(machine.getAdresse(), MODE_TEST);

        //Send the message
        SerialComResult result = SerialComResult.ERROR_SERIAL_NOT_CONNECTED;
        while(attempt < MAX_ATTEMPT && !SerialComResult.SUCCESS.equals(result)) {
            result = this.sendDatas(message);
            attempt++;
        }

        switch (result) {
            case SUCCESS:
                break;
            case ERROR_NOT_INITIALIZED:
                Log.w(TAG, this.context.getString(R.string.log_communication_notinit));
                this.log(TAG, this.context.getString(R.string.log_communication_notinit));
                break;
            case ERROR_MESSAGE_TIMEOUT:
                Log.w(TAG, this.context.getString(R.string.log_communication_timeout, machine.getAdresse()));
                this.log(TAG, this.context.getString(R.string.log_communication_timeout, machine.getAdresse()));
                break;
            case ERROR_SERIAL_NOT_CONNECTED:
                Log.w(TAG, this.context.getString(R.string.log_communication_error, machine.getAdresse()));
                this.log(TAG, this.context.getString(R.string.log_communication_error, machine.getAdresse()));
                break;
        }

        return result;
    }


    /**
     * Request permission to use USB
     * @param manager The usb manager
     * @param device The device to request permission to
     * @param context The activity that required permission
     */
    private void requestPermission(UsbManager manager, UsbDevice device, Activity context) {
        Log.d(TAG, this.context.getString(R.string.log_communication_requestpermission));

        Intent intent = new Intent(context, context.getClass());
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
        manager.requestPermission(device, pi);
    }

    /**
     * Get the context of the manager
     * @return The context
     */
    public Context getContext() {
        return context;
    }

    /**
     * Set the log listener
     * @param listener The logger
     */
    public void setListener(LogListener listener) {
        this.listener = listener;
    }

    /**
     * Log
     * @param tag log tag
     * @param message log message
     */
    private void log(String tag, String message) {
        if(this.listener != null) {
            this.listener.log(tag, message);
        }
    }
}
