package service;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class ID {
    public static int TaskCount = 0; // Нужен для рабботы map В будующем уёдет так как не думаю что пользователь будет что- то искать по счёту тасков

    public static int EpicCount = 0; // Нужен для рабботы map В будующем уёдет так как не думаю что пользователь будет что- то искать по счёту тасков

    public static int SubTaskCount = 0; // Нужен для рабботы map В будующем уёдет так как не думаю что пользователь будет что- то искать по счёту тасков

    private static final Random rand = new Random();

    private static final List idSet = new ArrayList();

    public static int generateId() {
        while (true) {
            int id = rand.nextInt(9999);

            if (idSet.contains(id)) continue;
            idSet.add(id);
            return id;
        }
    }
}
