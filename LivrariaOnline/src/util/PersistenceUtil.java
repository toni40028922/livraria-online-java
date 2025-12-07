package util;

import java.io.*;

public class PersistenceUtil {

    // salva objeto (serialização) em arquivo
    public static void save(Object obj, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(obj);
        } catch (IOException e) {
            System.err.println("Erro ao salvar " + filename + " : " + e.getMessage());
        }
    }

    // carrega objeto serializado (retorna null se não encontrou)
    public static Object load(String filename) {
        File f = new File(filename);
        if (!f.exists()) return null;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar " + filename + " : " + e.getMessage());
            return null;
        }
    }
}
