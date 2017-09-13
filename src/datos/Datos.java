package datos;

import java.util.ArrayList;

public class Datos {

    private static ArrayList<Ransomware> ransomwares;

    public static ArrayList<Ransomware> cargarRansomwares() {
        ransomwares = new ArrayList<>();
        Ransomware r1 = new RansomwareJAES128("JAES-128", "...");
        ransomwares.add(r1);
        return ransomwares;
    }
}
